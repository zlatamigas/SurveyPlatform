package epam.zlatamigas.surveyplatform.service.impl;

import epam.zlatamigas.surveyplatform.exception.DaoException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.dao.impl.ThemeDaoImpl;
import epam.zlatamigas.surveyplatform.model.entity.Theme;
import epam.zlatamigas.surveyplatform.model.entity.ThemeStatus;
import epam.zlatamigas.surveyplatform.service.ThemeService;

import java.util.List;

import static epam.zlatamigas.surveyplatform.model.entity.ThemeStatus.*;

public class ThemeServiceImpl implements ThemeService {

    private static ThemeServiceImpl instance = new ThemeServiceImpl();
    private static ThemeDaoImpl themeDao;

    private ThemeServiceImpl(){
        themeDao = ThemeDaoImpl.getInstance();
    }

    public static ThemeServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<Theme> findAllConfirmed() throws ServiceException {
        try {
            return themeDao.findWithThemeStatus(CONFIRMED);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Theme> findAllWaiting() throws ServiceException {
        try {
            return themeDao.findWithThemeStatus(WAITING);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean confirmTheme(int themeId) throws ServiceException {
        try {
            return themeDao.updateThemeStatus(themeId, CONFIRMED);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean insertConfirmedTheme(String themeName) throws ServiceException {

        if(themeName == null){
            return false;
        }

        // TODO: validate theme

        Theme theme = new Theme.ThemeBuilder()
                .setThemeName(themeName)
                .setThemeStatus(CONFIRMED)
                .getTheme();
        try {
            return themeDao.insert(theme);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean insertWaitingTheme(String themeName) throws ServiceException {

        if(themeName == null){
            return false;
        }

        // TODO: validate theme

        Theme theme = new Theme.ThemeBuilder()
                .setThemeName(themeName)
                .setThemeStatus(WAITING)
                .getTheme();
        try {
            return themeDao.insert(theme);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean delete(int themeId) throws ServiceException {
        try {
            return themeDao.delete(themeId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

}
