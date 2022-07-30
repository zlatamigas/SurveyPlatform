package com.zlatamigas.surveyplatform.controller.command.impl.changepassword;

import com.zlatamigas.surveyplatform.controller.command.Command;
import com.zlatamigas.surveyplatform.controller.navigation.Router;
import com.zlatamigas.surveyplatform.exception.CommandException;
import com.zlatamigas.surveyplatform.exception.ServiceException;
import com.zlatamigas.surveyplatform.service.UserService;
import com.zlatamigas.surveyplatform.service.impl.UserServiceImpl;
import com.zlatamigas.surveyplatform.util.validator.PatternValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Map;
import java.util.Optional;

import static com.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder.*;
import static com.zlatamigas.surveyplatform.controller.navigation.PageNavigation.*;
import static com.zlatamigas.surveyplatform.controller.navigation.Router.*;
import static com.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;
import static com.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.REDIRECT;
import static com.zlatamigas.surveyplatform.util.locale.LocalisedMessageKey.*;

public class FinishSendKeyToEmailCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();
        String page = SEND_KEY_TO_EMAIL;
        PageChangeType pageChangeType = FORWARD;

        String locale = (String) session.getAttribute(SESSION_ATTRIBUTE_PARAMETER_LOCALISATION);

        String email = request.getParameter(PARAMETER_EMAIL);

        PatternValidator validator = PatternValidator.getInstance();
        if(validator.validEmail(email)){

            UserService service = UserServiceImpl.getInstance();

            try {
                Optional<Integer> key = service.requestChangePassword(email, locale);
                if(key.isPresent()){
                    session.setAttribute(SESSION_ATTRIBUTE_CHANGE_PASSWORD_EMAIL, email);
                    session.setAttribute(SESSION_ATTRIBUTE_CHANGE_PASSWORD_KEY_SENT, key.get());

                    page = RECEIVE_KEY;
                    pageChangeType = REDIRECT;
                    session.setAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE, page);
                } else {
                    request.setAttribute(REQUEST_ATTRIBUTE_FORM_INVALID, Map.of(
                            PARAMETER_EMAIL, MESSAGE_INVALID_USER_EMAIL
                    ));
                }
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        } else {
            request.setAttribute(REQUEST_ATTRIBUTE_FORM_INVALID, Map.of(
                    PARAMETER_EMAIL, MESSAGE_INVALID_USER_EMAIL
            ));
        }

        return new Router(page, pageChangeType);
    }
}
