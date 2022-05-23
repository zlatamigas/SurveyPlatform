package epam.zlatamigas.surveyplatform.controller.command.impl;

import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.model.entity.User;
import epam.zlatamigas.surveyplatform.service.UserService;
import epam.zlatamigas.surveyplatform.service.impl.UserServiceImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Optional;

import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.PARAMETER_EMAIL;
import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.PARAMETER_PASSWORD;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;
import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.ATTRIBUTE_CURRENT_PAGE;
import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.ATTRIBUTE_USER;

public class LoginCommand implements Command {

    private static final String LOGIN_MSG_PARAMETER = "login_msg";
    private static final String LOGIN_MSG = "Incorrect login or password";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        String login = request.getParameter(PARAMETER_EMAIL);
        String password = request.getParameter(PARAMETER_PASSWORD);

        UserService service = UserServiceImpl.getInstance();
        String page;
        HttpSession session = request.getSession();
        try {

            Optional<User> userFromDb = service.authenticate(login, password);

            if (userFromDb.isPresent()) {

                User user = userFromDb.get();
                session.setAttribute(ATTRIBUTE_USER, user);
                page = HOME;
            } else {
                request.setAttribute(LOGIN_MSG_PARAMETER, LOGIN_MSG);
                page = AUTHORISATION;
            }

            session.setAttribute(ATTRIBUTE_CURRENT_PAGE, page);
        } catch (ServiceException e) {
            throw  new CommandException(e.getMessage(), e);
        }

        return new Router(page, FORWARD);
    }
}
