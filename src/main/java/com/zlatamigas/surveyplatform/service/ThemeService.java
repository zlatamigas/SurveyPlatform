package com.zlatamigas.surveyplatform.service;

import com.zlatamigas.surveyplatform.exception.ServiceException;
import com.zlatamigas.surveyplatform.model.entity.Theme;

import java.util.List;

public interface ThemeService {

    /**
     * Insert new confirmed theme.
     *
     * @param themeName Theme name.
     * @return True if new theme was inserted, false if theme already exists.
     * @throws ServiceException If a database access error occurs.
     */
    boolean insertConfirmedTheme(String themeName) throws ServiceException;

    /**
     * Insert new waiting confirmation theme.
     *
     * @param themeName Theme name.
     * @return True if new theme was inserted, false if theme already exists.
     * @throws ServiceException If a database access error occurs.
     */
    boolean insertWaitingTheme(String themeName) throws ServiceException;

    /**
     * Delete theme by id.
     *
     * @param themeId Theme id.
     * @return True if existing theme was deleted, otherwise false.
     * @throws ServiceException If a database access error occurs.
     */
    boolean delete(int themeId) throws ServiceException;

    /**
     * Find all confirmed themes.
     *
     * @return List of confirmed themes.
     * @throws ServiceException If a database access error occurs.
     */
    List<Theme> findAllConfirmed() throws ServiceException;

    /**
     * Find all waiting confirmation themes.
     *
     * @return List of waiting confirmation themes.
     * @throws ServiceException If a database access error occurs.
     */
    List<Theme> findAllWaiting() throws ServiceException;

    /**
     * Find confirmed themes according to requested filter and search parameters.
     *
     * @param searchWordsStr Words contained in theme name. Case insensitive.
     *                       If array size is 0, then all survey names are acceptable.
     * @param orderTypeName  Order type: ASC - ascending, DESC - descending.
     * @return List of themes.
     * @throws ServiceException If a database access error occurs or if invalid filter parameters are passed.
     */
    List<Theme> findConfirmedSearch(String searchWordsStr,
                                    String orderTypeName) throws ServiceException;


    /**
     * Change status of theme from waiting to confirmed.
     *
     * @param themeId Theme id.
     * @return True if theme was updated, false if theme does not exist.
     * @throws ServiceException If a database access error occurs.
     */
    boolean confirmTheme(int themeId) throws ServiceException;
}
