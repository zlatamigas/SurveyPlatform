package epam.zlatamigas.surveyplatform.model.dao;

import epam.zlatamigas.surveyplatform.exception.DaoException;
import epam.zlatamigas.surveyplatform.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> authenticate(String email, String password) throws DaoException;
    Optional<User> findByEmail(String email) throws DaoException;
    List<User> findUsersBySearch(int filterRoleId, int filterStatusID, String[] searchWords, DbOrderType orderType) throws DaoException;

    boolean insert(User user) throws DaoException;
    boolean delete(int id) throws DaoException;

    Optional<User> updateWithoutPassword(User user) throws DaoException;
    boolean updatePassword(int userId, String password) throws DaoException;

    Optional<User> findByIdWithoutPassword(int id) throws DaoException;
}
