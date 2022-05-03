package epam.zlatamigas.surveyplatform.service.impl;

import epam.zlatamigas.surveyplatform.exception.DaoException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.dao.SurveyDao;
import epam.zlatamigas.surveyplatform.model.dao.impl.SurveyDaoImpl;
import epam.zlatamigas.surveyplatform.model.entity.Survey;
import epam.zlatamigas.surveyplatform.service.SurveyService;

import java.util.List;

public class SurveyServiceImpl implements SurveyService {

    private static SurveyServiceImpl instance = new SurveyServiceImpl();

    private SurveyServiceImpl(){
    }

    public static SurveyServiceImpl getInstance() {
        return instance;
    }


    @Override
    public List<Survey> findParticipantSurveysCommonInfo() throws ServiceException {

        SurveyDao surveyDao = SurveyDaoImpl.getInstance();

        try {
            return surveyDao.findParticipantSurveysCommonInfo();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Survey> findCreatorSurveysCommonInfo(int userId) throws ServiceException {
        SurveyDao surveyDao = SurveyDaoImpl.getInstance();

        try {
            return surveyDao.findCreatorSurveysCommonInfo(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Survey findParticipantSurveyInfo(int surveyId) throws ServiceException {
        return null;
    }

    @Override
    public Survey findCreatorSurveyInfo(int surveyId) throws ServiceException {
        return null;
    }

    @Override
    public boolean updateParticipantSurveyResult(Survey survey) throws ServiceException {
        return false;
    }

    @Override
    public Survey findById(int id) throws ServiceException {
        SurveyDaoImpl surveyDao = SurveyDaoImpl.getInstance();

        try {
            return surveyDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
