package epam.zlatamigas.surveyplatform.service;

import epam.zlatamigas.surveyplatform.exception.DaoException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.entity.Survey;
import epam.zlatamigas.surveyplatform.model.entity.SurveyStatus;
import epam.zlatamigas.surveyplatform.model.entity.SurveyUserAttempt;
import epam.zlatamigas.surveyplatform.model.entity.Theme;

import java.util.List;
import java.util.Optional;

public interface SurveyService {

    /**
     * Find preview info (name, description) about started surveys according to parameters.
     *
     * @param filterThemeId  If theme id > 0 use as theme_id, 0 - all themes, -1 - column theme_id contains null.
     * @param searchWordsStr Words contained in survey_name. Case insensitive. If array size is 0, then all survey names are acceptable.
     * @param orderTypeName  Order type: ASC - ascending, DESC - descending.
     * @return List of surveys with common info without question and answer data.
     * @throws DaoException
     */
    List<Survey> findParticipantSurveysCommonInfoSearch(int filterThemeId, String searchWordsStr, String orderTypeName) throws ServiceException;

    /**
     * Find preview info (name, description, status) about all surveys created by user.
     *
     * @param filterThemeId  If theme id > 0 use as theme_id, 0 - all themes, -1 - column theme_id contains null.
     * @param searchWordsStr Words contained in survey_name. Case insensitive. If array size is 0, then all survey names are acceptable.
     * @param orderTypeName  Order type: ASC - ascending, DESC - descending.
     * @param userId User id to use for search.
     * @return List of surveys with common info without question and answer data.
     * @throws ServiceException
     */
    List<Survey> findCreatorSurveysCommonInfoSearch(int filterThemeId, String searchWordsStr, String orderTypeName, int userId) throws ServiceException;

    /**
     * Find info about specified survey: question info without statistics info.
     *
     * @param surveyId Survey id to use for search.
     * @return Surveys with common info with question and answer data without statistics.
     * @throws ServiceException
     */
    Optional<Survey> findParticipantSurveyInfo(int surveyId) throws ServiceException;

    /**
     * Find info about specified survey: question info with statistics info.
     *
     * @param surveyId Survey id to use for search.
     * @return Surveys with common info with question and answer data with statistics.
     * @throws ServiceException
     */
    Optional<Survey> findCreatorSurveyInfo(int surveyId) throws ServiceException;


    boolean updateParticipantSurveyResult(SurveyUserAttempt surveyAttempt) throws ServiceException;

    boolean updateSurveyStatus(int surveyId, SurveyStatus status) throws ServiceException;


    boolean insert(Survey survey) throws ServiceException;

    boolean delete(int id) throws ServiceException;

    Optional<Survey> update(Survey survey) throws ServiceException;
}
