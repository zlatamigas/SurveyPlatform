package epam.zlatamigas.surveyplatform.model.dao.impl;

import epam.zlatamigas.surveyplatform.exception.DaoException;
import epam.zlatamigas.surveyplatform.model.connection.ConnectionPool;
import epam.zlatamigas.surveyplatform.model.dao.BaseDao;
import epam.zlatamigas.surveyplatform.model.dao.ThemeDao;
import epam.zlatamigas.surveyplatform.model.entity.Theme;
import epam.zlatamigas.surveyplatform.model.entity.ThemeStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static epam.zlatamigas.surveyplatform.model.dao.DbTableInfo.*;

public class ThemeDaoImpl implements BaseDao<Theme>, ThemeDao {

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
            = "SELECT id_theme, theme_name, theme_status FROM themes";
    private static final String FIND_WITH_STATUS_STATEMENT
            = "SELECT id_theme, theme_name FROM themes where theme_status=?";
    private static final String FIND_BY_ID_STATEMENT
            = "SELECT theme_name, theme_status FROM themes WHERE id_theme = ?";
    private static final String FIND_BY_NAME_STATEMENT
            = "SELECT id_theme, theme_name, theme_status FROM themes WHERE theme_name = ?";

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

        if(findByName(theme.getThemeName()).isPresent()){
            return false;
        }

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_STATEMENT)) {

            ps.setString(1, theme.getThemeName());
            ps.setString(2, theme.getThemeStatus().toString());
            ps.executeUpdate();

        } catch (SQLException e) {
            logger.error("Error while insert: " + e.getMessage());
            throw new DaoException("Error while while insert: " + e.getMessage(), e);
        }

        return true;
    }

    @Override
    public boolean delete(int id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_STATEMENT)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            logger.error("Error while delete theme with ID = " + id + ": " + e.getMessage());
            throw new DaoException("Error while while delete theme with ID = " + id + ": " + e.getMessage(), e);
        }

        return true;
    }

    @Override
    public Optional<Theme> update(Theme theme) throws DaoException {

        int id = theme.getThemeId();

        Optional<Theme> oldTheme = findById(id);
        if (oldTheme.isEmpty()) {
            throw new DaoException("Theme does not exist: id_theme = " + id);
        }

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_STATEMENT)) {

            ps.setString(1, theme.getThemeName());
            ps.setString(2, theme.getThemeStatus().name());
            ps.setInt(3, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            logger.error("Error while update theme with ID = " + id + ": " + e.getMessage());
            throw new DaoException("Error while while update theme with ID = " + id + ": " + e.getMessage(), e);
        }

        return oldTheme;
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
            logger.error("Error while execute query: " + e.getMessage());
            throw new DaoException("Error while while execute query: " + e.getMessage(), e);
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
            logger.error("Error while execute query: " + e.getMessage());
            throw new DaoException("Error while while execute query: " + e.getMessage(), e);
        }

        return theme;
    }

    @Override
    public List<Theme> findAll() throws DaoException {

        List<Theme> themes = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ALL_STATEMENT);
             ResultSet resultSet = ps.executeQuery()) {

            Theme theme;
            while (resultSet.next()) {
                theme = new Theme.ThemeBuilder()
                        .setThemeId(resultSet.getInt(THEMES_TABLE_PK_COLUMN))
                        .setThemeName(resultSet.getString(THEMES_TABLE_NAME_COLUMN))
                        .setThemeStatus(ThemeStatus.valueOf(resultSet.getString(THEMES_TABLE_STATUS_COLUMN)))
                        .getTheme();
                themes.add(theme);
            }

        } catch (SQLException e) {
            logger.error("Error while execute query: " + e.getMessage());
            throw new DaoException("Error while while execute query: " + e.getMessage(), e);
        }

        return themes;
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
            logger.error("Error while execute query: " + e.getMessage());
            throw new DaoException("Error while while execute query: " + e.getMessage(), e);
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
            logger.error("Error while update theme with ID = " + themeId + ": " + e.getMessage());
            throw new DaoException("Error while while update theme with ID = " + themeId + ": " + e.getMessage(), e);
        }
    }
}
