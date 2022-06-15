package epam.zlatamigas.surveyplatform.service.impl;

import epam.zlatamigas.surveyplatform.exception.DaoException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.dao.impl.UserDaoImpl;
import epam.zlatamigas.surveyplatform.model.entity.User;
import epam.zlatamigas.surveyplatform.model.entity.UserRole;
import epam.zlatamigas.surveyplatform.model.entity.UserStatus;
import epam.zlatamigas.surveyplatform.service.UserService;
import epam.zlatamigas.surveyplatform.util.encoder.PasswordEncoder;
import epam.zlatamigas.surveyplatform.util.keygenerator.ChangePasswordKeyGenerator;
import epam.zlatamigas.surveyplatform.util.keygenerator.impl.ChangePasswordKeyGeneratorImpl;
import epam.zlatamigas.surveyplatform.util.mail.MailSender;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private static final String MESSAGE_REQUEST_CHANGE_PASSWORD_SUBJECT = "Change password";
    private static final String MESSAGE_REQUEST_CHANGE_PASSWORD_TEXT = "Your key: %d";

    private static UserServiceImpl instance = new UserServiceImpl();

    private UserServiceImpl(){
    }

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public Optional<User> authenticate(String email, String password) throws ServiceException {
        // TODO: validate email, password, encryption password (e.c. md5)
        UserDaoImpl userDao = UserDaoImpl.getInstance();
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
    public boolean changePassword(String email, String password) throws ServiceException {

        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            Optional<User> dbUser = userDao.findByEmail(email);

            if(dbUser.isPresent()){
                PasswordEncoder encoder = new PasswordEncoder();
                String encodedPassword = encoder.encode(password);

                User user = dbUser.get();
                user.setPassword(encodedPassword);

                userDao.update(user);
                return true;
            }
        } catch (DaoException | NoSuchAlgorithmException e) {
            throw new ServiceException(e);
        }
        return false;
    }

    @Override
    public boolean insertNewUser(String email, String password) throws ServiceException {
        // TODO: validate login, password, encryption password (e.c. md5)

        UserDaoImpl userDao = UserDaoImpl.getInstance();
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
    public int requestChangePassword(String email) throws ServiceException {
        MailSender sender = MailSender.getInstance();
        ChangePasswordKeyGenerator keyGenerator = ChangePasswordKeyGeneratorImpl.getInstance();

        int key = keyGenerator.generateKey();

        if(sender.sendMail(email, MESSAGE_REQUEST_CHANGE_PASSWORD_SUBJECT, String.format( MESSAGE_REQUEST_CHANGE_PASSWORD_TEXT, key))){
            return key;
        } else {
            throw  new ServiceException("Error while sending key to email: " + email);
        }
    }
}
