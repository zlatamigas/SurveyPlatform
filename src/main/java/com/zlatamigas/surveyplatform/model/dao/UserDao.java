package com.zlatamigas.surveyplatform.model.dao;

import com.zlatamigas.surveyplatform.exception.DaoException;
import com.zlatamigas.surveyplatform.model.entity.User;
import com.zlatamigas.surveyplatform.model.entity.UserRole;
import com.zlatamigas.surveyplatform.model.entity.UserStatus;

import java.util.List;
import java.util.Optional;

/**
 * DAO for manipulating User data in table users.
 */
public interface UserDao extends BaseDao<User> {

    int FILTER_ROLE_ALL = 0;
    int FILTER_ROLE_ADMIN = 1;
    int FILTER_ROLE_USER = 2;
    int FILTER_STATUS_ALL = 0;
    int FILTER_STATUS_ACTIVE = 1;
    int FILTER_STATUS_BANNED = 2;

    /**
     * Insert new user.
     *
     * @param user User with already encrypted password.
     * @return True if new user was inserted, false if user with such email already exists.
     * @throws DaoException If a database access error occurs.
     */
    @Override
    boolean insert(User user) throws DaoException;

    /**
     * Delete user by id.
     *
     * @param id User id.
     * @return True if existing user was deleted, otherwise false.
     * @throws DaoException If a database access error occurs.
     */
    @Override
    boolean delete(int id) throws DaoException;

    /**
     * Find user by id.
     *
     * @param id User id.
     * @return User without password if user with such email exists, otherwise Optional.empty().
     * @throws DaoException If a database access error occurs.
     */
    @Override
    Optional<User> findById(int id) throws DaoException;

    /**
     * Authenticate user: get User with email and password from DB.
     *
     * @param email    User email.
     * @param password Encrypted password.
     * @return User without password if user with such email and password exists,
     * otherwise Optional.empty().
     * @throws DaoException If a database access error occurs.
     */
    Optional<User> authenticate(String email, String password) throws DaoException;

    /**
     * Find user by email.
     *
     * @param email User email.
     * @return User without password if user with such email exists, otherwise Optional.empty().
     * @throws DaoException If a database access error occurs.
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
     * @param searchWords    Words contained in email. Case insensitive. If array size is 0,
     *                       then all survey names are acceptable.
     * @param orderType      Order type: ASC - ascending, DESC - descending.
     * @return List of users without passwords according to search and filter parameters.
     * @throws DaoException If a database access error occurs or if invalid filter parameters are passed.
     */
    List<User> findUsersBySearch(int filterRoleId,
                                 int filterStatusID,
                                 String[] searchWords,
                                 DbOrderType orderType) throws DaoException;

    /**
     * Change user role or/and status.
     *
     * @param userId Id of existing user.
     * @param role   UserRole: ADMIN or USER, GUEST is not supported.
     * @param status UserStatus: ACTIVE or BANNED.
     * @return True if user exist and was updated.
     * @throws DaoException If a database access error occurs.
     */
    boolean updateRoleStatus(int userId, UserRole role, UserStatus status) throws DaoException;

    /**
     * Change user password.
     *
     * @param userId   Id of existing user.
     * @param password New encrypted password.
     * @return True if user exist and was updated.
     * @throws DaoException If a database access error occurs.
     */
    boolean updatePassword(int userId, String password) throws DaoException;

    /**
     * Unsupported update operation for user. For update use methods:
     * {@link #updateRoleStatus(int, UserRole, UserStatus)} or
     * {@link #updatePassword(int, String)}
     *
     * @throws DaoException Never.
     * @throws UnsupportedOperationException Always.
     * @deprecated Unsupported operation.
     */
    @Override
    boolean update(User user) throws DaoException;
}
