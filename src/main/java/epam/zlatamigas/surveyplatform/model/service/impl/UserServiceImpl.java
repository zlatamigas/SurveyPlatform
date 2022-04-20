package epam.zlatamigas.surveyplatform.model.service.impl;

import epam.zlatamigas.surveyplatform.exception.DaoException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.dao.impl.UserDaoImpl;
import epam.zlatamigas.surveyplatform.model.entity.User;
import epam.zlatamigas.surveyplatform.model.service.UserService;
import epam.zlatamigas.surveyplatform.model.util.PasswordEncoder;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private static UserServiceImpl instance = new UserServiceImpl();

    private UserServiceImpl(){
    }

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public Optional<User> authenticate(String login, String password) throws ServiceException {
        // validate login, password, encryption password (e.c. md5)
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        Optional<User> user = Optional.empty();
        try {
            PasswordEncoder encoder = new PasswordEncoder();
            String encodedPassword = encoder.encode(password);
            user = userDao.authenticate(login, encodedPassword);
        } catch (DaoException | NoSuchAlgorithmException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    @Override
    public boolean insert(User user) throws ServiceException {
        // validate login, password, encryption password (e.c. md5)
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            PasswordEncoder encoder = new PasswordEncoder();
            String encodedPassword = encoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            return userDao.insert(user);
        } catch (DaoException | NoSuchAlgorithmException e) {
            throw new ServiceException(e);
        }
    }
}
