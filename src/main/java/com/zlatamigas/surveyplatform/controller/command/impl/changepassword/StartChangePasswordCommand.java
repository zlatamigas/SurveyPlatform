package com.zlatamigas.surveyplatform.controller.command.impl.changepassword;

import com.zlatamigas.surveyplatform.controller.command.Command;
import com.zlatamigas.surveyplatform.controller.navigation.Router;
import com.zlatamigas.surveyplatform.exception.CommandException;
import com.zlatamigas.surveyplatform.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder.*;
import static com.zlatamigas.surveyplatform.controller.navigation.PageNavigation.CHANGE_PASSWORD;
import static com.zlatamigas.surveyplatform.controller.navigation.PageNavigation.HOME;
import static com.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;
import static com.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.REDIRECT;

public class StartChangePasswordCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();
        String page = CHANGE_PASSWORD;
        Router.PageChangeType pageChangeType = FORWARD;

        User user = (User) session.getAttribute(SESSION_ATTRIBUTE_USER);
        boolean enableChange = switch (user.getRole()) {
            case ADMIN, USER -> true;
            default -> {
                String email = (String) session.getAttribute(SESSION_ATTRIBUTE_CHANGE_PASSWORD_EMAIL);
                Integer keySent = (Integer) session.getAttribute(SESSION_ATTRIBUTE_CHANGE_PASSWORD_KEY_SENT);
                Integer keyReceived = (Integer) session.getAttribute(SESSION_ATTRIBUTE_CHANGE_PASSWORD_KEY_RECEIVED);
                yield email != null
                        && keySent != null
                        && keySent.equals(keyReceived);
            }
        };

        if (enableChange) {
            session.setAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE, page);
        } else {
            page = HOME;
            pageChangeType = REDIRECT;
        }

        return new Router(page, pageChangeType);
    }
}
