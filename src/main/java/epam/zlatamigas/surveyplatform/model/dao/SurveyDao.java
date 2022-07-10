package epam.zlatamigas.surveyplatform.model.dao;

import epam.zlatamigas.surveyplatform.exception.DaoException;
import epam.zlatamigas.surveyplatform.model.entity.Survey;
import epam.zlatamigas.surveyplatform.model.entity.SurveyStatus;
import epam.zlatamigas.surveyplatform.model.entity.SurveyUserAttempt;

import java.util.List;
import java.util.Optional;

/**
 * DAO for manipulating Survey data in tables: surveys, questions, question_answers.
 */
public interface SurveyDao {

    int FILTER_THEMES_ALL = 0;
    int FILTER_THEMES_NONE = -1;

    /**
     * Find info about specified survey: question info without statistics info.
     *
     * @param surveyId Survey id to use for search.
     * @return Surveys with common info with question and answer data without statistics.
     * @throws DaoException
     */
    Optional<Survey> findParticipantSurveyInfo(int surveyId) throws DaoException;

    /**
     * Find info about specified survey: question info with statistics info.
     *
     * @param surveyId Survey id to use for search.
     * @return Surveys with common info with question and answer data with statistics.
     * @throws DaoException
     */
    Optional<Survey> findCreatorSurveyInfo(int surveyId) throws DaoException;

    /**
     * Find preview info (name, description, theme) about started surveys according to requested filter and search parameters.
     *
     * @param filterThemeId If > 0 use as theme_id, FILTER_THEMES_ALL (0) - all themes, FILTER_THEMES_NONE (-1) - column theme_id contains null.
     * @param searchWords   Words contained in survey_name. Case insensitive. If array size is 0, then all survey names are acceptable.
     * @param orderType     Order type: ASC - ascending, DESC - descending.
     * @return List of surveys with common info without question and answer data.
     * @throws DaoException
     */
    List<Survey> findParticipantSurveysCommonInfoSearch(int filterThemeId, String[] searchWords, DbOrderType orderType) throws DaoException;

    /**
     * Find preview info (name, description, status, theme) about all surveys created by user according to requested filter and search parameters.
     *
     * @param filterThemeId If > 0 use as theme_id, FILTER_THEMES_ALL (0) - all themes, FILTER_THEMES_NONE (-1) - column theme_id contains null.
     * @param searchWords   Words contained in survey_name. Case insensitive. If array size is 0, then all survey names are acceptable.
     * @param orderType     Order type: ASC - ascending, DESC - descending.
     * @param surveyStatus  Optional.empty() - if search all, otherwise search of specified UserStatus.
     * @param userId        User id to use for search.
     * @return List of surveys with common info without question and answer data.
     * @throws DaoException
     */
    List<Survey> findCreatorSurveysCommonInfoSearch(int filterThemeId, String[] searchWords, DbOrderType orderType, Optional<SurveyStatus> surveyStatus, int userId) throws DaoException;

    /**
     * Update participant survey result.
     *
     * @param surveyAttempt Survey attempt, containing User with actual ID, Survey with
     *                      list of questions with info about user answers.
     * @return True if all data was successfully updated.
     * @throws DaoException
     */
    boolean updateParticipantSurveyResult(SurveyUserAttempt surveyAttempt) throws DaoException;

    /**
     * Change status of existing survey.
     *
     * @param surveyId ID of Survey to update.
     * @param status   Survey status to set.
     * @return True if survey with surveyId exists.
     * @throws DaoException
     */
    boolean updateSurveyStatus(int surveyId, SurveyStatus status) throws DaoException;
}
