package epam.zlatamigas.surveyplatform.model.command.impl;

import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.command.Command;
import epam.zlatamigas.surveyplatform.model.entity.User;
import epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation;
import epam.zlatamigas.surveyplatform.model.service.UserService;
import epam.zlatamigas.surveyplatform.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;
import static epam.zlatamigas.surveyplatform.model.entity.UserRole.ADMIN;
import static epam.zlatamigas.surveyplatform.model.entity.UserRole.USER;

public class LoginCommand implements Command {

    private static final String LOGIN_PARAMETER = "login";
    private static final String PASSWORD_PARAMETER = "password";

    private static final String USER_PARAMETER = "user";
    private static final String USER_ID_PARAMETER = "user_id";
    private static final String USER_STATUS_PARAMETER = "user_status";

    private static final String LOGIN_MSG_PARAMETER = "login_msg";
    private static final String LOGIN_MSG = "Incorrect login or password";

    private static final String CURRENT_PAGE = "current_page";


    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        String login = request.getParameter(LOGIN_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);

        UserService service = UserServiceImpl.getInstance();
        String page;
        HttpSession session = request.getSession();
        try {

            Optional<User> userFromDb = service.authenticate(login, password);

            if (userFromDb.isPresent()) {

                User user = userFromDb.get();

                session.setAttribute(USER_PARAMETER, login);
                session.setAttribute(USER_ID_PARAMETER, user.getUserId());
                session.setAttribute(USER_STATUS_PARAMETER, user.getStatus().name());

                page = switch (user.getRole()){
                    case ADMIN -> ADMIN_HOME;
                    case USER -> CLIENT_HOME;
                    default -> VISITOR_HOME;
                };
            } else {
                request.setAttribute(LOGIN_MSG_PARAMETER, LOGIN_MSG);
                page = AUTHORIZATION;
            }

            session.setAttribute(CURRENT_PAGE, page);
        } catch (ServiceException e) {
            throw  new CommandException(e.getMessage(), e);
        }

        return new Router(page, FORWARD);
    }
}
