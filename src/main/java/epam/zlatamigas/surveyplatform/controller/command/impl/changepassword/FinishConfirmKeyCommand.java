package epam.zlatamigas.surveyplatform.controller.command.impl.changepassword;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static epam.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.REDIRECT;
import static epam.zlatamigas.surveyplatform.util.locale.LocalisedMessageKey.MESSAGE_INVALID_PASSWORD_CHANGE_CONFIRM_KEY;

public class FinishConfirmKeyCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();
        String page = RECEIVE_KEY;
        PageChangeType pageChangeType = FORWARD;

        String receivedKeyStr = request.getParameter(PARAMETER_RECEIVED_PASSWORD_CHANGE_KEY);
        Integer keySent = (Integer) session.getAttribute(SESSION_ATTRIBUTE_CHANGE_PASSWORD_KEY_SENT);
        if (session.getAttribute(SESSION_ATTRIBUTE_CHANGE_PASSWORD_EMAIL) != null
                && keySent != null
                && receivedKeyStr != null) {
            try {
                int receivedKey = Integer.parseInt(receivedKeyStr);

                if (receivedKey == keySent) {
                    session.setAttribute(SESSION_ATTRIBUTE_CHANGE_PASSWORD_KEY_RECEIVED, receivedKey);

                    page = CHANGE_PASSWORD;
                    pageChangeType = REDIRECT;
                    session.setAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE, page);
                } else {
                    request.setAttribute(REQUEST_ATTRIBUTE_PASSWORD_CHANGE_CONFIRM_KEY_INVALID, MESSAGE_INVALID_PASSWORD_CHANGE_CONFIRM_KEY);
                }
            } catch (NumberFormatException e) {
                logger.warn("Passed invalid {} parameter", PARAMETER_RECEIVED_PASSWORD_CHANGE_KEY);
                request.setAttribute(REQUEST_ATTRIBUTE_PASSWORD_CHANGE_CONFIRM_KEY_INVALID, MESSAGE_INVALID_PASSWORD_CHANGE_CONFIRM_KEY);
            }

        } else {
            page = HOME;
            pageChangeType = REDIRECT;
        }


        return new Router(page, pageChangeType);
    }
}
