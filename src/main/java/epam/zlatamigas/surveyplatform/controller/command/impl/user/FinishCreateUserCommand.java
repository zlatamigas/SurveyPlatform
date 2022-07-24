package epam.zlatamigas.surveyplatform.controller.command.impl.user;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.service.UserService;
import epam.zlatamigas.surveyplatform.service.impl.UserServiceImpl;
import epam.zlatamigas.surveyplatform.util.validator.FormValidator;
import epam.zlatamigas.surveyplatform.util.validator.impl.SignUpFormValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static epam.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.CREATE_USER;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.USERS;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.REDIRECT;
import static epam.zlatamigas.surveyplatform.util.locale.LocalisedMessageKey.MESSAGE_INVALID_USER_EXISTS_LOGUP;

public class FinishCreateUserCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        String page = CREATE_USER;
        PageChangeType pageChangeType = FORWARD;

        FormValidator validator = SignUpFormValidator.getInstance();
        Map<String, String[]> requestParameters = request.getParameterMap();
        Map<String, String> validationFeedback = validator.validateForm(requestParameters);

        if (validationFeedback.isEmpty()) {

            String email = request.getParameter(PARAMETER_EMAIL);
            String password = request.getParameter(PARAMETER_PASSWORD);
            String roleName = request.getParameter(REQUEST_ATTRIBUTE_PARAMETER_USER_ROLE);
            String statusName = request.getParameter(REQUEST_ATTRIBUTE_PARAMETER_USER_STATUS);

            UserService service = UserServiceImpl.getInstance();
            try {
                if (service.insertUser(email, password, roleName, statusName)) {
                    page = USERS;
                    pageChangeType = REDIRECT;
                } else {
                    request.setAttribute(REQUEST_ATTRIBUTE_USER_EXISTS, MESSAGE_INVALID_USER_EXISTS_LOGUP);
                }

            } catch (ServiceException e) {
                throw new CommandException(e.getMessage(), e);
            }

        } else {
            request.setAttribute(REQUEST_ATTRIBUTE_FORM_INVALID, validationFeedback);
        }

        return new Router(page, pageChangeType);
    }
}
