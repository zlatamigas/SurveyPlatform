package epam.zlatamigas.surveyplatform.service;

import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> authenticate(String email, String password) throws ServiceException;
    boolean insert(User user) throws ServiceException;
}
