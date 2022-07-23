package epam.zlatamigas.surveyplatform.controller.command.impl.changepassword;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.entity.User;
import epam.zlatamigas.surveyplatform.service.UserService;
import epam.zlatamigas.surveyplatform.service.impl.UserServiceImpl;
import epam.zlatamigas.surveyplatform.util.validator.FormValidator;
import epam.zlatamigas.surveyplatform.util.validator.impl.ChangePasswordFormValidator;
import epam.zlatamigas.surveyplatform.util.validator.impl.SignUpFormValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Map;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.REQUEST_ATTRIBUTE_FORM_INVALID;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.CHANGE_PASSWORD;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.SIGN_IN;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.REDIRECT;
import static epam.zlatamigas.surveyplatform.util.locale.LocalisedMessageKey.MESSAGE_INVALID_USER_EXISTS_LOGUP;

public class FinishChangePasswordCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();
        String page = CHANGE_PASSWORD;
        PageChangeType pageChangeType = FORWARD;

        User user = (User) session.getAttribute(SESSION_ATTRIBUTE_USER);

        FormValidator validator = ChangePasswordFormValidator.getInstance();
        Map<String, String[]> requestParameters = request.getParameterMap();
        Map<String, String> validationFeedback = validator.validateForm(requestParameters);

        if (validationFeedback.isEmpty()) {
            String password = request.getParameter(PARAMETER_PASSWORD);

            UserService service = UserServiceImpl.getInstance();
            try {
                switch (user.getRole()){
                    case ADMIN, USER -> {
                        service.changePassword(user.getEmail(), password);
                    }
                    default -> {
                    }
                }
                session.invalidate();
                page = SIGN_IN;
                pageChangeType = REDIRECT;

            } catch (ServiceException e) {
                throw new CommandException(e.getMessage(), e);
            }
        } else {
            request.setAttribute(REQUEST_ATTRIBUTE_FORM_INVALID, validationFeedback);
        }

        return new Router(page, pageChangeType);
    }
}
