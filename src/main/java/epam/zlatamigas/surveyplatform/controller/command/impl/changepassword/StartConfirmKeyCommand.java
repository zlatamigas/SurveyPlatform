package epam.zlatamigas.surveyplatform.controller.command.impl.changepassword;

import com.oracle.wls.shaded.org.apache.regexp.RE;
import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType;
import epam.zlatamigas.surveyplatform.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.REDIRECT;

public class StartConfirmKeyCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();
        String page = RECEIVE_KEY;
        PageChangeType pageChangeType= FORWARD;

        if(session.getAttribute(SESSION_ATTRIBUTE_CHANGE_PASSWORD_EMAIL) != null
                && session.getAttribute(SESSION_ATTRIBUTE_CHANGE_PASSWORD_KEY_SENT) != null){
            session.setAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE, page);
        } else {
            page = HOME;
            pageChangeType = REDIRECT;
        }

        return new Router(page, pageChangeType);
    }
}
