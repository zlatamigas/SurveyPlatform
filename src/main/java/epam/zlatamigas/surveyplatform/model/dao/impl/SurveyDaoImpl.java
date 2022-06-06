package epam.zlatamigas.surveyplatform.model.dao.impl;

import epam.zlatamigas.surveyplatform.exception.DaoException;
import epam.zlatamigas.surveyplatform.model.connection.ConnectionPool;
import epam.zlatamigas.surveyplatform.model.dao.BaseDao;
import epam.zlatamigas.surveyplatform.model.dao.SurveyDao;
import epam.zlatamigas.surveyplatform.model.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static epam.zlatamigas.surveyplatform.model.dao.DbTableInfo.*;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class SurveyDaoImpl implements BaseDao<Survey>, SurveyDao {

    private static final Logger logger = LogManager.getLogger();

    //Insert survey info statements

    private static final String INSERT_SURVEY_STATEMENT =
            "INSERT INTO surveys(survey_name, survey_description, survey_status, theme_id, creator_id) VALUES (?,?,?,?,?)";
    private static final String INSERT_SURVEY_QUESTION_STATEMENT =
            "INSERT INTO questions(select_multiple, formulation, survey_id) VALUES (?,?,?)";
    private static final String INSERT_SURVEY_QUESTION_ANSWER_STATEMENT =
            "INSERT INTO question_answers(answer, selected_count, question_id) VALUES (?,?,?)";


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


    // Cascade delete for survey, questions, answers

    private static final String DELETE_SURVEY_STATEMENT = "DELETE FROM surveys WHERE id_survey = ?";
    private static final String DELETE_QUESTION_STATEMENT = "DELETE FROM questions WHERE id_question = ?";
    private static final String DELETE_QUESTION_ANSWER_STATEMENT = "DELETE FROM question_answer WHERE id_question_answer = ?";


    // Find survey info statements

    private static final String FIND_ALL_SURVEYS_STATEMENT =
            "SELECT * FROM surveys";
    private static final String FIND_SURVEY_BY_ID_STATEMENT =
            "SELECT id_survey, survey_name, survey_status, survey_description, theme_id, theme_name, theme_status FROM surveys INNER JOIN themes on theme_id = id_theme WHERE id_survey = ?";
    private static final String FIND_SURVEY_QUESTION_BY_ID_STATEMENT =
            "SELECT * FROM questions WHERE id_question = ?";
    private static final String FIND_SURVEY_QUESTIONS_BY_SURVEY_ID_STATEMENT =
            "SELECT * FROM questions WHERE survey_id = ?";
    private static final String FIND_SURVEY_QUESTION_ANSWER_BY_ID_STATEMENT =
            "SELECT * FROM question_answers WHERE id_question_answer = ?";
    private static final String FIND_SURVEY_QUESTION_ANSWERS_BY_SURVEY_ID_STATEMENT =
            "SELECT * FROM question_answers WHERE question_id = ?";
    private static final String FIND_SURVEY_ID_BY_NAME_STATEMENT =
            "SELECT id_survey FROM surveys WHERE survey_name = ?";
    private static final String FIND_SURVEY_QUESTION_ID_BY_FORMULATION_AND_SURVEY_ID_STATEMENT =
            "SELECT id_question FROM questions WHERE formulation = ? AND survey_id = ?";

    private static final String FIND_PARTICIPANT_SURVEY_COMMON_INFO_BY_ID_STATEMENT =
            "SELECT survey_name, survey_description, theme_id FROM surveys WHERE id_survey = ?";
    private static final String FIND_PARTICIPANT_SURVEYS_COMMON_INFO_STATEMENT =
            "SELECT id_survey, survey_name, survey_description, theme_id, theme_name, theme_status FROM surveys INNER JOIN themes on theme_id = id_theme WHERE survey_status = 'STARTED'";
    private static final String FIND_CREATOR_SURVEYS_COMMON_INFO_STATEMENT =
            "SELECT id_survey, survey_name, survey_description, theme_id, theme_name, theme_status, survey_status FROM surveys INNER JOIN themes on theme_id = id_theme WHERE creator_id = ?";
    private static final String FIND_PARTICIPANT_SURVEY_QUESTION_ANSWERS_BY_SURVEY_ID_STATEMENT =
            "SELECT id_question_answer, answer FROM question_answers WHERE question_id = ?";

    //SurveyDaoImpl instance

    private static SurveyDaoImpl instance;

    private SurveyDaoImpl() {
    }

    public static SurveyDaoImpl getInstance() {
        if (instance == null) {
            instance = new SurveyDaoImpl();
        }
        return instance;
    }

    @Override
    public boolean insert(Survey survey) throws DaoException {

        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);

            // Insert new survey data
            try (PreparedStatement psInsertSurvey = connection.prepareStatement(INSERT_SURVEY_STATEMENT, RETURN_GENERATED_KEYS)) {
                psInsertSurvey.setString(1, survey.getName());
                psInsertSurvey.setString(2, survey.getDescription());
                psInsertSurvey.setString(3, survey.getStatus().name());
                psInsertSurvey.setInt(4, survey.getTheme().getThemeId());
                psInsertSurvey.setInt(5, survey.getCreator().getUserId());
                psInsertSurvey.executeUpdate();

                // Get id of inserted survey
                ResultSet rsSurveyId = psInsertSurvey.getGeneratedKeys();
                if (rsSurveyId.next()) {
                    int surveyId = rsSurveyId.getInt(1);

                    // Insert survey questions
                    for (SurveyQuestion question : survey.getQuestions()) {
                        try (PreparedStatement psInsertSurveyQuestion = connection.prepareStatement(INSERT_SURVEY_QUESTION_STATEMENT, RETURN_GENERATED_KEYS)) {
                            psInsertSurveyQuestion.setBoolean(1, question.isSelectMultiple());
                            psInsertSurveyQuestion.setString(2, question.getFormulation());
                            psInsertSurveyQuestion.setInt(3, surveyId);
                            psInsertSurveyQuestion.executeUpdate();

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
                                        psInsertSurveyQuestionAnswer.executeUpdate();
                                    }
                                }
                            }
                        }
                    }
                }
            }

            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error("Error while rollback changes: " + ex.getMessage());
                throw new DaoException("Error while rollback changes: " + ex.getMessage(), e);
            }
            logger.error("Error while executing insert: " + e.getMessage());
            throw new DaoException("Error while executing insert: " + e.getMessage(), e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    logger.error("Error while closing connection: " + ex.getMessage());
                }
            }
        }

        return true;
    }

    @Override
    public boolean delete(int id) throws DaoException {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_SURVEY_STATEMENT)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error while executing delete: " + e.getMessage());
            throw new DaoException("Error while executing delete: " + e.getMessage(), e);
        }

        return true;
    }

    // NEW
    @Override
    public Survey update(Survey survey) throws DaoException {

        Survey oldSurvey = findById(survey.getSurveyId());
        int surveyId = oldSurvey.getSurveyId();

        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);

            // 1. Update common survey data
            if (survey.getName() != oldSurvey.getName()
                    || survey.getDescription() != oldSurvey.getDescription()
                    || survey.getStatus().name() != oldSurvey.getStatus().name()
                    || survey.getTheme().getThemeId() != oldSurvey.getTheme().getThemeId()
                    || survey.getCreator().getUserId() != oldSurvey.getCreator().getUserId()) {

                PreparedStatement psInsertSurvey =
                        connection.prepareStatement(UPDATE_SURVEY_STATEMENT, RETURN_GENERATED_KEYS);
                psInsertSurvey.setString(1, survey.getName());
                psInsertSurvey.setString(2, survey.getDescription());
                psInsertSurvey.setString(3, survey.getStatus().name());
                psInsertSurvey.setInt(4, survey.getTheme().getThemeId());
                psInsertSurvey.setInt(5, survey.getCreator().getUserId());
                psInsertSurvey.setInt(6, surveyId);
                psInsertSurvey.executeUpdate();
            }


            // 2. Sort questions
            List<SurveyQuestion> oldQuestions = oldSurvey.getQuestions();
            List<SurveyQuestion> newQuestions = survey.getQuestions();

            for (SurveyQuestion newQuestion : newQuestions) {
                // 2.1. Search for question with same id in old questions (result: updated or new)
                Optional<SurveyQuestion> updateQuestionOptional = oldQuestions.stream()
                        .filter(oldQuestion -> oldQuestion.getQuestionId() == newQuestion.getQuestionId())
                        .findFirst();

                // Updated
                if(updateQuestionOptional.isPresent()){

                    // TODO - Update
                    // 0 - same common and answers
                    // 1 - same common, diff answers
                    // 2 - diff common, common answers
                    // 3 - diff common and answers

                } else {
                // New

                    // TODO - Add new

                }
            }

            // Deleted
            oldQuestions.stream()
                    .filter(question ->  newQuestions.stream()
                            .noneMatch(newQuestion -> newQuestion.getQuestionId() == question.getQuestionId()))
                    .forEach(deleteQuestion -> {
                        // TODO - Delete
                    });


            // ---------------------------------
            // Update survey questions
            for (SurveyQuestion question : survey.getQuestions()) {

                int surveyQuestionId = question.getQuestionId();
                PreparedStatement psInsertSurveyQuestion =
                        connection.prepareStatement(UPDATE_SURVEY_QUESTION_STATEMENT, RETURN_GENERATED_KEYS);
                psInsertSurveyQuestion.setBoolean(1, question.isSelectMultiple());
                psInsertSurveyQuestion.setString(2, question.getFormulation());
                psInsertSurveyQuestion.setInt(3, surveyId);
                psInsertSurveyQuestion.setInt(4, surveyQuestionId);
                psInsertSurveyQuestion.executeUpdate();

                // Insert survey question answers
                for (SurveyQuestionAnswer answer : question.getAnswers()) {

                    PreparedStatement psInsertSurveyQuestionAnswer =
                            connection.prepareStatement(UPDATE_SURVEY_QUESTION_ANSWER_STATEMENT, RETURN_GENERATED_KEYS);
                    psInsertSurveyQuestionAnswer.setString(1, answer.getAnswer());
                    psInsertSurveyQuestionAnswer.setInt(2, answer.getSelectedCount());
                    psInsertSurveyQuestionAnswer.setInt(3, surveyQuestionId);
                    psInsertSurveyQuestionAnswer.setInt(4, answer.getQuestionAnswerId());
                    psInsertSurveyQuestionAnswer.executeUpdate();
                }
            }
            // ---------------------------------

            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error(ex.getMessage());
            }
            logger.error(e.getMessage());
            throw new DaoException(e.getMessage(), e);
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException ex) {
                    logger.error(ex.getMessage());
                }
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }

        return oldSurvey;
    }

    // OLD
//    @Override
//    public Survey update(Survey survey) throws DaoException {
//
//        Survey oldSurvey = findById(survey.getSurveyId());
//        int surveyId = oldSurvey.getSurveyId();
//
//        Connection connection = null;
//        try {
//            connection = ConnectionPool.getInstance().getConnection();
//            connection.setAutoCommit(false);
//
//            // Update survey data
//            PreparedStatement psInsertSurvey =
//                    connection.prepareStatement(UPDATE_SURVEY_STATEMENT, RETURN_GENERATED_KEYS);
//            psInsertSurvey.setString(1, survey.getName());
//            psInsertSurvey.setString(2, survey.getDescription());
//            psInsertSurvey.setString(3, survey.getStatus().name());
//            psInsertSurvey.setInt(4, survey.getTheme().getThemeId());
//            psInsertSurvey.setInt(5, survey.getCreator().getUserId());
//            psInsertSurvey.setInt(6, surveyId);
//            psInsertSurvey.executeUpdate();
//
//            // Sort questions
//            List<SurveyQuestion> oldQuestions = oldSurvey.getQuestions();
//            List<SurveyQuestion> newQuestions = survey.getQuestions();
//
//            List<SurveyQuestion> updatedQuestions = new ArrayList<>();
//            List<SurveyQuestion> createdQuestions = new ArrayList<>();
//            List<SurveyQuestion> deletedQuestions = new ArrayList<>();
//
//            for(SurveyQuestion question : oldQuestions){
//
//            }
//
//            // Update survey questions
//
//            for (SurveyQuestion question : survey.getQuestions()) {
//
//                int surveyQuestionId = question.getQuestionId();
//                PreparedStatement psInsertSurveyQuestion =
//                        connection.prepareStatement(UPDATE_SURVEY_QUESTION_STATEMENT, RETURN_GENERATED_KEYS);
//                psInsertSurveyQuestion.setBoolean(1, question.isSelectMultiple());
//                psInsertSurveyQuestion.setString(2, question.getFormulation());
//                psInsertSurveyQuestion.setInt(3, surveyId);
//                psInsertSurveyQuestion.setInt(4, surveyQuestionId);
//                psInsertSurveyQuestion.executeUpdate();
//
//                // Insert survey question answers
//                for (SurveyQuestionAnswer answer : question.getAnswers()) {
//
//                    PreparedStatement psInsertSurveyQuestionAnswer =
//                            connection.prepareStatement(UPDATE_SURVEY_QUESTION_ANSWER_STATEMENT, RETURN_GENERATED_KEYS);
//                    psInsertSurveyQuestionAnswer.setString(1, answer.getAnswer());
//                    psInsertSurveyQuestionAnswer.setInt(2, answer.getSelectedCount());
//                    psInsertSurveyQuestionAnswer.setInt(3, surveyQuestionId);
//                    psInsertSurveyQuestionAnswer.setInt(4, answer.getQuestionAnswerId());
//                    psInsertSurveyQuestionAnswer.executeUpdate();
//                }
//            }
//
//            connection.commit();
//
//        } catch (SQLException e) {
//            try {
//                connection.rollback();
//            } catch (SQLException ex) {
//                logger.error(ex.getMessage());
//            }
//            logger.error(e.getMessage());
//            throw new DaoException(e.getMessage(), e);
//        } finally {
//            if (connection != null) {
//                try {
//                    connection.setAutoCommit(true);
//                } catch (SQLException ex) {
//                    logger.error(ex.getMessage());
//                }
//                ConnectionPool.getInstance().releaseConnection(connection);
//            }
//        }
//
//        return oldSurvey;
//    }

    @Override
    public Survey findById(int id) throws DaoException {
        Survey survey = null;

        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();

            //Find survey

            PreparedStatement psFindSurvey = connection.prepareStatement(FIND_SURVEY_BY_ID_STATEMENT);
            psFindSurvey.setInt(1, id);
            ResultSet rsSurvey = psFindSurvey.executeQuery();

            if (rsSurvey.next()) {

                Theme theme = new Theme(
                        rsSurvey.getInt(SURVEYS_TABLE_FK_THEME_ID_COLUMN),
                        rsSurvey.getString(THEMES_TABLE_NAME_COLUMN),
                        ThemeStatus.valueOf(rsSurvey.getString(THEMES_TABLE_STATUS_COLUMN)));


                survey = new Survey.SurveyBuilder()
                        .setSurveyId(rsSurvey.getInt(SURVEYS_TABLE_PK_COLUMN))
                        .setName(rsSurvey.getString(SURVEYS_TABLE_NAME_COLUMN))
                        .setDescription(rsSurvey.getString(SURVEYS_TABLE_DESCRIPTION_COLUMN))
                        .setStatus(SurveyStatus.valueOf(rsSurvey.getString(SURVEYS_TABLE_STATUS_COLUMN)))
                        .setTheme(theme)
//                        .setCreator(creator)
                        .getSurvey();

                //Find survey questions

                PreparedStatement psFindSurveyQuestion = connection.prepareStatement(FIND_SURVEY_QUESTIONS_BY_SURVEY_ID_STATEMENT);
                psFindSurveyQuestion.setInt(1, id);
                ResultSet rsSurveyQuestion = psFindSurveyQuestion.executeQuery();

                SurveyQuestion question;
                int questionId;
                while (rsSurveyQuestion.next()) {
                    question = new SurveyQuestion();

                    questionId = rsSurveyQuestion.getInt(QUESTIONS_TABLE_PK_COLUMN);
                    question.setQuestionId(questionId);
                    question.setSelectMultiple(rsSurveyQuestion.getBoolean(QUESTIONS_TABLE_SELECT_MULTIPLE_COLUMN));
                    question.setFormulation(rsSurveyQuestion.getString(QUESTIONS_TABLE_FORMULATION_COLUMN));

                    //Find survey question answers

                    PreparedStatement psFindSurveyQuestionAnswer = connection.prepareStatement(FIND_SURVEY_QUESTION_ANSWERS_BY_SURVEY_ID_STATEMENT);
                    psFindSurveyQuestionAnswer.setInt(1, questionId);
                    ResultSet rsSurveyQuestionAnswer = psFindSurveyQuestionAnswer.executeQuery();

                    SurveyQuestionAnswer answer;
                    while (rsSurveyQuestionAnswer.next()) {
                        answer = new SurveyQuestionAnswer();
                        answer.setQuestionAnswerId(rsSurveyQuestionAnswer.getInt(ANSWERS_TABLE_PK_COLUMN));
                        answer.setAnswer(rsSurveyQuestionAnswer.getString(ANSWERS_TABLE_ANSWER_COLUMN));
                        answer.setSelectedCount(rsSurveyQuestionAnswer.getInt(ANSWERS_TABLE_SELECTED_COUNT_COLUMN));

                        question.addAnswer(answer);
                    }

                    survey.addQuestion(question);
                }
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException(e.getMessage(), e);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }

        return survey;
    }

    @Override
    public List<Survey> findAll() throws DaoException {
        List<Survey> surveys = new ArrayList<>();
        Survey survey = null;

        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();

            //Find survey

            PreparedStatement psFindSurvey = connection.prepareStatement(FIND_ALL_SURVEYS_STATEMENT);
            ResultSet rsSurvey = psFindSurvey.executeQuery();

            while (rsSurvey.next()) {

//                Theme theme = themeDao.findById(rsSurvey.getInt(SURVEYS_TABLE_FK_THEME_ID_COLUMN));
//                User creator = userDao.findById(rsSurvey.getInt(SURVEYS_TABLE_FK_CREATOR_ID_COLUMN));

                int id = rsSurvey.getInt(SURVEYS_TABLE_PK_COLUMN);
                survey = new Survey.SurveyBuilder()
                        .setSurveyId(id)
                        .setName(rsSurvey.getString(SURVEYS_TABLE_NAME_COLUMN))
                        .setDescription(rsSurvey.getString(SURVEYS_TABLE_DESCRIPTION_COLUMN))
                        .setStatus(SurveyStatus.valueOf(rsSurvey.getString(SURVEYS_TABLE_STATUS_COLUMN)))
//                        .setTheme(theme)
//                        .setCreator(creator)
                        .getSurvey();

                //Find survey questions

                PreparedStatement psFindSurveyQuestion = connection.prepareStatement(FIND_SURVEY_QUESTIONS_BY_SURVEY_ID_STATEMENT);
                psFindSurveyQuestion.setInt(1, id);
                ResultSet rsSurveyQuestion = psFindSurveyQuestion.executeQuery();

                SurveyQuestion question;
                int questionId;
                while (rsSurveyQuestion.next()) {
                    question = new SurveyQuestion();

                    questionId = rsSurveyQuestion.getInt(QUESTIONS_TABLE_PK_COLUMN);
                    question.setQuestionId(questionId);
                    question.setSelectMultiple(rsSurveyQuestion.getBoolean(QUESTIONS_TABLE_SELECT_MULTIPLE_COLUMN));
                    question.setFormulation(rsSurveyQuestion.getString(QUESTIONS_TABLE_FORMULATION_COLUMN));

                    //Find survey question answers

                    PreparedStatement psFindSurveyQuestionAnswer = connection.prepareStatement(FIND_SURVEY_QUESTION_ANSWERS_BY_SURVEY_ID_STATEMENT);
                    psFindSurveyQuestionAnswer.setInt(1, questionId);
                    ResultSet rsSurveyQuestionAnswer = psFindSurveyQuestionAnswer.executeQuery();

                    SurveyQuestionAnswer answer;
                    while (rsSurveyQuestionAnswer.next()) {
                        answer = new SurveyQuestionAnswer();
                        answer.setQuestionAnswerId(rsSurveyQuestionAnswer.getInt(ANSWERS_TABLE_PK_COLUMN));
                        answer.setAnswer(rsSurveyQuestionAnswer.getString(ANSWERS_TABLE_ANSWER_COLUMN));
                        answer.setSelectedCount(rsSurveyQuestionAnswer.getInt(ANSWERS_TABLE_SELECTED_COUNT_COLUMN));

                        question.addAnswer(answer);
                    }

                    survey.addQuestion(question);
                }

                surveys.add(survey);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException(e.getMessage(), e);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }

        return surveys;
    }

    @Override
    public List<Survey> findParticipantSurveysCommonInfo() throws DaoException {
        List<Survey> surveys = new ArrayList<>();
        Survey survey = null;

        try (Connection connection = ConnectionPool.getInstance().getConnection();) {

            PreparedStatement psFindSurvey = connection.prepareStatement(FIND_PARTICIPANT_SURVEYS_COMMON_INFO_STATEMENT);
            ResultSet rsSurvey = psFindSurvey.executeQuery();

            while (rsSurvey.next()) {

                Theme theme = new Theme(
                        rsSurvey.getInt(SURVEYS_TABLE_FK_THEME_ID_COLUMN),
                        rsSurvey.getString(THEMES_TABLE_NAME_COLUMN),
                        ThemeStatus.valueOf(rsSurvey.getString(THEMES_TABLE_STATUS_COLUMN)));

                survey = new Survey.SurveyBuilder()
                        .setSurveyId(rsSurvey.getInt(SURVEYS_TABLE_PK_COLUMN))
                        .setName(rsSurvey.getString(SURVEYS_TABLE_NAME_COLUMN))
                        .setDescription(rsSurvey.getString(SURVEYS_TABLE_DESCRIPTION_COLUMN))
                        .setTheme(theme)
                        .getSurvey();

                surveys.add(survey);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException(e.getMessage(), e);
        }

        return surveys;
    }

    @Override
    public List<Survey> findCreatorSurveysCommonInfo(int userId) throws DaoException {
        List<Survey> surveys = new ArrayList<>();
        Survey survey = null;

        try (Connection connection = ConnectionPool.getInstance().getConnection()) {

            PreparedStatement psFindSurvey = connection.prepareStatement(FIND_CREATOR_SURVEYS_COMMON_INFO_STATEMENT);
            psFindSurvey.setInt(1, userId);
            ResultSet rsSurvey = psFindSurvey.executeQuery();

            while (rsSurvey.next()) {

                Theme theme = new Theme(
                        rsSurvey.getInt(SURVEYS_TABLE_FK_THEME_ID_COLUMN),
                        rsSurvey.getString(THEMES_TABLE_NAME_COLUMN),
                        ThemeStatus.valueOf(rsSurvey.getString(THEMES_TABLE_STATUS_COLUMN)));

                survey = new Survey.SurveyBuilder()
                        .setSurveyId(rsSurvey.getInt(SURVEYS_TABLE_PK_COLUMN))
                        .setName(rsSurvey.getString(SURVEYS_TABLE_NAME_COLUMN))
                        .setDescription(rsSurvey.getString(SURVEYS_TABLE_DESCRIPTION_COLUMN))
                        .setStatus(SurveyStatus.valueOf(rsSurvey.getString(SURVEYS_TABLE_STATUS_COLUMN)))
                        .setTheme(theme)
                        .setCreator(new User.UserBuilder().setUserId(userId).getUser())
                        .getSurvey();

                surveys.add(survey);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException(e.getMessage(), e);
        }

        return surveys;
    }

    @Override
    public Survey findParticipantSurveyInfo(int surveyId) throws DaoException {
        Survey survey = null;

        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();

            //Find survey

            PreparedStatement psFindSurvey = connection.prepareStatement(FIND_PARTICIPANT_SURVEY_COMMON_INFO_BY_ID_STATEMENT);
            psFindSurvey.setInt(1, surveyId);
            ResultSet rsSurvey = psFindSurvey.executeQuery();

            if (rsSurvey.next()) {

//                Theme theme = themeDao.findById(rsSurvey.getInt(SURVEYS_TABLE_FK_THEME_ID_COLUMN));

                survey = new Survey.SurveyBuilder()
                        .setSurveyId(surveyId)
                        .setName(rsSurvey.getString(SURVEYS_TABLE_NAME_COLUMN))
                        .setDescription(rsSurvey.getString(SURVEYS_TABLE_DESCRIPTION_COLUMN))
//                        .setTheme(theme)
                        .getSurvey();

                //Find survey questions

                PreparedStatement psFindSurveyQuestion = connection.prepareStatement(FIND_SURVEY_QUESTIONS_BY_SURVEY_ID_STATEMENT);
                psFindSurveyQuestion.setInt(1, surveyId);
                ResultSet rsSurveyQuestion = psFindSurveyQuestion.executeQuery();

                SurveyQuestion question;
                int questionId;
                while (rsSurveyQuestion.next()) {
                    question = new SurveyQuestion();

                    questionId = rsSurveyQuestion.getInt(QUESTIONS_TABLE_PK_COLUMN);
                    question.setQuestionId(questionId);
                    question.setSelectMultiple(rsSurveyQuestion.getBoolean(QUESTIONS_TABLE_SELECT_MULTIPLE_COLUMN));
                    question.setFormulation(rsSurveyQuestion.getString(QUESTIONS_TABLE_FORMULATION_COLUMN));

                    //Find survey question answers

                    PreparedStatement psFindSurveyQuestionAnswer = connection.prepareStatement(FIND_PARTICIPANT_SURVEY_QUESTION_ANSWERS_BY_SURVEY_ID_STATEMENT);
                    psFindSurveyQuestionAnswer.setInt(1, questionId);
                    ResultSet rsSurveyQuestionAnswer = psFindSurveyQuestionAnswer.executeQuery();

                    SurveyQuestionAnswer answer;
                    while (rsSurveyQuestionAnswer.next()) {
                        answer = new SurveyQuestionAnswer();
                        answer.setQuestionAnswerId(rsSurveyQuestionAnswer.getInt(ANSWERS_TABLE_PK_COLUMN));
                        answer.setAnswer(rsSurveyQuestionAnswer.getString(ANSWERS_TABLE_ANSWER_COLUMN));
                        answer.setSelectedCount(0);

                        question.addAnswer(answer);
                    }

                    survey.addQuestion(question);
                }
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException(e.getMessage(), e);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }

        return survey;
    }

    @Override
    public Survey findCreatorSurveyInfo(int surveyId) throws DaoException {
        return findById(surveyId);
    }

    @Override
    public boolean updateParticipantSurveyResult(Survey survey) throws DaoException {
        //TODO
        return false;
    }

    @Override
    public boolean updateSurveyStatus(int surveyId, SurveyStatus status) throws DaoException {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement psInsertSurvey = connection.prepareStatement(UPDATE_SURVEY_STATUS_STATEMENT)) {

            psInsertSurvey.setString(1, status.name());
            psInsertSurvey.setInt(2, surveyId);

            return psInsertSurvey.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }
}
