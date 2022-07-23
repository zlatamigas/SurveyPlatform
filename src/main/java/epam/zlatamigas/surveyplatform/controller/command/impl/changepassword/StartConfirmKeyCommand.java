package epam.zlatamigas.surveyplatform.controller.command.impl.changepassword;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.SESSION_ATTRIBUTE_CURRENT_PAGE;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.RECEIVE_KEY;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.SEND_KEY_ON_EMAIL;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;

public class StartConfirmKeyCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();
        String page = RECEIVE_KEY;

        session.setAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE, page);

        return new Router(page, FORWARD);
    }
}