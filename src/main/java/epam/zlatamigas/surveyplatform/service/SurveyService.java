package epam.zlatamigas.surveyplatform.service;

import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.entity.Survey;
import epam.zlatamigas.surveyplatform.model.entity.SurveyStatus;
import epam.zlatamigas.surveyplatform.model.entity.SurveyUserAttempt;

import java.util.List;
import java.util.Optional;

/**
 * Service for manipulating Survey data.
 */
public interface SurveyService {

    /**
     * Insert new survey.
     *
     * @param survey Survey, containing questions and answers.
     * @return True if new survey was inserted, false if survey already exists.
     * @throws ServiceException If a database access error occurs.
     */
    boolean insert(Survey survey) throws ServiceException;

    /**
     * Delete survey by id.
     *
     * @param surveyId Survey id.
     * @param creatorId ID of survey creator.
     * @return True if existing survey was deleted, otherwise false.
     * @throws ServiceException If a database access error occurs.
     */
    boolean delete(int surveyId, int creatorId) throws ServiceException;

    /**
     * Find info about specified survey: question info without statistics info.
     *
     * @param surveyId Survey id to use for search.
     * @return Surveys with common info with question and answer data without statistics.
     * @throws ServiceException If a database access error occurs.
     */
    Optional<Survey> findParticipantSurveyInfo(int surveyId) throws ServiceException;

    /**
     * Find info about specified survey: question info with statistics info.
     *
     * @param surveyId  Survey id to use for search.
     * @param creatorId Creator id to use for search.
     * @return Surveys with common info with question and answer data with statistics.
     * @throws ServiceException If a database access error occurs.
     */
    Optional<Survey> findCreatorSurveyInfo(int surveyId, int creatorId) throws ServiceException;

    /**
     * Find preview info (name, description, theme) about started surveys according to
     * requested filter and search parameters.
     *
     * @param filterThemeId  If > 0 use as theme id,
     *                       FILTER_THEMES_ALL (0) - all themes,
     *                       FILTER_THEMES_NONE (-1) - surveys without theme.
     * @param searchWordsStr Words contained in survey name. Case insensitive.
     *                       If array size is 0, then all survey names are acceptable.
     * @param orderTypeName  Order type: ASC - ascending, DESC - descending.
     * @return List of surveys with common info without question and answer data.
     * @throws ServiceException If a database access error occurs or if invalid filter parameters are passed.
     */
    List<Survey> findParticipantSurveysCommonInfoSearch(int filterThemeId,
                                                        String searchWordsStr,
                                                        String orderTypeName) throws ServiceException;

    /**
     * Find preview info (name, description, status, theme) about all surveys created by user according to
     * requested filter and search parameters.
     *
     * @param filterThemeId    If > 0 use as theme id,
     *                         FILTER_THEMES_ALL (0) - all themes,
     *                         FILTER_THEMES_NONE (-1) - surveys without theme.
     * @param searchWordsStr   Words contained in survey name. Case insensitive.
     *                         If array size is 0, then all survey names are acceptable.
     * @param orderTypeName    Order type: ASC - ascending, DESC - descending.
     * @param surveyStatusName Survey status: NOT_STARTED, STARTED, CLOSED.
     * @param userId           User id to use for search.
     * @return List of surveys with common info without question and answer data.
     * @throws ServiceException If a database access error occurs or if invalid filter parameters are passed.
     */
    List<Survey> findCreatorSurveysCommonInfoSearch(int filterThemeId,
                                                    String searchWordsStr,
                                                    String orderTypeName,
                                                    String surveyStatusName,
                                                    int userId) throws ServiceException;

    /**
     * Update participant survey result.
     *
     * @param surveyAttempt Survey attempt, containing User with actual ID, Survey with
     *                      list of questions with info about user answers.
     * @return True if all data was successfully updated.
     * @throws ServiceException If a database access error occurs.
     */
    boolean updateParticipantSurveyResult(SurveyUserAttempt surveyAttempt) throws ServiceException;

    /**
     * Change status of existing survey.
     *
     * @param surveyId ID of Survey to update.
     * @param status   Survey status to set.
     * @param creatorId ID of survey creator.
     * @return True if survey with surveyId exists.
     * @throws ServiceException If a database access error occurs.
     */
    boolean updateSurveyStatus(int surveyId, SurveyStatus status, int creatorId) throws ServiceException;

    /**
     * Update survey.
     *
     * @param survey Survey, containing questions and answers with valid IDs.
     * @param creatorId ID of survey creator.
     * @return True if survey and all its parts was updated successfully,otherwise false.
     * @throws ServiceException If a database access error occurs.
     */
    boolean update(Survey survey, int creatorId) throws ServiceException;
}
