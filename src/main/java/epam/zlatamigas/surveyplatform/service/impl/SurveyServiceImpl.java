package epam.zlatamigas.surveyplatform.service.impl;

import epam.zlatamigas.surveyplatform.exception.DaoException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.dao.DbOrderType;
import epam.zlatamigas.surveyplatform.model.dao.SurveyDao;
import epam.zlatamigas.surveyplatform.model.dao.impl.SurveyDaoImpl;
import epam.zlatamigas.surveyplatform.model.entity.Survey;
import epam.zlatamigas.surveyplatform.model.entity.SurveyStatus;
import epam.zlatamigas.surveyplatform.model.entity.SurveyUserAttempt;
import epam.zlatamigas.surveyplatform.model.entity.Theme;
import epam.zlatamigas.surveyplatform.service.SurveyService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SurveyServiceImpl implements SurveyService {

    private static final String SEARCH_WORDS_DELIMITER = " ";

    private static SurveyServiceImpl instance = new SurveyServiceImpl();

    private static SurveyDaoImpl surveyDao;

    private SurveyServiceImpl() {
        surveyDao = SurveyDaoImpl.getInstance();
    }

    public static SurveyServiceImpl getInstance() {
        return instance;
    }


    @Override
    public List<Survey> findParticipantSurveysCommonInfoSearch(int filterThemeId, String searchWordsStr, String orderTypeName) throws ServiceException {

        String[] searchWords = Arrays.stream(searchWordsStr
                .split(SEARCH_WORDS_DELIMITER))
                .filter(s -> !s.isBlank())
                .toArray(String[]::new);
        DbOrderType orderType = DbOrderType.valueOf(orderTypeName);

        try {
            return surveyDao.findParticipantSurveysCommonInfoSearch(filterThemeId, searchWords, orderType);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Survey> findCreatorSurveysCommonInfo(int userId) throws ServiceException {

        try {
            return surveyDao.findCreatorSurveysCommonInfo(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Survey> findParticipantSurveyInfo(int surveyId) throws ServiceException {

        try {
            return surveyDao.findParticipantSurveyInfo(surveyId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Survey> findCreatorSurveyInfo(int surveyId) throws ServiceException {

        try {
            return surveyDao.findCreatorSurveyInfo(surveyId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateParticipantSurveyResult(SurveyUserAttempt surveyAttempt) throws ServiceException {

        try {
            return surveyDao.updateParticipantSurveyResult(surveyAttempt);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateSurveyStatus(int surveyId, SurveyStatus status) throws ServiceException {

        try {
            return surveyDao.updateSurveyStatus(surveyId, status);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean insert(Survey survey) throws ServiceException {

        try {
            return surveyDao.insert(survey);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean delete(int id) throws ServiceException {
        SurveyDaoImpl surveyDao = SurveyDaoImpl.getInstance();

        try {
            return surveyDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Survey> update(Survey survey) throws ServiceException {
        SurveyDaoImpl surveyDao = SurveyDaoImpl.getInstance();

        try {
            return surveyDao.update(survey);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
