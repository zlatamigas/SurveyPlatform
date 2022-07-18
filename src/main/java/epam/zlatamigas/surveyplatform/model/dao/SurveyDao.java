package epam.zlatamigas.surveyplatform.model.dao;

import epam.zlatamigas.surveyplatform.exception.DaoException;
import epam.zlatamigas.surveyplatform.model.entity.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * DAO for manipulating Survey data in tables: surveys, questions, question_answers.
 */
public interface SurveyDao extends BaseDao<Survey> {

    int FILTER_THEMES_ALL = 0;
    int FILTER_THEMES_NONE = -1;

    /**
     * Insert new survey.
     *
     * @param survey Survey, containing questions and answers.
     * @return True if new survey was inserted, false if survey already exists.
     * @throws DaoException If a database access error occurs.
     */
    @Override
    boolean insert(Survey survey) throws DaoException;

    /**
     * Delete survey by id.
     *
     * @param id Survey id.
     * @return True if existing survey was deleted, otherwise false.
     * @throws DaoException If a database access error occurs.
     */
    @Override
    boolean delete(int id) throws DaoException;

    /**
     * Unsupported find operation for survey. For find use methods:
     * {@link #findCreatorSurveyInfo(int)} or
     * {@link #findParticipantSurveyInfo(int)}.
     *
     * @throws DaoException Never.
     * @throws UnsupportedOperationException Always.
     * @deprecated Unsupported operation.
     */
    @Override
    Optional<Survey> findById(int id) throws DaoException;

    /**
     * Find info about specified survey: question info without statistics info.
     *
     * @param surveyId Survey id to use for search.
     * @return Surveys with common info with question and answer data without statistics.
     * @throws DaoException If a database access error occurs.
     */
    Optional<Survey> findParticipantSurveyInfo(int surveyId) throws DaoException;

    /**
     * Find info about specified survey: question info with statistics info.
     *
     * @param surveyId Survey id to use for search.
     * @param creatorId Creator id to use for search.
     * @return Surveys with common info with question and answer data with statistics.
     * @throws DaoException If a database access error occurs.
     */
    Optional<Survey> findCreatorSurveyInfo(int surveyId, int creatorId) throws DaoException;

    /**
     * Find preview info (name, description, theme) about started surveys according to
     * requested filter and search parameters.
     *
     * @param filterThemeId If > 0 use as theme_id,
     *                      FILTER_THEMES_ALL (0) - all themes,
     *                      FILTER_THEMES_NONE (-1) - column theme_id contains null.
     * @param searchWords   Words contained in survey_name. Case insensitive.
     *                      If array size is 0, then all survey names are acceptable.
     * @param orderType     Order type: ASC - ascending, DESC - descending.
     * @return List of surveys with common info without question and answer data.
     * @throws DaoException If a database access error occurs or if invalid filter parameters are passed.
     */
    List<Survey> findParticipantSurveysCommonInfoSearch(int filterThemeId,
                                                        String[] searchWords,
                                                        DbOrderType orderType) throws DaoException;

    /**
     * Find preview info (name, description, status, theme) about all surveys created by user according to
     * requested filter and search parameters.
     *
     * @param filterThemeId If > 0 use as theme_id,
     *                      FILTER_THEMES_ALL (0) - all themes,
     *                      FILTER_THEMES_NONE (-1) - column theme_id contains null.
     * @param searchWords   Words contained in survey_name. Case insensitive.
     *                      If array size is 0, then all survey names are acceptable.
     * @param orderType     Order type: ASC - ascending, DESC - descending.
     * @param surveyStatus  Optional.empty() - if search all, otherwise search of specified UserStatus.
     * @param userId        User id to use for search.
     * @return List of surveys with common info without question and answer data.
     * @throws DaoException If a database access error occurs or if invalid filter parameters are passed.
     */
    List<Survey> findCreatorSurveysCommonInfoSearch(int filterThemeId,
                                                    String[] searchWords,
                                                    DbOrderType orderType,
                                                    Optional<SurveyStatus> surveyStatus,
                                                    int userId) throws DaoException;

    /**
     * Change status of existing survey.
     *
     * @param surveyId ID of Survey to update.
     * @param status   Survey status to set.
     * @return True if survey with surveyId exists.
     * @throws DaoException If a database access error occurs.
     */
    boolean updateSurveyStatus(int surveyId, SurveyStatus status) throws DaoException;

    /**
     * Change status of existing survey to started.
     *
     * @param surveyId ID of Survey to update.
     * @param startDateTime   Survey start date and time.
     * @return True if survey with surveyId exists.
     * @throws DaoException If a database access error occurs.
     */
    boolean updateSurveyStarted(int surveyId, LocalDateTime startDateTime) throws DaoException;

    /**
     * Change status of existing survey to closed.
     *
     * @param surveyId ID of Survey to update.
     * @param closeDateTime   Survey close date and time.
     * @return True if survey with surveyId exists.
     * @throws DaoException If a database access error occurs.
     */
    boolean updateSurveyClosed(int surveyId, LocalDateTime closeDateTime) throws DaoException;

    /**
     * Update participant survey result.
     *
     * @param surveyAttempt Survey attempt, containing User with actual ID, Survey with
     *                      list of questions with info about user answers.
     * @return True if all data was successfully updated.
     * @throws DaoException If a database access error occurs.
     */
    boolean updateParticipantSurveyResult(SurveyUserAttempt surveyAttempt) throws DaoException;

    /**
     * Update survey.
     *
     * @param survey Survey, containing questions and answers with valid IDs.
     * @return True if survey and all its parts was updated successfully,otherwise false.
     * @throws DaoException If a database access error occurs.
     */
    @Override
    boolean update(Survey survey) throws DaoException;
}
