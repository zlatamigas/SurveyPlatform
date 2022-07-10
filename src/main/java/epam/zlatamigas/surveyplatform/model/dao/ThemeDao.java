package epam.zlatamigas.surveyplatform.model.dao;

import epam.zlatamigas.surveyplatform.exception.DaoException;
import epam.zlatamigas.surveyplatform.model.entity.Theme;
import epam.zlatamigas.surveyplatform.model.entity.ThemeStatus;

import java.util.List;
import java.util.Optional;

/**
 * DAO for manipulating Theme data in table themes.
 */
public interface ThemeDao {

    int FILTER_THEMES_ALL = 0;
    int FILTER_THEMES_CONFIRMED = 1;
    int FILTER_THEMES_WAITING = 2;

    /**
     * Find theme by name.
     *
     * @param themeName Name to use.
     * @return Survey, if it exists, otherwise - Optional.empty().
     * @throws DaoException
     */
    Optional<Theme> findByName(String themeName) throws DaoException;

    /**
     * Find all themes with specified status. Used for fast info search.
     *
     * @param themeStatus ThemeStatus to use in search.
     * @return List of themes of specified status.
     * @throws DaoException
     */
    List<Theme> findWithThemeStatus(ThemeStatus themeStatus) throws DaoException;

    /**
     * Find themes according to requested filter and search parameters.
     *
     * @param themeStatusId FILTER_THEMES_ALL (0) - search through all themes,
     *                      FILTER_THEMES_CONFIRMED (1) - only confirmed,
     *                      FILTER_THEMES_WAITING (2) - only waiting.
     * @param searchWords   Words contained in theme_name. Case insensitive. If array size is 0, then all survey names are acceptable.
     * @param orderType     Order type: ASC - ascending, DESC - descending.
     * @return List of themes according to search and filter parameters.
     * @throws DaoException
     */
    List<Theme> findWithThemeStatusSearch(int themeStatusId, String[] searchWords, DbOrderType orderType) throws DaoException;

    /**
     * Change status of existing theme.
     *
     * @param themeId     ID of Theme to update.
     * @param themeStatus Theme status to set.
     * @return True if theme with themeId exists.
     * @throws DaoException
     */
    boolean updateThemeStatus(int themeId, ThemeStatus themeStatus) throws DaoException;
}
