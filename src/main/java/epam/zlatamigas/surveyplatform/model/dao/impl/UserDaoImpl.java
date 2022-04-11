package epam.zlatamigas.surveyplatform.model.dao.impl;

import epam.zlatamigas.surveyplatform.exception.DaoException;
import epam.zlatamigas.surveyplatform.model.connection.ConnectionPool;
import epam.zlatamigas.surveyplatform.model.dao.BaseDao;
import epam.zlatamigas.surveyplatform.model.dao.UserDao;
import epam.zlatamigas.surveyplatform.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements BaseDao<User>, UserDao {

    private static final Logger logger = LogManager.getLogger();
    private static final String ENCRYPTION_METHOD = "SHA-1";

    private static final String INSERT_STATEMENT = "INSERT INTO users(email, password, registration_date, user_role, user_status)VALUES(?,?,?,?,?)";
    private static final String UPDATE_STATEMENT = "UPDATE users SET email = ?, password = ?, registration_date = ?, user_role = ?, user_status = ? WHERE id_user = ?";
    private static final String DELETE_STATEMENT = "DELETE FROM users WHERE id_user = ?";
    private static final String FIND_ALL_STATEMENT = "SELECT * FROM users";
    private static final String FIND_STATEMENT = "SELECT * FROM users WHERE id_user = ?";
    private static final String LOGIN_QUERY = "SELECT registration_date, user_role, user_status FROM users WHERE email = ? AND password = ?";

    private static final byte[] SEED = new byte[]{1, 43, 32, 2, 2, 1, 43, 32, 2, 79, 1, 43, 32, 2, 2, 29};

    private static final int BASE = 16;
    private static final int PASSWORD_LENGTH = 20;
    private static final BigInteger MOD_BASE = new BigInteger("100000000000000000000");
    private static final char ADDITIONAL_SYMBOL = '0';

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

        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement st = connection.prepareStatement(LOGIN_QUERY);

            st.setString(1, email);
            String encodedPassword = encode(password);
            logger.info(encodedPassword);
            st.setString(2, encodedPassword);
            ResultSet resultSet = st.executeQuery();

            if (resultSet.next()) {
                user = Optional.of(new User.UserBuilder()
                        .setEmail(email)
                        .setRegistrationDate(resultSet.getDate(1))
                        .setRole(User.UserRole.valueOf(resultSet.getString(2)))
                        .setStatus(User.UserStatus.valueOf(resultSet.getString(3)))
                        .getUser());
            }

        } catch (SQLException | NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }

        return user;
    }

    @Override
    public boolean insert(User user) throws DaoException {

        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement ps = connection.prepareStatement(INSERT_STATEMENT);


            ps.setString(1, user.getEmail());
            String password = encode(user.getPassword());
            ps.setString(2, password);
            ps.setDate(3, user.getRegistrationDate());
            ps.setString(4, user.getRole().name());
            ps.setString(5, user.getStatus().name());

            ps.executeUpdate();

        } catch (SQLException | NoSuchAlgorithmException e) {
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
    public User update(User user) throws DaoException {

        int id = user.getUserId();

        User oldUser = findById(id);
        if (oldUser == null) {
            throw new DaoException("User does not exist: id_user = " + id);
        }

        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement ps = connection.prepareStatement(UPDATE_STATEMENT);


            ps.setString(1, user.getEmail());
            String password = encode(user.getPassword());
            ps.setString(2, password);
            ps.setDate(3, user.getRegistrationDate());
            ps.setString(4, user.getRole().name());
            ps.setString(5, user.getStatus().name());
            ps.setInt(6, id);

            ps.executeUpdate();

        } catch (SQLException | NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
            throw new DaoException(e.getMessage(), e);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }

        return oldUser;
    }

    @Override
    public User findById(int id) throws DaoException {

        User user = null;

        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement ps = connection.prepareStatement(FIND_STATEMENT);

            ps.setInt(1, id);

            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                user = new User.UserBuilder()
                        .setUserId(resultSet.getInt(1))
                        .setEmail(resultSet.getString(2))
                        .setPassword(resultSet.getString(3))
                        .setRegistrationDate(resultSet.getDate(4))
                        .setRole(User.UserRole.valueOf(resultSet.getString(5)))
                        .setStatus(User.UserStatus.valueOf(resultSet.getString(6)))
                        .getUser();
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException(e.getMessage(), e);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }

        return user;
    }

    @Override
    public List<User> findAll() throws DaoException {

        List<User> users = new ArrayList<>();
        User user = null;

        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement ps = connection.prepareStatement(FIND_ALL_STATEMENT);

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                user = new User.UserBuilder()
                        .setUserId(resultSet.getInt(1))
                        .setEmail(resultSet.getString(2))
                        .setPassword(resultSet.getString(3))
                        .setRegistrationDate(resultSet.getDate(4))
                        .setRole(User.UserRole.valueOf(resultSet.getString(5)))
                        .setStatus(User.UserStatus.valueOf(resultSet.getString(6)))
                        .getUser();
                users.add(user);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException(e.getMessage(), e);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }

        return users;
    }

    private String encode(String password) throws NoSuchAlgorithmException {

        MessageDigest messageDigest = MessageDigest.getInstance(ENCRYPTION_METHOD);
        messageDigest.update(SEED);
        messageDigest.update(password.getBytes(StandardCharsets.UTF_8));
        byte[] encodedBytes = messageDigest.digest();

        BigInteger bigInteger = new BigInteger(1, encodedBytes);
        bigInteger = bigInteger.mod(MOD_BASE);

        StringBuffer encodedPassword = new StringBuffer(bigInteger.toString(BASE));

        while (encodedPassword.length() < PASSWORD_LENGTH) {
            encodedPassword.append(ADDITIONAL_SYMBOL);
        }

        return encodedPassword.toString();
    }
}
