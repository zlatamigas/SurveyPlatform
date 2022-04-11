package epam.zlatamigas.surveyplatform.model.dao.impl;

import epam.zlatamigas.surveyplatform.exception.DaoException;
import epam.zlatamigas.surveyplatform.model.connection.ConnectionPool;
import epam.zlatamigas.surveyplatform.model.dao.BaseDao;
import epam.zlatamigas.surveyplatform.model.dao.ThemeDao;
import epam.zlatamigas.surveyplatform.model.entity.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ThemeDaoImpl implements BaseDao<Theme>, ThemeDao {

    private static final Logger logger = LogManager.getLogger();

    private static final String INSERT_STATEMENT = "INSERT INTO themes(theme_name, theme_status) VALUES(?,?)";
    private static final String UPDATE_STATEMENT = "UPDATE themes SET theme_name = ?, theme_status = ? WHERE id_theme = ?";
    private static final String DELETE_STATEMENT = "DELETE FROM themes WHERE id_theme = ?";
    private static final String FIND_ALL_STATEMENT = "SELECT * FROM themes";
    private static final String FIND_STATEMENT = "SELECT * FROM themes WHERE id_theme = ?";

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
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement ps = connection.prepareStatement(INSERT_STATEMENT);

            ps.setString(1, theme.getThemeName());
            ps.setString(2, theme.getThemeStatus().toString());

            ps.executeUpdate();

        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException(e.getMessage(), e);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }

        return true;
    }

    @Override
    public boolean delete(int id) throws DaoException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement ps = connection.prepareStatement(DELETE_STATEMENT);

            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException(e.getMessage(), e);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
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

        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement ps = connection.prepareStatement(UPDATE_STATEMENT);

            ps.setString(1, theme.getThemeName());
            ps.setString(2, theme.getThemeStatus().toString());
            ps.setInt(3, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException(e.getMessage(), e);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }

        return oldTheme;
    }

    @Override
    public Theme findById(int id) throws DaoException {
        Theme theme = null;

        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement ps = connection.prepareStatement(FIND_STATEMENT);

            ps.setInt(1, id);

            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                theme = new Theme();
                theme.setThemeId(resultSet.getInt(1));
                theme.setThemeName(resultSet.getString(2));
                theme.setThemeStatus(Theme.ThemeStatus.valueOf(resultSet.getString(3)));
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException(e.getMessage(), e);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }

        return theme;
    }

    @Override
    public List<Theme> findAll() throws DaoException {
        List<Theme> themes = new ArrayList<>();
        Theme theme = null;

        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement ps = connection.prepareStatement(FIND_ALL_STATEMENT);

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                theme = new Theme();
                theme.setThemeId(resultSet.getInt(1));
                theme.setThemeName(resultSet.getString(2));
                theme.setThemeStatus(Theme.ThemeStatus.valueOf(resultSet.getString(3)));
                themes.add(theme);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException(e.getMessage(), e);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }

        return themes;
    }
}
