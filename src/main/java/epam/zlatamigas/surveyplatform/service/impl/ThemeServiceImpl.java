package epam.zlatamigas.surveyplatform.service.impl;

import epam.zlatamigas.surveyplatform.exception.DaoException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.dao.impl.ThemeDaoImpl;
import epam.zlatamigas.surveyplatform.model.entity.Theme;
import epam.zlatamigas.surveyplatform.service.ThemeService;

import java.util.List;

public class ThemeServiceImpl implements ThemeService {

    private static ThemeServiceImpl instance = new ThemeServiceImpl();

    private ThemeServiceImpl(){
    }

    public static ThemeServiceImpl getInstance() {
        return instance;
    }



    @Override
    public List<Theme> findAll() throws ServiceException {
        ThemeDaoImpl themeDao = ThemeDaoImpl.getInstance();

        try {
            return themeDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Theme> findAllConfirmed() throws ServiceException {
        ThemeDaoImpl themeDao = ThemeDaoImpl.getInstance();

        try {
            return themeDao.findAllConfirmed();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
