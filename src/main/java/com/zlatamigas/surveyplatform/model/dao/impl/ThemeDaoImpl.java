package com.zlatamigas.surveyplatform.model.dao.impl;

import com.zlatamigas.surveyplatform.exception.DaoException;
import com.zlatamigas.surveyplatform.model.connection.ConnectionPool;
import com.zlatamigas.surveyplatform.model.dao.BaseDao;
import com.zlatamigas.surveyplatform.model.dao.DbOrderType;
import com.zlatamigas.surveyplatform.model.dao.ThemeDao;
import com.zlatamigas.surveyplatform.model.entity.Theme;
import com.zlatamigas.surveyplatform.model.entity.ThemeStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.zlatamigas.surveyplatform.model.dao.DbTableInfo.*;

public class ThemeDaoImpl implements ThemeDao {

    private static final Logger logger = LogManager.getLogger();

    private static final String INSERT_STATEMENT
            = "INSERT INTO themes(theme_name, theme_status) VALUES(?,?)";
    private static final String UPDATE_STATEMENT
            = "UPDATE themes SET theme_name = ?, theme_status = ? WHERE id_theme = ?";
    private static final String UPDATE_THEME_STATUS_STATEMENT
            = "UPDATE themes SET theme_status = ? WHERE id_theme = ?";
    private static final String DELETE_STATEMENT
            = "DELETE FROM themes WHERE id_theme = ?";
    private static final String FIND_ALL_STATEMENT
            = "SELECT id_theme, theme_name, theme_status FROM themes ORDER BY theme_name";
    private static final String FIND_WITH_STATUS_STATEMENT
            = "SELECT id_theme, theme_name FROM themes where theme_status=? ORDER BY theme_name";
    private static final String FIND_BY_ID_STATEMENT
            = "SELECT theme_name, theme_status FROM themes WHERE id_theme = ?";
    private static final String FIND_BY_NAME_STATEMENT
            = "SELECT id_theme, theme_name, theme_status FROM themes WHERE theme_name = ?";

    private static final String FIND_WITH_STATUS_SEARCH_BASE_STATEMENT
            = "SELECT id_theme, theme_name, theme_status FROM themes WHERE id_theme = id_theme ";
    private static final String WHERE_STATUS_EQUALS_STATEMENT = "AND theme_status = ? ";
    private static final String WHERE_NAME_CONTAINS_STATEMENT = "AND INSTR(LOWER(theme_name), LOWER(?)) > 0 ";
    private static final String ORDER_BY_SURVEY_NAME_STATEMENT = "ORDER BY theme_name ";

    private static ThemeDaoImpl instance;

    private ThemeDaoImpl() {
    }

    public static ThemeDaoImpl getInstance() {
        if (instance == null) {
            instance = new ThemeDaoImpl();
        }
        return instance;
    }

    @Override
    public boolean insert(Theme theme) throws DaoException {

        if (findByName(theme.getThemeName()).isPresent()) {
            logger.error("Already exists theme with theme_name = {}", theme.getThemeName() );
            return false;
        }

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_STATEMENT)) {

            ps.setString(1, theme.getThemeName());
            ps.setString(2, theme.getThemeStatus().toString());
            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            logger.error("Failed to insert new theme: {}", e.getMessage());
            throw new DaoException("Failed to insert new theme", e);
        }
    }

    @Override
    public boolean delete(int id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_STATEMENT)) {

            ps.setInt(1, id);
            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            logger.error("Failed to delete theme with id_theme = {} : {}", id, e.getMessage());
            throw new DaoException("Failed to delete theme with id_theme = " + id , e);
        }
    }

    @Override
    public Optional<Theme> findById(int id) throws DaoException {

        Optional<Theme> theme = Optional.empty();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_BY_ID_STATEMENT)) {

            ps.setInt(1, id);

            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    theme = Optional.of(new Theme.ThemeBuilder()
                            .setThemeId(id)
                            .setThemeName(resultSet.getString(THEMES_TABLE_NAME_COLUMN))
                            .setThemeStatus(ThemeStatus.valueOf(resultSet.getString(THEMES_TABLE_STATUS_COLUMN)))
                            .getTheme());
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to find theme by id_theme = {} : {}", id,  e.getMessage());
            throw new DaoException("Failed to find theme by id_theme = " + id, e);
        }

        return theme;
    }

    @Override
    public Optional<Theme> findByName(String themeName) throws DaoException {

        Optional<Theme> theme = Optional.empty();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_BY_NAME_STATEMENT)) {

            ps.setString(1, themeName);

            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    theme = Optional.of(new Theme.ThemeBuilder()
                            .setThemeId(resultSet.getInt(THEMES_TABLE_PK_COLUMN))
                            .setThemeName(resultSet.getString(THEMES_TABLE_NAME_COLUMN))
                            .setThemeStatus(ThemeStatus.valueOf(resultSet.getString(THEMES_TABLE_STATUS_COLUMN)))
                            .getTheme());
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to find theme by theme_name = {} : {}", themeName,  e.getMessage());
            throw new DaoException("Failed to find theme by theme_name = " + themeName, e);
        }

        return theme;
    }

    @Override
    public List<Theme> findWithThemeStatus(ThemeStatus themeStatus) throws DaoException {
        List<Theme> themes = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_WITH_STATUS_STATEMENT);) {

            ps.setString(1, themeStatus.name());

            try (ResultSet resultSet = ps.executeQuery()) {
                Theme theme;
                while (resultSet.next()) {
                    theme = new Theme.ThemeBuilder()
                            .setThemeId(resultSet.getInt(THEMES_TABLE_PK_COLUMN))
                            .setThemeName(resultSet.getString(THEMES_TABLE_NAME_COLUMN))
                            .setThemeStatus(themeStatus)
                            .getTheme();
                    themes.add(theme);
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to find theme by theme_status = {} : {}", themeStatus.name(),  e.getMessage());
            throw new DaoException("Failed to find theme by theme_status = " + themeStatus.name(), e);
        }

        return themes;
    }

    @Override
    public List<Theme> findWithThemeStatusSearch(int themeStatusId, String[] searchWords, DbOrderType orderType) throws DaoException {
        List<Theme> themes = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection()) {

            StringBuilder query = new StringBuilder(FIND_WITH_STATUS_SEARCH_BASE_STATEMENT);
            if (themeStatusId == FILTER_THEMES_CONFIRMED || themeStatusId == FILTER_THEMES_WAITING) {
                query.append(WHERE_STATUS_EQUALS_STATEMENT);
            } else if (themeStatusId != FILTER_THEMES_ALL) {
                throw new DaoException("Passed unknown theme status: themeStatusId = " + themeStatusId);
            }
            query.append(WHERE_NAME_CONTAINS_STATEMENT.repeat(searchWords.length));
            query.append(ORDER_BY_SURVEY_NAME_STATEMENT).append(orderType.name());

            try (PreparedStatement ps = connection.prepareStatement(query.toString())) {

                int parameterIndex = 1;
                switch (themeStatusId) {
                    case FILTER_THEMES_CONFIRMED -> ps.setString(parameterIndex++, ThemeStatus.CONFIRMED.name());
                    case FILTER_THEMES_WAITING -> ps.setString(parameterIndex++, ThemeStatus.WAITING.name());
                }
                for (int i = 0; i < searchWords.length; i++, parameterIndex++) {
                    ps.setString(parameterIndex, searchWords[i]);
                }

                try (ResultSet resultSet = ps.executeQuery()) {
                    Theme theme;
                    while (resultSet.next()) {
                        theme = new Theme.ThemeBuilder()
                                .setThemeId(resultSet.getInt(THEMES_TABLE_PK_COLUMN))
                                .setThemeName(resultSet.getString(THEMES_TABLE_NAME_COLUMN))
                                .setThemeStatus(ThemeStatus.valueOf(resultSet.getString(THEMES_TABLE_STATUS_COLUMN)))
                                .getTheme();
                        themes.add(theme);
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to find themes by search: {}", e.getMessage());
            throw new DaoException("Failed to find themes by search", e);
        }

        return themes;
    }

    @Override
    public boolean updateThemeStatus(int themeId, ThemeStatus themeStatus) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_THEME_STATUS_STATEMENT)) {

            ps.setString(1, themeStatus.name());
            ps.setInt(2, themeId);
            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            logger.error("Failed to update theme with id_theme = {}: {}", themeId, e.getMessage());
            throw new DaoException("Failed to update theme with id_theme = " + themeId, e);
        }
    }

    @Override
    public boolean update(Theme theme) throws DaoException {

        int id = theme.getThemeId();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_STATEMENT)) {

            ps.setString(1, theme.getThemeName());
            ps.setString(2, theme.getThemeStatus().name());
            ps.setInt(3, id);
            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            logger.error("Failed to update theme with id_theme = {}: {}", theme.getThemeId(), e.getMessage());
            throw new DaoException("Failed to update theme with id_theme = " + theme.getThemeId(), e);
        }
    }
}
