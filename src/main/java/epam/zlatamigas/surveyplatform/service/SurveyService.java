package epam.zlatamigas.surveyplatform.service;

import epam.zlatamigas.surveyplatform.exception.DaoException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.entity.Survey;

import java.util.List;

public interface SurveyService {
    /**
     * Find preview info (name, description) about all started surveys.
     *
     * @return List of surveys with common info without question and answer data.
     * @throws ServiceException
     */
    List<Survey> findParticipantSurveysCommonInfo() throws ServiceException;

    /**
     * Find preview info (name, description, status) about all surveys created by user.
     *
     * @param userId User id to use for search.
     * @return List of surveys with common info without question and answer data.
     * @throws ServiceException
     */
    List<Survey> findCreatorSurveysCommonInfo(int userId) throws ServiceException;

    /**
     * Find info about specified survey: question info without statistics info.
     *
     * @param surveyId Survey id to use for search.
     * @return Surveys with common info with question and answer data without statistics.
     * @throws ServiceException
     */
    Survey findParticipantSurveyInfo(int surveyId) throws ServiceException;

    /**
     * Find info about specified survey: question info with statistics info.
     *
     * @param surveyId Survey id to use for search.
     * @return Surveys with common info with question and answer data with statistics.
     * @throws ServiceException
     */
    Survey findCreatorSurveyInfo(int surveyId) throws ServiceException;

    /**
     * Update survey results.
     *
     * @param survey Survey object with correct id for survey, questions and answers.
     *               Answer data must contain real id and selected_count = 1
     *               if participant selected the answer, otherwise selected_count = 0;
     * @return  true - if operation succeeded, else false.
     * @throws ServiceException
     */
    boolean updateParticipantSurveyResult(Survey survey) throws ServiceException;

    Survey findById(int id) throws ServiceException;

    boolean insert(Survey survey) throws ServiceException;
    boolean delete(int id) throws ServiceException;
    Survey update(Survey survey) throws ServiceException;
}
