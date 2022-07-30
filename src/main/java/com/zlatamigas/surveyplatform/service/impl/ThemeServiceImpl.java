package com.zlatamigas.surveyplatform.service.impl;

import com.zlatamigas.surveyplatform.exception.DaoException;
import com.zlatamigas.surveyplatform.exception.ServiceException;
import com.zlatamigas.surveyplatform.model.dao.DbOrderType;
import com.zlatamigas.surveyplatform.model.dao.impl.ThemeDaoImpl;
import com.zlatamigas.surveyplatform.model.entity.Theme;
import com.zlatamigas.surveyplatform.service.ThemeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

import static com.zlatamigas.surveyplatform.model.dao.impl.ThemeDaoImpl.FILTER_THEMES_CONFIRMED;
import static com.zlatamigas.surveyplatform.model.entity.ThemeStatus.CONFIRMED;
import static com.zlatamigas.surveyplatform.model.entity.ThemeStatus.WAITING;
import static com.zlatamigas.surveyplatform.util.search.SearchParameter.DEFAULT_SEARCH_WORDS_DELIMITER;

public class ThemeServiceImpl implements ThemeService {

    private static final Logger logger = LogManager.getLogger();

    private static ThemeServiceImpl instance = new ThemeServiceImpl();
    private final ThemeDaoImpl themeDao;

    private ThemeServiceImpl() {
        themeDao = ThemeDaoImpl.getInstance();
    }

    public static ThemeServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean insertConfirmedTheme(String themeName) throws ServiceException {

        if (themeName == null) {
            logger.error("Passed null as themeName");
            return false;
        }

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

        if (themeName == null) {
            logger.error("Passed null as themeName");
            return false;
        }

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
    public List<Theme> findConfirmedSearch(String searchWordsStr, String orderTypeName) throws ServiceException {

        String[] searchWords;
        if (searchWordsStr != null && !searchWordsStr.isBlank()) {
            searchWords = Arrays.stream(searchWordsStr
                    .split(DEFAULT_SEARCH_WORDS_DELIMITER))
                    .filter(s -> !s.isBlank())
                    .toArray(String[]::new);
        } else {
            searchWords = new String[]{};
            logger.warn("Set default searchWords parameter");
        }

        DbOrderType orderType;
        try {
            if(orderTypeName != null){
                orderType = DbOrderType.valueOf(orderTypeName.toUpperCase());
            } else {
                orderType = DbOrderType.ASC;
                logger.warn("Set default orderType parameter");
            }
        } catch (IllegalArgumentException e) {
            orderType = DbOrderType.ASC;
            logger.warn("Set default orderType parameter");
        }

        try {
            return themeDao.findWithThemeStatusSearch(FILTER_THEMES_CONFIRMED, searchWords, orderType);
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
}
