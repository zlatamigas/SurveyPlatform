package epam.zlatamigas.surveyplatform.controller.command.impl.changepassword;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.FORGOT_PASSWORD_CHANGE_PASSWORD;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.FORGOT_PASSWORD_RECEIVE_KEY;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;

public class ConfirmPasswordKeyCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String page;

        int key = (int)session.getAttribute(ATTRIBUTE_CHANGE_PASSWORD_KEY);
        int receivedKey = Integer.parseInt(request.getParameter(PARAMETER_FORGOT_PASSWORD_CHANGE_KEY));

        if(receivedKey == key){
            session.removeAttribute(ATTRIBUTE_CHANGE_PASSWORD_KEY);
            page = FORGOT_PASSWORD_CHANGE_PASSWORD;
        } else {
            page = FORGOT_PASSWORD_RECEIVE_KEY;
        }

        session.setAttribute(ATTRIBUTE_CURRENT_PAGE, page);

        return new Router(page, FORWARD);
    }
}
