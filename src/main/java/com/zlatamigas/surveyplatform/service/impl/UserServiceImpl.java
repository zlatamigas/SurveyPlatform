package com.zlatamigas.surveyplatform.service.impl;

import com.zlatamigas.surveyplatform.exception.DaoException;
import com.zlatamigas.surveyplatform.exception.ServiceException;
import com.zlatamigas.surveyplatform.model.dao.DbOrderType;
import com.zlatamigas.surveyplatform.model.dao.impl.UserDaoImpl;
import com.zlatamigas.surveyplatform.model.entity.User;
import com.zlatamigas.surveyplatform.model.entity.UserRole;
import com.zlatamigas.surveyplatform.model.entity.UserStatus;
import com.zlatamigas.surveyplatform.service.UserService;
import com.zlatamigas.surveyplatform.util.encoder.PasswordEncoder;
import com.zlatamigas.surveyplatform.util.keygenerator.ChangePasswordKeyGenerator;
import com.zlatamigas.surveyplatform.util.keygenerator.impl.ChangePasswordKeyGeneratorImpl;
import com.zlatamigas.surveyplatform.util.locale.ResourceBundleManager;
import com.zlatamigas.surveyplatform.util.mail.MailSender;
import com.zlatamigas.surveyplatform.util.search.SearchParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.zlatamigas.surveyplatform.model.dao.impl.UserDaoImpl.*;
import static com.zlatamigas.surveyplatform.util.locale.LocalisedMessageKey.MAIL_CHANGE_PASSWORD_HEADER;
import static com.zlatamigas.surveyplatform.util.locale.LocalisedMessageKey.MAIL_CHANGE_PASSWORD_TEXT_PATTERN;
import static com.zlatamigas.surveyplatform.util.search.SearchParameter.DEFAULT_SEARCH_WORDS_DELIMITER;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger();

    private static UserServiceImpl instance = new UserServiceImpl();

    private final UserDaoImpl userDao;
    private final ResourceBundleManager resourceBundleManager;

    private UserServiceImpl() {
        userDao = UserDaoImpl.getInstance();
        resourceBundleManager = ResourceBundleManager.getInstance();
    }

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean insertUser(String email, String password, String roleName, String statusName) throws ServiceException {

        if (email == null) {
            logger.error("Passed null as email");
            return false;
        }

        if (password == null) {
            logger.error("Passed null as password");
            return false;
        }

        UserRole role;
        if (roleName != null) {
            try {
                role = UserRole.valueOf(roleName);
            } catch (IllegalArgumentException e) {
                logger.error("Passed invalid role {}", roleName);
                return false;
            }
        } else {
            logger.error("Passed null as role");
            return false;
        }

        UserStatus status;
        if (statusName != null) {
            try {
                status = UserStatus.valueOf(statusName);
            } catch (IllegalArgumentException e) {
                logger.error("Passed invalid status {}", statusName);
                return false;
            }
        } else {
            logger.error("Passed null as status");
            return false;
        }

        try {

            PasswordEncoder encoder = new PasswordEncoder();
            String encodedPassword = encoder.encode(password);

            User user = new User.UserBuilder()
                    .setEmail(email)
                    .setPassword(encodedPassword)
                    .setRegistrationDate(LocalDate.now())
                    .setRole(role)
                    .setStatus(status)
                    .getUser();

            return userDao.insert(user);
        } catch (DaoException | NoSuchAlgorithmException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean signUpUser(String email, String password) throws ServiceException {

        if (email == null) {
            logger.error("Passed null as email");
            return false;
        }

        if (password == null) {
            logger.error("Passed null as password");
            return false;
        }
        try {

            PasswordEncoder encoder = new PasswordEncoder();
            String encodedPassword = encoder.encode(password);

            User user = new User.UserBuilder()
                    .setEmail(email)
                    .setPassword(encodedPassword)
                    .setRegistrationDate(LocalDate.now())
                    .setRole(UserRole.USER)
                    .setStatus(UserStatus.ACTIVE)
                    .getUser();
            return userDao.insert(user);
        } catch (DaoException | NoSuchAlgorithmException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<User> authenticate(String email, String password) throws ServiceException {

        if (email == null) {
            logger.error("Passed null as email");
            return Optional.empty();
        }

        if (password == null) {
            logger.error("Passed null as password");
            return Optional.empty();
        }

        Optional<User> user = Optional.empty();
        try {
            PasswordEncoder encoder = new PasswordEncoder();
            String encodedPassword = encoder.encode(password);
            user = userDao.authenticate(email, encodedPassword);
        } catch (DaoException | NoSuchAlgorithmException e) {
            throw new ServiceException(e);
        }

        return user;
    }

    @Override
    public Optional<User> findById(int userId) throws ServiceException {
        try {
            return userDao.findById(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> findUsersBySearch(String filterRoleName, String filterStatusName, String searchWordsStr, String orderTypeName) throws ServiceException {

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
            if (orderTypeName != null) {
                orderType = DbOrderType.valueOf(orderTypeName.toUpperCase());
            } else {
                orderType = DbOrderType.ASC;
                logger.warn("Set default orderType parameter");
            }
        } catch (IllegalArgumentException e) {
            orderType = DbOrderType.ASC;
            logger.warn("Set default orderType parameter");
        }

        int roleId;
        if (filterRoleName.equals(SearchParameter.DEFAULT_FILTER_STR_ALL)) {
            roleId = FILTER_ROLE_ALL;
        } else {

            try {
                UserRole role = UserRole.valueOf(filterRoleName);
                roleId = switch (role) {
                    case ADMIN -> FILTER_ROLE_ADMIN;
                    case USER -> FILTER_ROLE_USER;
                    default -> throw new ServiceException("Unacceptable UserRole = " + filterRoleName);
                };
            } catch (IllegalArgumentException e) {
                logger.error("Passed invalid role {}", filterRoleName);
                throw new ServiceException("Unacceptable UserRole = " + filterRoleName);
            }

        }

        int statusId;
        if (filterStatusName.equals(SearchParameter.DEFAULT_FILTER_STR_ALL)) {
            statusId = FILTER_STATUS_ALL;
        } else {
            try {
                UserStatus status = UserStatus.valueOf(filterStatusName);
                statusId = switch (status) {
                    case ACTIVE -> FILTER_STATUS_ACTIVE;
                    case BANNED -> FILTER_STATUS_BANNED;
                };
            } catch (IllegalArgumentException e) {
                logger.error("Passed invalid status {}", filterStatusName);
                throw new ServiceException("Unacceptable UserStatus = " + filterStatusName);
            }
        }

        try {
            return userDao.findUsersBySearch(roleId, statusId, searchWords, orderType);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean changePassword(String email, String newPassword) throws ServiceException {

        if (email == null) {
            logger.error("Passed null as email");
            return false;
        }

        if (newPassword == null) {
            logger.error("Passed null as password");
            return false;
        }

        try {
            Optional<User> dbUser = userDao.findByEmail(email);

            if (dbUser.isPresent()) {
                PasswordEncoder encoder = new PasswordEncoder();
                String encodedPassword = encoder.encode(newPassword);

                User user = dbUser.get();

                return userDao.updatePassword(user.getUserId(), encodedPassword);
            }
        } catch (DaoException | NoSuchAlgorithmException e) {
            throw new ServiceException(e);
        }

        return false;
    }

    @Override
    public boolean updateRoleStatus(int userId, String roleName, String statusName) throws ServiceException {

        try {
            UserRole role;
            if (roleName != null) {
                try {
                    role = UserRole.valueOf(roleName);
                } catch (IllegalArgumentException e) {
                    logger.error("Passed invalid role {}", roleName);
                    return false;
                }
            } else {
                logger.error("Passed null as role");
                return false;
            }

            UserStatus status;
            if (statusName != null) {
                try {
                    status = UserStatus.valueOf(statusName);
                } catch (IllegalArgumentException e) {
                    logger.error("Passed invalid status {}", statusName);
                    return false;
                }
            } else {
                logger.error("Passed null as status");
                return false;
            }

            return userDao.updateRoleStatus(userId, role, status);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Integer> requestChangePassword(String email, String locale) throws ServiceException {

        if (email == null) {
            logger.error("Passed null as email");
            return Optional.empty();
        }

        try {
            if (userDao.findByEmail(email).isEmpty()) {
                logger.error("Cannot find user with email {} in DB", email);
                return Optional.empty();
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        MailSender sender = MailSender.getInstance();
        ChangePasswordKeyGenerator keyGenerator = ChangePasswordKeyGeneratorImpl.getInstance();

        int key = keyGenerator.generateKey();

        ResourceBundle resourceBundle = resourceBundleManager.getResourceBundle(locale);

        if (sender.sendMail(email,
                resourceBundle.getString(MAIL_CHANGE_PASSWORD_HEADER),
                String.format(resourceBundle.getString(MAIL_CHANGE_PASSWORD_TEXT_PATTERN), key))) {
            return Optional.of(key);
        } else {
            throw new ServiceException("Cannot send key to email: " + email);
        }
    }

    @Override
    public boolean deleteUser(int userId) throws ServiceException {
        try {
            return userDao.delete(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
