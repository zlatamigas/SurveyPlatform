package epam.zlatamigas.surveyplatform.controller.command.impl.changepassword;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.service.UserService;
import epam.zlatamigas.surveyplatform.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;

public class ChangePasswordCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String page;

        String email = (String) session.getAttribute(SESSION_ATTRIBUTE_CHANGE_PASSWORD_EMAIL);

        String password = request.getParameter(PARAMETER_PASSWORD);
        String passwordRepeat = request.getParameter(PARAMETER_PASSWORD_REPEAT);

        UserService service = UserServiceImpl.getInstance();

        try {
            if(service.changePassword(email, password)){
                session.removeAttribute(SESSION_ATTRIBUTE_CHANGE_PASSWORD_EMAIL);
                page = SIGN_IN;
            } else {
                page = FORGOT_PASSWORD_CHANGE_PASSWORD;
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        session.setAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE, page);

        return new Router(page, FORWARD);
    }
}
