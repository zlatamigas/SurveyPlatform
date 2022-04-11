package epam.zlatamigas.surveyplatform.model.dao;

import epam.zlatamigas.surveyplatform.exception.DaoException;
import epam.zlatamigas.surveyplatform.model.entity.User;

import java.util.Optional;

public interface UserDao {
    Optional<User> authenticate(String email, String password) throws DaoException;
}
