package epam.zlatamigas.surveyplatform.model.command.impl;

import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.command.Command;
import epam.zlatamigas.surveyplatform.model.entity.User;
import epam.zlatamigas.surveyplatform.model.navigation.PageNavigation;
import epam.zlatamigas.surveyplatform.model.service.UserService;
import epam.zlatamigas.surveyplatform.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

public class LoginCommand implements Command {

    private static final String LOGIN_PARAMETER = "login";
    private static final String PASSWORD_PARAMETER = "password";

    private static final String USER_PARAMETER = "user";

    private static final String LOGIN_MSG_PARAMETER = "login_msg";
    private static final String LOGIN_MSG = "Incorrect login or password";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {

        String login = request.getParameter(LOGIN_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);

        UserService service = UserServiceImpl.getInstance();
        String page;
        HttpSession session = request.getSession();
        try {

            Optional<User> userFromDb = service.authenticate(login, password);

            if (userFromDb.isPresent()) {
                request.setAttribute(USER_PARAMETER, login);

                session.setAttribute("user_name", login);
                // локаль
                // роль
                // тек

                page = PageNavigation.USER_HOME_PAGE;
            } else {
                request.setAttribute(LOGIN_MSG_PARAMETER, LOGIN_MSG);
                page = PageNavigation.DEFAULT_PAGE;
            }

            // для возврата на страницу с локалью
            session.setAttribute("current_page", page);
        } catch (ServiceException e) {
            throw  new CommandException(e.getMessage(), e);
        }

        return page;
    }
}
