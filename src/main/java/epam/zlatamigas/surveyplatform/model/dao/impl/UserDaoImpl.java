package epam.zlatamigas.surveyplatform.model.dao.impl;

import epam.zlatamigas.surveyplatform.exception.DaoException;
import epam.zlatamigas.surveyplatform.model.connection.ConnectionPool;
import epam.zlatamigas.surveyplatform.model.dao.BaseDao;
import epam.zlatamigas.surveyplatform.model.dao.UserDao;
import epam.zlatamigas.surveyplatform.model.entity.User;
import epam.zlatamigas.surveyplatform.model.entity.UserRole;
import epam.zlatamigas.surveyplatform.model.entity.UserStatus;
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

public class UserDaoImpl implements BaseDao<User>, UserDao {

    private static final Logger logger = LogManager.getLogger();

    private static final String INSERT_STATEMENT
            = "INSERT INTO users(email, password, registration_date, user_role, user_status)VALUES(?,?,?,?,?)";
    private static final String UPDATE_STATEMENT
            = "UPDATE users SET email = ?, password = ?, registration_date = ?, user_role = ?, user_status = ? WHERE id_user = ?";
    private static final String DELETE_STATEMENT
            = "DELETE FROM users WHERE id_user = ?";
    private static final String FIND_ALL_STATEMENT
            = "SELECT id_user, email, password, registration_date, user_role, user_status FROM users";
    private static final String FIND_STATEMENT
            = "SELECT email, password, registration_date, user_role, user_status FROM users WHERE id_user = ?";
    private static final String LOGIN_QUERY
            = "SELECT registration_date, user_role, user_status FROM users WHERE email = ? AND password = ?";

    private static UserDaoImpl instance;

    private UserDaoImpl() {
    }


    public static UserDaoImpl getInstance() {
        if (instance == null) {
            instance = new UserDaoImpl();
        }
        return instance;
    }

    @Override
    public Optional<User> authenticate(String email, String password) throws DaoException {

        Optional<User> user = Optional.empty();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement st = connection.prepareStatement(LOGIN_QUERY)) {

            st.setString(1, email);
            st.setString(2, password);

            try (ResultSet resultSet = st.executeQuery()) {
                if (resultSet.next()) {
                    user = Optional.of(new User.UserBuilder()
                            .setEmail(email)
                            .setRegistrationDate(resultSet.getDate(1))
                            .setRole(UserRole.valueOf(resultSet.getString(2)))
                            .setStatus(UserStatus.valueOf(resultSet.getString(3)))
                            .getUser());
                }
            }

        } catch (SQLException e) {
            logger.error("Error while authenticate: " + e.getMessage());
            throw new DaoException("Error while while authenticate: " + e.getMessage(), e);
        }

        return user;
    }

    @Override
    public boolean insert(User user) throws DaoException {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_STATEMENT)) {

            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setDate(3, user.getRegistrationDate());
            ps.setString(4, user.getRole().name());
            ps.setString(5, user.getStatus().name());
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
            logger.error("Error while delete user with ID = " + id + ": " + e.getMessage());
            throw new DaoException("Error while while delete user with ID = " + id + ": " + e.getMessage(), e);
        }

        return true;
    }

    @Override
    public User update(User user) throws DaoException {

        int id = user.getUserId();

        User oldUser = findById(id);
        if (oldUser == null) {
            throw new DaoException("User does not exist: id_user = " + id);
        }

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_STATEMENT)) {

            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setDate(3, user.getRegistrationDate());
            ps.setString(4, user.getRole().name());
            ps.setString(5, user.getStatus().name());
            ps.setInt(6, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            logger.error("Error while update user with ID = " + id + ": " + e.getMessage());
            throw new DaoException("Error while while update user with ID = " + id + ": " + e.getMessage(), e);
        }

        return oldUser;
    }

    @Override
    public User findById(int id) throws DaoException {

        User user = null;

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_STATEMENT)) {

            ps.setInt(1, id);

            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    user = new User.UserBuilder()
                            .setUserId(resultSet.getInt(id))
                            .setEmail(resultSet.getString(USERS_TABLE_EMAIL_COLUMN))
                            .setPassword(resultSet.getString(USERS_TABLE_PASSWORD_COLUMN))
                            .setRegistrationDate(resultSet.getDate(USERS_TABLE_REGISTRATION_DATE_COLUMN))
                            .setRole(UserRole.valueOf(resultSet.getString(USERS_TABLE_ROLE_COLUMN)))
                            .setStatus(UserStatus.valueOf(resultSet.getString(USERS_TABLE_STATUS_COLUMN)))
                            .getUser();
                }
            }

        } catch (SQLException e) {
            logger.error("Error while execute query: " + e.getMessage());
            throw new DaoException("Error while while execute query: " + e.getMessage(), e);
        }

        return user;
    }

    @Override
    public List<User> findAll() throws DaoException {

        List<User> users = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ALL_STATEMENT);
             ResultSet resultSet = ps.executeQuery()) {

            User user;
            while (resultSet.next()) {
                user = new User.UserBuilder()
                        .setUserId(resultSet.getInt(USERS_TABLE_PK_COLUMN))
                        .setEmail(resultSet.getString(USERS_TABLE_EMAIL_COLUMN))
                        .setPassword(resultSet.getString(USERS_TABLE_PASSWORD_COLUMN))
                        .setRegistrationDate(resultSet.getDate(USERS_TABLE_REGISTRATION_DATE_COLUMN))
                        .setRole(UserRole.valueOf(resultSet.getString(USERS_TABLE_ROLE_COLUMN)))
                        .setStatus(UserStatus.valueOf(resultSet.getString(USERS_TABLE_STATUS_COLUMN)))
                        .getUser();
                users.add(user);
            }
        } catch (SQLException e) {
            logger.error("Error while execute query: " + e.getMessage());
            throw new DaoException("Error while while execute query: " + e.getMessage(), e);
        }

        return users;
    }
}
