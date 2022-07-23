package epam.zlatamigas.surveyplatform.service;

import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.entity.User;
import epam.zlatamigas.surveyplatform.model.entity.UserRole;
import epam.zlatamigas.surveyplatform.model.entity.UserStatus;

import java.util.List;
import java.util.Optional;

public interface UserService {

    /**
     * Insert new user.
     *
     * @param email User email.
     * @param password Not encrypted password.
     * @param roleName User role: ADMIN or USER.
     * @param statusName User status: ACTIVE or BANNED.
     * @return True, if user was added, otherwise false.
     * @throws ServiceException If a database access error occurs.
     */
    boolean insertUser(String email, String password, String roleName, String statusName) throws ServiceException;

    /**
     * Insert new user with default role of USER.
     *
     * @param email    User email.
     * @param password Not encrypted password.
     * @return True, if user was added, otherwise false.
     * @throws ServiceException If a database access error occurs.
     */
    boolean signUpUser(String email, String password) throws ServiceException;

    /**
     * Authenticate user.
     *
     * @param email    User email.
     * @param password Not encrypted password.
     * @return User without password, if exists, otherwise Optional.empty().
     * @throws ServiceException If a database access error occurs.
     */
    Optional<User> authenticate(String email, String password) throws ServiceException;

    /**
     * Find user by id.
     *
     * @param userId User id.
     * @return User without password, if exists, otherwise Optional.empty().
     * @throws ServiceException If a database access error occurs.
     */
    Optional<User> findById(int userId) throws ServiceException;


    /**
     * Find users according to requested filter and search parameters.
     *
     * @param filterRoleName User role: ADMIN or USER.
     * @param filterStatusName User status: ACTIVE or BANNED.
     * @param searchWordsStr Words contained in user email. Case insensitive.
     *                       If array size is 0, then all survey names are acceptable.
     * @param orderTypeName  Order type: ASC - ascending, DESC - descending.
     * @return List of users without passwords.
     * @throws ServiceException If a database access error occurs or if invalid filter parameters are passed.
     */
    List<User> findUsersBySearch(String filterRoleName,
                                 String filterStatusName,
                                 String searchWordsStr,
                                 String orderTypeName) throws ServiceException;

    /**
     * Change password for user with requested email.
     *
     * @param email    User email.
     * @param newPassword New password. Not encrypted.
     * @return True, if user exists and password was changed, otherwise false.
     * @throws ServiceException If a database access error occurs.
     */
    boolean changePassword(String email, String newPassword) throws ServiceException;

    /**
     * Update user role and status.
     *
     * @param userId User id.
     * @param roleName User role: ADMIN or USER.
     * @param statusName User status: ACTIVE or BANNED.
     * @return True, if user exists and was updated, otherwise false.
     * @throws ServiceException
     */
    boolean updateRoleStatus(int userId, String roleName, String statusName) throws ServiceException;

    /**
     * Send key for changing password to email.
     *
     * @param email Email to send key to.
     * @param locale Required localisation of sent email.
     * @return Sent key, if user exists in system, otherwise Optional.empty().
     * @throws ServiceException Thrown when key was not sent.
     */
    Optional<Integer> requestChangePassword(String email, String locale) throws ServiceException;

    /**
     * Delete user by id.
     *
     * @param userId User id.
     * @return Tru if existing user was deleted, otherwise false..
     * @throws ServiceException If a database access error occurs.
     */
    boolean deleteUser(int userId) throws ServiceException;
}
