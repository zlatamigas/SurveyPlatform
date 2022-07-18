package epam.zlatamigas.surveyplatform.model.dao.impl;

import epam.zlatamigas.surveyplatform.exception.DaoException;
import epam.zlatamigas.surveyplatform.model.connection.ConnectionPool;
import epam.zlatamigas.surveyplatform.model.dao.BaseDao;
import epam.zlatamigas.surveyplatform.model.dao.DbOrderType;
import epam.zlatamigas.surveyplatform.model.dao.SurveyDao;
import epam.zlatamigas.surveyplatform.model.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static epam.zlatamigas.surveyplatform.model.dao.DbTableInfo.*;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class SurveyDaoImpl implements SurveyDao {

    private static final Logger logger = LogManager.getLogger();

    //Get all themes
    private static final String FIND_ALL_THEMES = "SELECT id_theme, theme_name, theme_status FROM themes";

    //Insert survey info statements
    private static final String INSERT_SURVEY_STATEMENT =
            "INSERT INTO surveys(survey_name, survey_description, survey_status, theme_id, creator_id) VALUES (?,?,?,?,?)";
    private static final String INSERT_SURVEY_QUESTION_STATEMENT =
            "INSERT INTO questions(select_multiple, formulation, survey_id) VALUES (?,?,?)";
    private static final String INSERT_SURVEY_QUESTION_ANSWER_STATEMENT =
            "INSERT INTO question_answers(answer, selected_count, question_id) VALUES (?,?,?)";
    public static final String INSERT_SURVEY_USER_ATTEMPT =
            "INSERT INTO survey_user_attempts(finished_date_time, survey_id, user_id) VALUES (CONVERT(?, DATETIME), ?, ?)";
    public static final String INSERT_SURVEY_GUEST_ATTEMPT =
            "INSERT INTO survey_user_attempts(finished_date_time, survey_id) VALUES (CONVERT(?, DATETIME), ?)";

    //Update survey info statements
    private static final String UPDATE_SURVEY_STATEMENT = """
            UPDATE surveys 
            SET survey_name = ?, survey_description = ?, survey_status = ?, theme_id = ?, creator_id = ? 
            WHERE id_survey = ?;
            """;
    private static final String UPDATE_SURVEY_STATUS_STATEMENT = """
            UPDATE surveys 
            SET survey_status = ?  
            WHERE id_survey = ?;
            """;
    private static final String UPDATE_SURVEY_QUESTION_STATEMENT = """
            UPDATE questions 
            SET select_multiple = ?, formulation = ?, survey_id = ? 
            WHERE id_question = ?;
            """;
    private static final String UPDATE_SURVEY_QUESTION_ANSWER_STATEMENT = """
            UPDATE question_answers 
            SET answer = ?, selected_count = ?, question_id = ? 
            WHERE id_question_answer = ?;
            """;
    private static final String INCREMENT_SURVEY_QUESTION_ANSWER_ATTEMPT_STATEMENT = """
            UPDATE question_answers 
            SET selected_count = selected_count + 1 
            WHERE id_question_answer = ?;
            """;

    // Cascade delete for survey, questions, answers
    private static final String DELETE_SURVEY_STATEMENT = "DELETE FROM surveys WHERE id_survey = ?";
    private static final String DELETE_QUESTION_STATEMENT = "DELETE FROM questions WHERE id_question = ?";
    private static final String DELETE_QUESTION_ANSWER_STATEMENT = "DELETE FROM question_answers WHERE id_question_answer = ?";

    // Get survey info
    private static final String FIND_PARTICIPANT_SURVEY_BY_ID_STATEMENT = """
            SELECT survey_name, survey_description, theme_id 
            FROM surveys 
            WHERE id_survey = ? AND survey_status = 'STARTED'
            """;
    private static final String FIND_CREATOR_SURVEY_BY_ID_STATEMENT = """
            SELECT survey_name, survey_status, survey_description, theme_id, creator_id 
            FROM surveys  
            WHERE id_survey = ? AND creator_id = ?
            """;
    private static final String FIND_SURVEY_QUESTIONS_BY_SURVEY_ID_STATEMENT = """
            SELECT id_question, formulation, select_multiple 
            FROM questions 
            WHERE survey_id = ?
            """;
    private static final String FIND_SURVEY_QUESTION_ANSWERS_BY_SURVEY_ID_STATEMENT = """
            SELECT id_question_answer, answer, selected_count 
            FROM question_answers 
            WHERE question_id = ?
            """;

    private static final String FIND_CREATOR_SURVEYS_COMMON_INFO_BASE_STATEMENT = """
            SELECT id_survey, survey_name, survey_description, theme_id, survey_status 
            FROM surveys 
            WHERE creator_id = ? 
            """;
    private static final String FIND_PARTICIPANT_SURVEYS_COMMON_INFO_BASE_STATEMENT = """
            SELECT id_survey, survey_name, survey_description, theme_id 
            FROM surveys 
            WHERE survey_status = 'STARTED' 
            """;
    private static final String WHERE_THEME_ID_EQUALS_STATEMENT = "AND theme_id = ? ";
    private static final String WHERE_THEME_ID_IS_NULL_STATEMENT = "AND theme_id IS NULL ";
    private static final String WHERE_SURVEY_STATUS_EQUALS_STATEMENT = "AND survey_status = ? ";
    private static final String WHERE_NAME_CONTAINS_STATEMENT = "AND INSTR(LOWER(survey_name), LOWER(?)) > 0 ";
    private static final String ORDER_BY_SURVEY_NAME_STATEMENT = "ORDER BY survey_name ";

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static SurveyDaoImpl instance;

    private SurveyDaoImpl() {
    }

    public static SurveyDaoImpl getInstance() {
        if (instance == null) {
            instance = new SurveyDaoImpl();
        }
        return instance;
    }

    private Map<Integer, Theme> findThemes() throws DaoException {
        Map<Integer, Theme> themes = new HashMap<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ALL_THEMES);
             ResultSet resultSet = ps.executeQuery()) {

            Theme theme;
            int themeId;
            while (resultSet.next()) {
                themeId = resultSet.getInt(THEMES_TABLE_PK_COLUMN);
                theme = new Theme.ThemeBuilder()
                        .setThemeId(themeId)
                        .setThemeName(resultSet.getString(THEMES_TABLE_NAME_COLUMN))
                        .setThemeStatus(ThemeStatus.valueOf(resultSet.getString(THEMES_TABLE_STATUS_COLUMN)))
                        .getTheme();
                themes.put(themeId, theme);
            }

        } catch (SQLException e) {
            logger.error("Failed to find all themes: {}", e.getMessage());
            throw new DaoException("Failed to find all themes", e);
        }

        return themes;
    }

    @Override
    public boolean insert(Survey survey) throws DaoException {

        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean result = false;

            // Insert new survey data
            try (PreparedStatement psInsertSurvey = connection.prepareStatement(INSERT_SURVEY_STATEMENT, RETURN_GENERATED_KEYS)) {
                psInsertSurvey.setString(1, survey.getName());
                psInsertSurvey.setString(2, survey.getDescription());
                psInsertSurvey.setString(3, survey.getStatus().name());
                if (survey.getTheme().getThemeId() > 0) {
                    psInsertSurvey.setInt(4, survey.getTheme().getThemeId());
                } else {
                    psInsertSurvey.setNull(4, Types.NULL);
                }
                psInsertSurvey.setInt(5, survey.getCreator().getUserId());
                psInsertSurvey.executeUpdate();

                // Get id of inserted survey
                try (ResultSet rsSurveyId = psInsertSurvey.getGeneratedKeys()) {
                    if (rsSurveyId.next()) {
                        result = true;
                        int surveyId = rsSurveyId.getInt(1);

                        // Insert survey questions
                        for (SurveyQuestion question : survey.getQuestions()) {
                            try (PreparedStatement psInsertSurveyQuestion =
                                         connection.prepareStatement(INSERT_SURVEY_QUESTION_STATEMENT, RETURN_GENERATED_KEYS)) {
                                psInsertSurveyQuestion.setBoolean(1, question.isSelectMultiple());
                                psInsertSurveyQuestion.setString(2, question.getFormulation());
                                psInsertSurveyQuestion.setInt(3, surveyId);
                                psInsertSurveyQuestion.executeUpdate();

                                // Get id of inserted question
                                try (ResultSet rsSurveyQuestionId = psInsertSurveyQuestion.getGeneratedKeys()) {
                                    if (rsSurveyQuestionId.next()) {
                                        int surveyQuestionId = rsSurveyQuestionId.getInt(1);

                                        // Insert survey question answers
                                        for (SurveyQuestionAnswer answer : question.getAnswers()) {
                                            try (PreparedStatement psInsertSurveyQuestionAnswer =
                                                         connection.prepareStatement(INSERT_SURVEY_QUESTION_ANSWER_STATEMENT)) {
                                                psInsertSurveyQuestionAnswer.setString(1, answer.getAnswer());
                                                psInsertSurveyQuestionAnswer.setInt(2, answer.getSelectedCount());
                                                psInsertSurveyQuestionAnswer.setInt(3, surveyQuestionId);
                                                result = psInsertSurveyQuestionAnswer.executeUpdate() == 1;
                                            }
                                            if(!result){
                                                break;
                                            }
                                        }
                                    } else {
                                        result = false;
                                    }
                                }
                            }
                            if(!result){
                                break;
                            }
                        }
                    }
                }
            }

            if(result) {
                connection.commit();
            } else {
                connection.rollback();
                logger.error("Failed to insert new survey");
            }

            return result;

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error("Failed to rollback changes while insert: {}", ex.getMessage());
                throw new DaoException("Failed to rollback changes while insert", e);
            }
            logger.error("Failed to insert new survey: {}", e.getMessage());
            throw new DaoException("Failed to insert new survey", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public boolean delete(int id) throws DaoException {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_SURVEY_STATEMENT)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Failed to delete survey with id_survey = {} : {}", id, e.getMessage());
            throw new DaoException("Failed to delete survey with id_survey = " + id, e);
        }
    }

    @Override
    public Optional<Survey> findById(int id) throws DaoException {
        throw new UnsupportedOperationException("Unsupported find operation for survey");
    }

    @Override
    public Optional<Survey> findParticipantSurveyInfo(int surveyId) throws DaoException {

        Optional<Survey> surveyOptional = Optional.empty();

        Map<Integer, Theme> themes = findThemes();

        try (Connection connection = ConnectionPool.getInstance().getConnection()) {

            //Find survey

            try (PreparedStatement psFindSurvey = connection.prepareStatement(FIND_PARTICIPANT_SURVEY_BY_ID_STATEMENT)) {
                psFindSurvey.setInt(1, surveyId);
                try (ResultSet rsSurvey = psFindSurvey.executeQuery()) {
                    if (rsSurvey.next()) {
                        int themeId = rsSurvey.getInt(SURVEYS_TABLE_FK_THEME_ID_COLUMN);
                        Theme theme = themes.containsKey(themeId) ? themes.get(themeId) : new Theme.ThemeBuilder().setThemeId(-1).getTheme();

                        Survey survey = new Survey.SurveyBuilder()
                                .setSurveyId(surveyId)
                                .setName(rsSurvey.getString(SURVEYS_TABLE_NAME_COLUMN))
                                .setDescription(rsSurvey.getString(SURVEYS_TABLE_DESCRIPTION_COLUMN))
                                .setTheme(theme)
                                .getSurvey();

                        //Find survey questions

                        try (PreparedStatement psFindSurveyQuestion = connection.prepareStatement(FIND_SURVEY_QUESTIONS_BY_SURVEY_ID_STATEMENT)) {
                            psFindSurveyQuestion.setInt(1, surveyId);
                            try (ResultSet rsSurveyQuestion = psFindSurveyQuestion.executeQuery()) {
                                SurveyQuestion question;
                                int questionId;
                                while (rsSurveyQuestion.next()) {

                                    questionId = rsSurveyQuestion.getInt(QUESTIONS_TABLE_PK_COLUMN);
                                    question = new SurveyQuestion.SurveyQuestionBuilder()
                                            .setQuestionId(questionId)
                                            .setSelectMultiple(rsSurveyQuestion.getBoolean(QUESTIONS_TABLE_SELECT_MULTIPLE_COLUMN))
                                            .setFormulation(rsSurveyQuestion.getString(QUESTIONS_TABLE_FORMULATION_COLUMN))
                                            .getSurveyQuestion();

                                    //Find survey question answers

                                    try (PreparedStatement psFindSurveyQuestionAnswer = connection.prepareStatement(FIND_SURVEY_QUESTION_ANSWERS_BY_SURVEY_ID_STATEMENT)) {
                                        psFindSurveyQuestionAnswer.setInt(1, questionId);
                                        try (ResultSet rsSurveyQuestionAnswer = psFindSurveyQuestionAnswer.executeQuery()) {
                                            SurveyQuestionAnswer answer;
                                            while (rsSurveyQuestionAnswer.next()) {
                                                answer = new SurveyQuestionAnswer.SurveyQuestionAnswerBuilder()
                                                        .setQuestionAnswerId(rsSurveyQuestionAnswer.getInt(ANSWERS_TABLE_PK_COLUMN))
                                                        .setAnswer(rsSurveyQuestionAnswer.getString(ANSWERS_TABLE_ANSWER_COLUMN))
                                                        .getSurveyQuestionAnswer();

                                                question.addAnswer(answer);
                                            }
                                        }
                                    }
                                    survey.addQuestion(question);
                                }
                            }
                        }
                        surveyOptional = Optional.of(survey);
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to find survey by id_survey = {} : {}", surveyId, e.getMessage());
            throw new DaoException("Failed to find survey by id_survey = " + surveyId, e);
        }

        return surveyOptional;
    }

    @Override
    public Optional<Survey> findCreatorSurveyInfo(int surveyId, int creatorId) throws DaoException {
        Optional<Survey> surveyOptional = Optional.empty();

        Map<Integer, Theme> themes = findThemes();

        try (Connection connection = ConnectionPool.getInstance().getConnection()) {

            //Find survey

            try (PreparedStatement psFindSurvey = connection.prepareStatement(FIND_CREATOR_SURVEY_BY_ID_STATEMENT)) {
                psFindSurvey.setInt(1, surveyId);
                psFindSurvey.setInt(2, creatorId);
                try (ResultSet rsSurvey = psFindSurvey.executeQuery()) {
                    if (rsSurvey.next()) {

                        int themeId = rsSurvey.getInt(SURVEYS_TABLE_FK_THEME_ID_COLUMN);
                        Theme theme = themes.containsKey(themeId) ? themes.get(themeId) : new Theme.ThemeBuilder().setThemeId(-1).getTheme();

                        Survey survey = new Survey.SurveyBuilder()
                                .setSurveyId(surveyId)
                                .setName(rsSurvey.getString(SURVEYS_TABLE_NAME_COLUMN))
                                .setDescription(rsSurvey.getString(SURVEYS_TABLE_DESCRIPTION_COLUMN))
                                .setStatus(SurveyStatus.valueOf(rsSurvey.getString(SURVEYS_TABLE_STATUS_COLUMN)))
                                .setTheme(theme)
                                .setCreator(new User.UserBuilder()
                                        .setUserId(rsSurvey.getInt(SURVEYS_TABLE_FK_CREATOR_ID_COLUMN)).getUser())
                                .getSurvey();

                        //Find survey questions

                        try (PreparedStatement psFindSurveyQuestion = connection.prepareStatement(FIND_SURVEY_QUESTIONS_BY_SURVEY_ID_STATEMENT)) {
                            psFindSurveyQuestion.setInt(1, surveyId);
                            try (ResultSet rsSurveyQuestion = psFindSurveyQuestion.executeQuery()) {
                                SurveyQuestion question;
                                int questionId;
                                while (rsSurveyQuestion.next()) {
                                    questionId = rsSurveyQuestion.getInt(QUESTIONS_TABLE_PK_COLUMN);
                                    question = new SurveyQuestion.SurveyQuestionBuilder()
                                            .setQuestionId(questionId)
                                            .setSelectMultiple(rsSurveyQuestion.getBoolean(QUESTIONS_TABLE_SELECT_MULTIPLE_COLUMN))
                                            .setFormulation(rsSurveyQuestion.getString(QUESTIONS_TABLE_FORMULATION_COLUMN))
                                            .getSurveyQuestion();

                                    //Find survey question answers

                                    try (PreparedStatement psFindSurveyQuestionAnswer = connection.prepareStatement(FIND_SURVEY_QUESTION_ANSWERS_BY_SURVEY_ID_STATEMENT)) {
                                        psFindSurveyQuestionAnswer.setInt(1, questionId);
                                        try (ResultSet rsSurveyQuestionAnswer = psFindSurveyQuestionAnswer.executeQuery()) {
                                            SurveyQuestionAnswer answer;
                                            while (rsSurveyQuestionAnswer.next()) {
                                                answer = new SurveyQuestionAnswer.SurveyQuestionAnswerBuilder()
                                                        .setQuestionAnswerId(rsSurveyQuestionAnswer.getInt(ANSWERS_TABLE_PK_COLUMN))
                                                        .setAnswer(rsSurveyQuestionAnswer.getString(ANSWERS_TABLE_ANSWER_COLUMN))
                                                        .setSelectedCount(rsSurveyQuestionAnswer.getInt(ANSWERS_TABLE_SELECTED_COUNT_COLUMN))
                                                        .getSurveyQuestionAnswer();
                                                question.addAnswer(answer);
                                            }
                                        }
                                    }
                                    survey.addQuestion(question);
                                }
                            }
                        }
                        surveyOptional = Optional.of(survey);
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to find survey by id_survey = {} : {}", surveyId, e.getMessage());
            throw new DaoException("Failed to find survey by id_survey = " + surveyId, e);
        }

        return surveyOptional;
    }

    @Override
    public List<Survey> findParticipantSurveysCommonInfoSearch(int filterThemeId, String[] searchWords, DbOrderType orderType) throws DaoException {
        List<Survey> surveys = new ArrayList<>();
        Survey survey = null;

        Map<Integer, Theme> themes = findThemes();

        try (Connection connection = ConnectionPool.getInstance().getConnection();) {

            StringBuilder query = new StringBuilder(FIND_PARTICIPANT_SURVEYS_COMMON_INFO_BASE_STATEMENT);
            if (filterThemeId > FILTER_THEMES_ALL) {
                query.append(WHERE_THEME_ID_EQUALS_STATEMENT);
            } else if (filterThemeId == FILTER_THEMES_NONE) {
                query.append(WHERE_THEME_ID_IS_NULL_STATEMENT);
            } else if (filterThemeId != FILTER_THEMES_ALL) {
                throw new DaoException("Passed unknown theme: filterThemeId = " + filterThemeId);
            }
            query.append(WHERE_NAME_CONTAINS_STATEMENT.repeat(searchWords.length));
            query.append(ORDER_BY_SURVEY_NAME_STATEMENT).append(orderType.name());

            try (PreparedStatement psFindSurvey = connection.prepareStatement(query.toString())) {
                int parameterIndex = 1;
                if (filterThemeId > FILTER_THEMES_ALL) {
                    psFindSurvey.setInt(parameterIndex++, filterThemeId);
                }
                for (int i = 0; i < searchWords.length; i++, parameterIndex++) {
                    psFindSurvey.setString(parameterIndex, searchWords[i]);
                }

                try (ResultSet rsSurvey = psFindSurvey.executeQuery()) {
                    while (rsSurvey.next()) {

                        int themeId = rsSurvey.getInt(SURVEYS_TABLE_FK_THEME_ID_COLUMN);
                        Theme theme = themes.containsKey(themeId) ? themes.get(themeId) : new Theme.ThemeBuilder().setThemeId(-1).getTheme();

                        survey = new Survey.SurveyBuilder()
                                .setSurveyId(rsSurvey.getInt(SURVEYS_TABLE_PK_COLUMN))
                                .setName(rsSurvey.getString(SURVEYS_TABLE_NAME_COLUMN))
                                .setDescription(rsSurvey.getString(SURVEYS_TABLE_DESCRIPTION_COLUMN))
                                .setTheme(theme)
                                .getSurvey();

                        surveys.add(survey);
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to find surveys: {}", e.getMessage());
            throw new DaoException("Failed to find surveys", e);
        }

        return surveys;
    }

    @Override
    public List<Survey> findCreatorSurveysCommonInfoSearch(int filterThemeId, String[] searchWords, DbOrderType orderType, Optional<SurveyStatus> surveyStatus, int userId) throws DaoException {
        List<Survey> surveys = new ArrayList<>();
        Survey survey = null;

        Map<Integer, Theme> themes = findThemes();

        try (Connection connection = ConnectionPool.getInstance().getConnection();) {

            StringBuilder query = new StringBuilder(FIND_CREATOR_SURVEYS_COMMON_INFO_BASE_STATEMENT);
            if (filterThemeId > FILTER_THEMES_ALL) {
                query.append(WHERE_THEME_ID_EQUALS_STATEMENT);
            } else if (filterThemeId == FILTER_THEMES_NONE) {
                query.append(WHERE_THEME_ID_IS_NULL_STATEMENT);
            } else if (filterThemeId != FILTER_THEMES_ALL) {
                throw new DaoException("Passed unknown theme: filterThemeId = " + filterThemeId);
            }
            query.append(WHERE_NAME_CONTAINS_STATEMENT.repeat(searchWords.length));
            if (surveyStatus.isPresent()) {
                query.append(WHERE_SURVEY_STATUS_EQUALS_STATEMENT);
            }
            query.append(ORDER_BY_SURVEY_NAME_STATEMENT).append(orderType.name());

            try (PreparedStatement psFindSurvey = connection.prepareStatement(query.toString())) {
                int parameterIndex = 1;
                psFindSurvey.setInt(parameterIndex++, userId);
                if (filterThemeId > FILTER_THEMES_ALL) {
                    psFindSurvey.setInt(parameterIndex++, filterThemeId);
                }
                for (int i = 0; i < searchWords.length; i++, parameterIndex++) {
                    psFindSurvey.setString(parameterIndex, searchWords[i]);
                }
                if (surveyStatus.isPresent()) {
                    psFindSurvey.setString(parameterIndex, surveyStatus.get().name());
                }

                try (ResultSet rsSurvey = psFindSurvey.executeQuery()) {
                    while (rsSurvey.next()) {

                        int themeId = rsSurvey.getInt(SURVEYS_TABLE_FK_THEME_ID_COLUMN);
                        Theme theme = themes.containsKey(themeId) ? themes.get(themeId) : new Theme.ThemeBuilder().setThemeId(-1).getTheme();

                        survey = new Survey.SurveyBuilder()
                                .setSurveyId(rsSurvey.getInt(SURVEYS_TABLE_PK_COLUMN))
                                .setName(rsSurvey.getString(SURVEYS_TABLE_NAME_COLUMN))
                                .setDescription(rsSurvey.getString(SURVEYS_TABLE_DESCRIPTION_COLUMN))
                                .setStatus(SurveyStatus.valueOf(rsSurvey.getString(SURVEYS_TABLE_STATUS_COLUMN)))
                                .setTheme(theme)
                                .getSurvey();

                        surveys.add(survey);
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to find surveys: {}", e.getMessage());
            throw new DaoException("Failed to find surveys", e);
        }

        return surveys;
    }

    @Override
    public boolean updateSurveyStatus(int surveyId, SurveyStatus status) throws DaoException {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement psInsertSurvey = connection.prepareStatement(UPDATE_SURVEY_STATUS_STATEMENT)) {

            psInsertSurvey.setString(1, status.name());
            psInsertSurvey.setInt(2, surveyId);
            return psInsertSurvey.executeUpdate() == 1;

        } catch (SQLException e) {
            logger.error("Failed to update survey with id_survey = {}: {}", surveyId, e.getMessage());
            throw new DaoException("Failed to update survey with id_survey = " + surveyId, e);
        }
    }

    @Override
    public boolean updateParticipantSurveyResult(SurveyUserAttempt surveyAttempt) throws DaoException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean result = false;

            String dateTime = surveyAttempt.getFinishedDate().format(dateTimeFormatter);
            User user = surveyAttempt.getUser();
            Survey survey = surveyAttempt.getSurvey();

            if (user.getUserId() > 0) {
                try (PreparedStatement psInsertUserAttempt = connection.prepareStatement(INSERT_SURVEY_USER_ATTEMPT)) {
                    psInsertUserAttempt.setString(1, dateTime);
                    psInsertUserAttempt.setInt(2, survey.getSurveyId());
                    psInsertUserAttempt.setInt(3, user.getUserId());
                    result = psInsertUserAttempt.executeUpdate() == 1;
                }
            } else {
                try (PreparedStatement psInsertGuestAttempt = connection.prepareStatement(INSERT_SURVEY_GUEST_ATTEMPT)) {
                    psInsertGuestAttempt.setString(1, dateTime);
                    psInsertGuestAttempt.setInt(2, survey.getSurveyId());
                    result = psInsertGuestAttempt.executeUpdate() == 1;
                }
            }

            if(result) {
                for (SurveyQuestion question : survey.getQuestions()) {
                    for (SurveyQuestionAnswer answer : question.getAnswers()) {
                        if (answer.getSelectedCount() == 1) {
                            try (PreparedStatement psIncrementAnswerAttempt =
                                         connection.prepareStatement(INCREMENT_SURVEY_QUESTION_ANSWER_ATTEMPT_STATEMENT)) {
                                psIncrementAnswerAttempt.setInt(1, answer.getQuestionAnswerId());
                                result = psIncrementAnswerAttempt.executeUpdate() == 1;
                            }
                        }
                        if(!result){
                            break;
                        }
                    }
                    if(!result){
                        break;
                    }
                }
            }

            if(result) {
                connection.commit();
            } else {
                connection.rollback();
                logger.error("Failed to update survey result");
            }

            return result;

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error("Failed to rollback changes while update: {}", ex.getMessage());
                throw new DaoException("Failed to rollback changes while update", e);
            }
            logger.error("Failed to update survey result for survey with id_survey = {}: {}", surveyAttempt.getSurvey().getSurveyId(), e.getMessage());
            throw new DaoException("Failed to update survey result for survey with id_survey = " + surveyAttempt.getSurvey().getSurveyId(), e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public boolean update(Survey survey) throws DaoException {

        Optional<Survey> oldSurveyOptional = findCreatorSurveyInfo(survey.getSurveyId(), survey.getCreator().getUserId());
        if (oldSurveyOptional.isEmpty()) {
            logger.error("Survey does not exist: id_survey = {}", survey.getSurveyId());
            return false;
        }

        Survey oldSurvey = oldSurveyOptional.get();
        int surveyId = oldSurvey.getSurveyId();

        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean result = false;

            // 1. Update common survey data

            try (PreparedStatement psUpdateSurvey =
                         connection.prepareStatement(UPDATE_SURVEY_STATEMENT, RETURN_GENERATED_KEYS)) {
                psUpdateSurvey.setString(1, survey.getName());
                psUpdateSurvey.setString(2, survey.getDescription());
                psUpdateSurvey.setString(3, survey.getStatus().name());
                if (survey.getTheme().getThemeId() != -1) {
                    psUpdateSurvey.setInt(4, survey.getTheme().getThemeId());
                } else {
                    psUpdateSurvey.setNull(4, Types.NULL);
                }
                psUpdateSurvey.setInt(5, survey.getCreator().getUserId());
                psUpdateSurvey.setInt(6, surveyId);
                result = psUpdateSurvey.executeUpdate() == 1;
            }

            List<SurveyQuestion> oldQuestions = oldSurvey.getQuestions();
            List<SurveyQuestion> newQuestions = survey.getQuestions();

            // 2.1. Search for question with same id in old questions (result: updated or new)
            if(result) {
                for (SurveyQuestion question : newQuestions) {
                    if (question.getQuestionId() > 0) {

                        // 2.2 Updated question
                        try (PreparedStatement psUpdateSurveyQuestion = connection.prepareStatement(UPDATE_SURVEY_QUESTION_STATEMENT)) {
                            psUpdateSurveyQuestion.setBoolean(1, question.isSelectMultiple());
                            psUpdateSurveyQuestion.setString(2, question.getFormulation());
                            psUpdateSurveyQuestion.setInt(3, surveyId);
                            psUpdateSurveyQuestion.setInt(4, question.getQuestionId());
                            result = psUpdateSurveyQuestion.executeUpdate() == 1;
                        }

                        if(result) {

                            // 2.3 Update answers
                            SurveyQuestion oldQuestion = oldQuestions.stream()
                                    .filter(q -> q.getQuestionId() == question.getQuestionId())
                                    .findFirst()
                                    .orElseThrow(() -> new DaoException("In survey no question found with id = " + question.getQuestionId()));

                            for (SurveyQuestionAnswer answer : question.getAnswers()) {
                                if (answer.getQuestionAnswerId() > 0) {
                                    // 2.3.1. Update answer

                                    try (PreparedStatement psUpdateSurveyQuestionAnswer =
                                                 connection.prepareStatement(UPDATE_SURVEY_QUESTION_ANSWER_STATEMENT)) {
                                        psUpdateSurveyQuestionAnswer.setString(1, answer.getAnswer());
                                        psUpdateSurveyQuestionAnswer.setInt(2, answer.getSelectedCount());
                                        psUpdateSurveyQuestionAnswer.setInt(3, question.getQuestionId());
                                        psUpdateSurveyQuestionAnswer.setInt(4, answer.getQuestionAnswerId());
                                        result = psUpdateSurveyQuestionAnswer.executeUpdate() == 1;
                                    }
                                } else {
                                    // 2.3.2. New answer

                                    try (PreparedStatement psInsertSurveyQuestionAnswer =
                                                 connection.prepareStatement(INSERT_SURVEY_QUESTION_ANSWER_STATEMENT)) {
                                        psInsertSurveyQuestionAnswer.setString(1, answer.getAnswer());
                                        psInsertSurveyQuestionAnswer.setInt(2, answer.getSelectedCount());
                                        psInsertSurveyQuestionAnswer.setInt(3, question.getQuestionId());
                                        result = psInsertSurveyQuestionAnswer.executeUpdate() == 1;
                                    }
                                }

                                if(!result){
                                    break;
                                }
                            }

                            // 2.3.3 Delete answer
                            for (SurveyQuestionAnswer deleteAnswer : oldQuestion.getAnswers()) {
                                if(!result){
                                    break;
                                }
                                if (question.getAnswers().stream()
                                        .noneMatch(newQuestionAnswer -> newQuestionAnswer.getQuestionAnswerId() == deleteAnswer.getQuestionAnswerId())) {
                                    try (PreparedStatement psDeleteQuestionAnswer = connection.prepareStatement(DELETE_QUESTION_ANSWER_STATEMENT)) {
                                        psDeleteQuestionAnswer.setInt(1, deleteAnswer.getQuestionAnswerId());
                                        result = psDeleteQuestionAnswer.executeUpdate() == 1;
                                    }
                                }
                            }
                        }

                    } else {
                        // 2.3 New question
                        try (PreparedStatement psInsertSurveyQuestion = connection.prepareStatement(INSERT_SURVEY_QUESTION_STATEMENT, RETURN_GENERATED_KEYS)) {
                            psInsertSurveyQuestion.setBoolean(1, question.isSelectMultiple());
                            psInsertSurveyQuestion.setString(2, question.getFormulation());
                            psInsertSurveyQuestion.setInt(3, surveyId);
                            result = psInsertSurveyQuestion.executeUpdate() == 1;

                            // Get id of inserted question
                            ResultSet rsSurveyQuestionId = psInsertSurveyQuestion.getGeneratedKeys();
                            if (rsSurveyQuestionId.next()) {
                                int surveyQuestionId = rsSurveyQuestionId.getInt(1);

                                // Insert survey question answers
                                for (SurveyQuestionAnswer answer : question.getAnswers()) {
                                    try (PreparedStatement psInsertSurveyQuestionAnswer =
                                                 connection.prepareStatement(INSERT_SURVEY_QUESTION_ANSWER_STATEMENT)) {
                                        psInsertSurveyQuestionAnswer.setString(1, answer.getAnswer());
                                        psInsertSurveyQuestionAnswer.setInt(2, answer.getSelectedCount());
                                        psInsertSurveyQuestionAnswer.setInt(3, surveyQuestionId);
                                        result = psInsertSurveyQuestionAnswer.executeUpdate() == 1;
                                    }
                                    if(!result){
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if(!result){
                        break;
                    }
                }
            }

            // 2.4 Delete question
            for (SurveyQuestion deleteQuestion : oldQuestions) {
                if(!result){
                    break;
                }
                if (newQuestions.stream()
                        .noneMatch(newQuestion -> newQuestion.getQuestionId() == deleteQuestion.getQuestionId())) {
                    try (PreparedStatement psDeleteQuestion = connection.prepareStatement(DELETE_QUESTION_STATEMENT)) {
                        psDeleteQuestion.setInt(1, deleteQuestion.getQuestionId());
                        result = psDeleteQuestion.executeUpdate() == 1;
                    }
                }
            }


            if(result) {
                connection.commit();
            } else {
                connection.rollback();
                logger.error("Failed to update survey");
            }

            return result;

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error("Failed to rollback changes while update: {}", ex.getMessage());
                throw new DaoException("Failed to rollback changes while update", e);
            }
            logger.error("Failed to update survey with id_survey = {}: {}", surveyId, e.getMessage());
            throw new DaoException("Failed to update survey with id_survey = " + surveyId, e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }

    }
}
