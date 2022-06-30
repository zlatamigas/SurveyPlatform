package epam.zlatamigas.surveyplatform.model.dao.impl;

import epam.zlatamigas.surveyplatform.exception.DaoException;
import epam.zlatamigas.surveyplatform.model.connection.ConnectionPool;
import epam.zlatamigas.surveyplatform.model.dao.DbOrderType;
import epam.zlatamigas.surveyplatform.model.dao.UserDao;
import epam.zlatamigas.surveyplatform.model.entity.User;
import epam.zlatamigas.surveyplatform.model.entity.UserRole;
import epam.zlatamigas.surveyplatform.model.entity.UserStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static epam.zlatamigas.surveyplatform.model.dao.DbTableInfo.*;

public class UserDaoImpl implements UserDao {

    private static final Logger logger = LogManager.getLogger();


    private static final String INSERT_STATEMENT
            = "INSERT INTO users(email, password, registration_date, user_role, user_status)VALUES(?,?,?,?,?)";
    private static final String UPDATE_WITHOUT_PASSWORD_STATEMENT
            = "UPDATE users SET email = ?, registration_date = ?, user_role = ?, user_status = ? WHERE id_user = ?";
    private static final String UPDATE_PASSWORD_STATEMENT
            = "UPDATE users SET password = ? WHERE id_user = ?";
    private static final String DELETE_STATEMENT
            = "DELETE FROM users WHERE id_user = ?";
    private static final String FIND_ALL_STATEMENT
            = "SELECT id_user, email, password, registration_date, user_role, user_status FROM users";
    private static final String FIND_BY_ID_WITHOUT_PASSWORD_STATEMENT
            = "SELECT email, registration_date, user_role, user_status FROM users WHERE id_user = ?";
    private static final String FIND_BY_EMAIL_STATEMENT
            = "SELECT id_user, password, registration_date, user_role, user_status FROM users WHERE email = ?";
    private static final String AUTHENTICATE_STATEMENT
            = "SELECT id_user, registration_date, user_role, user_status FROM users WHERE email = ? AND password = ?";


    private static final String FIND_USERS_BY_SEARCH_BASE_STATEMENT
            = "SELECT id_user, email, registration_date, user_role, user_status FROM users ";
    private static final String WHERE_ROLE_EQUALS_STATEMENT = """
            AND user_role = ? 
            """;
    private static final String WHERE_STATUS_EQUALS_STATEMENT = """
            AND user_status = ? 
            """;
    private static final String WHERE_EMAIL_CONTAINS_STATEMENT = """
            AND INSTR(LOWER(email), LOWER(?)) > 0 
            """;
    private static final String ORDER_BY_EMAIL_STATEMENT = """
            ORDER BY email 
            """;

    public static final int FILTER_ROLE_ALL = 0;
    public static final int FILTER_ROLE_ADMIN = 1;
    public static final int FILTER_ROLE_USER = 2;
    public static final int FILTER_STATUS_ALL = 0;
    public static final int FILTER_STATUS_ACTIVE = 1;
    public static final int FILTER_STATUS_BANNED = 2;

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
             PreparedStatement st = connection.prepareStatement(AUTHENTICATE_STATEMENT)) {

            st.setString(1, email);
            st.setString(2, password);

            try (ResultSet resultSet = st.executeQuery()) {
                if (resultSet.next()) {
                    user = Optional.of(new User.UserBuilder()
                            .setUserId(resultSet.getInt(USERS_TABLE_PK_COLUMN))
                            .setEmail(email)
                            .setRegistrationDate(resultSet.getDate(USERS_TABLE_REGISTRATION_DATE_COLUMN).toLocalDate())
                            .setRole(UserRole.valueOf(resultSet.getString(USERS_TABLE_ROLE_COLUMN)))
                            .setStatus(UserStatus.valueOf(resultSet.getString(USERS_TABLE_STATUS_COLUMN)))
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
    public Optional<User> findByEmail(String email) throws DaoException {

        Optional<User> user = Optional.empty();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement st = connection.prepareStatement(FIND_BY_EMAIL_STATEMENT)) {

            st.setString(1, email);

            try (ResultSet resultSet = st.executeQuery()) {
                if (resultSet.next()) {
                    user = Optional.of(new User.UserBuilder()
                            .setUserId(resultSet.getInt(USERS_TABLE_PK_COLUMN))
                            .setEmail(email)
                            .setRegistrationDate(resultSet.getDate(USERS_TABLE_REGISTRATION_DATE_COLUMN).toLocalDate())
                            .setRole(UserRole.valueOf(resultSet.getString(USERS_TABLE_ROLE_COLUMN)))
                            .setStatus(UserStatus.valueOf(resultSet.getString(USERS_TABLE_STATUS_COLUMN)))
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
    public List<User> findUsersBySearch(int filterRoleId, int filterStatusId, String[] searchWords, DbOrderType orderType) throws DaoException {
        List<User> users = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            StringBuilder query = new StringBuilder(FIND_USERS_BY_SEARCH_BASE_STATEMENT);

            if (filterRoleId == FILTER_ROLE_ADMIN || filterRoleId == FILTER_ROLE_USER) {
                query.append(WHERE_ROLE_EQUALS_STATEMENT);
            } else if (filterRoleId != FILTER_ROLE_ALL) {
                throw new DaoException("Passed unknown role: filterRoleId = " + filterRoleId);
            }
            if (filterStatusId == FILTER_STATUS_ACTIVE
                    || filterStatusId == FILTER_STATUS_BANNED) {
                query.append(WHERE_STATUS_EQUALS_STATEMENT);
            } else if (filterStatusId != FILTER_STATUS_ALL) {
                throw new DaoException("Passed unknown status: filterStatusId = " + filterStatusId);
            }
            query.append(WHERE_EMAIL_CONTAINS_STATEMENT.repeat(searchWords.length));
            query.append(ORDER_BY_EMAIL_STATEMENT).append(orderType.name());

            try (PreparedStatement ps = connection.prepareStatement(query.toString())) {

                int parameterIndex = 1;
                switch (filterRoleId) {
                    case FILTER_ROLE_ADMIN -> ps.setString(parameterIndex++, UserRole.ADMIN.name());
                    case FILTER_ROLE_USER -> ps.setString(parameterIndex++, UserRole.USER.name());
                }
                switch (filterStatusId) {
                    case FILTER_STATUS_ACTIVE -> ps.setString(parameterIndex++, UserStatus.ACTIVE.name());
                    case FILTER_STATUS_BANNED -> ps.setString(parameterIndex++, UserStatus.BANNED.name());
                }
                for (int i = 0; i < searchWords.length; i++, parameterIndex++) {
                    ps.setString(parameterIndex, searchWords[i]);
                }

                try (ResultSet resultSet = ps.executeQuery()) {
                    User user;
                    while (resultSet.next()) {
                        user = new User.UserBuilder()
                                .setUserId(resultSet.getInt(USERS_TABLE_PK_COLUMN))
                                .setEmail(resultSet.getString(USERS_TABLE_EMAIL_COLUMN))
                                .setRegistrationDate(resultSet.getDate(USERS_TABLE_REGISTRATION_DATE_COLUMN).toLocalDate())
                                .setRole(UserRole.valueOf(resultSet.getString(USERS_TABLE_ROLE_COLUMN)))
                                .setStatus(UserStatus.valueOf(resultSet.getString(USERS_TABLE_STATUS_COLUMN)))
                                .getUser();
                        users.add(user);
                    }
                }
            }
        } catch (
                SQLException e) {
            logger.error("Error while execute query: " + e.getMessage());
            throw new DaoException("Error while while execute query: " + e.getMessage(), e);
        }

        return users;
    }


    @Override
    public boolean insert(User user) throws DaoException {

        Optional<User> dbUser = findByEmail(user.getEmail());
        if (dbUser.isPresent()) {
            logger.error("Already exists user with email: " + user.getEmail());
            return false;
        }

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_STATEMENT)) {

            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setDate(3, Date.valueOf(user.getRegistrationDate()));
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
            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            logger.error("Error while delete user with ID = " + id + ": " + e.getMessage());
            throw new DaoException("Error while while delete user with ID = " + id + ": " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<User> updateWithoutPassword(User user) throws DaoException {

        int id = user.getUserId();

        Optional<User> oldUser = findByIdWithoutPassword(id);
        if (oldUser.isEmpty()) {
            throw new DaoException("User does not exist: id_user = " + id);
        }

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_WITHOUT_PASSWORD_STATEMENT)) {

            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setDate(3, Date.valueOf(user.getRegistrationDate()));
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
    public boolean updatePassword(int userId, String password) throws DaoException {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_PASSWORD_STATEMENT)) {

            ps.setString(1, password);
            ps.setInt(2, userId);
            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            logger.error("Error while update user with ID = " + userId + ": " + e.getMessage());
            throw new DaoException("Error while while update user with ID = " + userId + ": " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<User> findByIdWithoutPassword(int id) throws DaoException {

        Optional<User> user = Optional.empty();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_BY_ID_WITHOUT_PASSWORD_STATEMENT)) {

            ps.setInt(1, id);

            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    user = Optional.of(new User.UserBuilder()
                            .setUserId(id)
                            .setEmail(resultSet.getString(USERS_TABLE_EMAIL_COLUMN))
                            .setRegistrationDate(resultSet.getDate(USERS_TABLE_REGISTRATION_DATE_COLUMN).toLocalDate())
                            .setRole(UserRole.valueOf(resultSet.getString(USERS_TABLE_ROLE_COLUMN)))
                            .setStatus(UserStatus.valueOf(resultSet.getString(USERS_TABLE_STATUS_COLUMN)))
                            .getUser());
                }
            }

        } catch (SQLException e) {
            logger.error("Error while execute query: " + e.getMessage());
            throw new DaoException("Error while while execute query: " + e.getMessage(), e);
        }

        return user;
    }
}
