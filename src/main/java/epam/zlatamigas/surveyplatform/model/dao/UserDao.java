package epam.zlatamigas.surveyplatform.model.dao;

import epam.zlatamigas.surveyplatform.exception.DaoException;
import epam.zlatamigas.surveyplatform.model.entity.User;
import epam.zlatamigas.surveyplatform.model.entity.UserRole;
import epam.zlatamigas.surveyplatform.model.entity.UserStatus;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    int FILTER_ROLE_ALL = 0;
    int FILTER_ROLE_ADMIN = 1;
    int FILTER_ROLE_USER = 2;
    int FILTER_STATUS_ALL = 0;
    int FILTER_STATUS_ACTIVE = 1;
    int FILTER_STATUS_BANNED = 2;

    /**
     * Authenticate user: get User with email and password from DB.
     *
     * @param email    User email.
     * @param password Encrypted password.
     * @return User without password if user with such email and password exists, otherwise - Optional.empty().
     * @throws DaoException
     */
    Optional<User> authenticate(String email, String password) throws DaoException;

    /**
     * Find user by email.
     *
     * @param email User email.
     * @return User without password if user with such email exists, otherwise - Optional.empty().
     * @throws DaoException
     */
    Optional<User> findByEmail(String email) throws DaoException;

    /**
     * Find users according to requested filter and search parameters
     *
     * @param filterRoleId   FILTER_ROLE_ALL (0) - search through both users and admins,
     *                       FILTER_ROLE_ADMIN (1) - search through admins,
     *                       FILTER_ROLE_USER (2) - search through users.
     * @param filterStatusID FILTER_STATUS_ALL (0) - search through both active and banned users,
     *                       FILTER_STATUS_ACTIVE (1) - search through active users,
     *                       FILTER_STATUS_BANNED (2) - search through banned users.
     * @param searchWords    Words contained in email. Case insensitive. If array size is 0, then all survey names are acceptable.
     * @param orderType      Order type: ASC - ascending, DESC - descending.
     * @return List of users without passwords according to search and filter parameters.
     * @throws DaoException
     */
    List<User> findUsersBySearch(int filterRoleId, int filterStatusID, String[] searchWords, DbOrderType orderType) throws DaoException;

    /**
     * Change user role or/and status.
     *
     * @param userId Id of existing user.
     * @param role   UserRole: ADMIN or USER, GUEST is not supported.
     * @param status UserStatus: ACTIVE or BANNED.
     * @return True if user exist and was updated.
     * @throws DaoException
     */
    boolean updateRoleStatus(int userId, UserRole role, UserStatus status) throws DaoException;

    /**
     * Change user password.
     *
     * @param userId   Id of existing user.
     * @param password New encrypted password.
     * @return True if user exist and was updated.
     * @throws DaoException
     */
    boolean updatePassword(int userId, String password) throws DaoException;
}
