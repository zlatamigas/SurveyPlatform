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

import static epam.zlatamigas.surveyplatform.model.dao.DbTableInfo.*;

public class ThemeDaoImpl implements BaseDao<Theme>, ThemeDao {

    private static final Logger logger = LogManager.getLogger();

    private static final String INSERT_STATEMENT
            = "INSERT INTO themes(theme_name, theme_status) VALUES(?,?)";
    private static final String UPDATE_STATEMENT
            = "UPDATE themes SET theme_name = ?, theme_status = ? WHERE id_theme = ?";
    private static final String DELETE_STATEMENT
            = "DELETE FROM themes WHERE id_theme = ?";
    private static final String FIND_ALL_STATEMENT
            = "SELECT id_theme, theme_name, theme_status FROM themes";
    private static final String FIND_STATEMENT
            = "SELECT theme_name, theme_status FROM themes WHERE id_theme = ?";

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
    public Theme update(Theme theme) throws DaoException {

        int id = theme.getThemeId();

        Theme oldTheme = findById(id);
        if (oldTheme == null) {
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
    public Theme findById(int id) throws DaoException {

        Theme theme = null;

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_STATEMENT)) {

            ps.setInt(1, id);

            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    theme = new Theme();
                    theme.setThemeId(id);
                    theme.setThemeName(resultSet.getString(THEMES_TABLE_NAME_COLUMN));
                    theme.setThemeStatus(ThemeStatus.valueOf(resultSet.getString(THEMES_TABLE_STATUS_COLUMN)));
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
                theme = new Theme();
                theme.setThemeId(resultSet.getInt(THEMES_TABLE_PK_COLUMN));
                theme.setThemeName(resultSet.getString(THEMES_TABLE_NAME_COLUMN));
                theme.setThemeStatus(ThemeStatus.valueOf(resultSet.getString(THEMES_TABLE_STATUS_COLUMN)));
                themes.add(theme);
            }

        } catch (SQLException e) {
            logger.error("Error while execute query: " + e.getMessage());
            throw new DaoException("Error while while execute query: " + e.getMessage(), e);
        }

        return themes;
    }
}
