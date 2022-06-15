package epam.zlatamigas.surveyplatform.controller.command.impl.finish;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.entity.User;
import epam.zlatamigas.surveyplatform.service.UserService;
import epam.zlatamigas.surveyplatform.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.ATTRIBUTE_CURRENT_PAGE;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;

// TODO
public class FinishSignUpCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();
        String page;

        String email = request.getParameter(PARAMETER_EMAIL);
        String password = request.getParameter(PARAMETER_PASSWORD);
        String passwordRepeat = request.getParameter(PARAMETER_PASSWORD_REPEAT);

        // TODO: validation

        UserService service = UserServiceImpl.getInstance();
        try {
            if(service.insertNewUser(email, password)){
                page = SIGN_IN;
            } else {
                // TODO: user exists
                page = SIGN_UP;
            }

        } catch (ServiceException e) {
            throw  new CommandException(e.getMessage(), e);
        }

        session.setAttribute(ATTRIBUTE_CURRENT_PAGE, page);
        return new Router(page, FORWARD);
    }
}
