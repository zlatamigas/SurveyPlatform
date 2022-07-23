package epam.zlatamigas.surveyplatform.controller.command.impl.changepassword;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.entity.User;
import epam.zlatamigas.surveyplatform.model.entity.UserRole;
import epam.zlatamigas.surveyplatform.service.UserService;
import epam.zlatamigas.surveyplatform.service.impl.UserServiceImpl;
import epam.zlatamigas.surveyplatform.util.validator.FormValidator;
import epam.zlatamigas.surveyplatform.util.validator.impl.ChangePasswordFormValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.REDIRECT;

public class FinishChangePasswordCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();
        String page = CHANGE_PASSWORD;
        PageChangeType pageChangeType = FORWARD;

        User user = (User) session.getAttribute(SESSION_ATTRIBUTE_USER);

        boolean enableChange = true;
        if (user == null || user.getRole() == UserRole.GUEST) {

            String email = (String) session.getAttribute(SESSION_ATTRIBUTE_CHANGE_PASSWORD_EMAIL);
            Integer keySent = (Integer) session.getAttribute(SESSION_ATTRIBUTE_CHANGE_PASSWORD_KEY_SENT);
            Integer keyReceived = (Integer) session.getAttribute(SESSION_ATTRIBUTE_CHANGE_PASSWORD_KEY_RECEIVED);

            if (email == null || keySent == null || keyReceived == null || !keySent.equals(keyReceived)) {
                enableChange = false;
            }
        }

        if (enableChange) {
            FormValidator validator = ChangePasswordFormValidator.getInstance();
            Map<String, String[]> requestParameters = request.getParameterMap();
            Map<String, String> validationFeedback = validator.validateForm(requestParameters);

            if (validationFeedback.isEmpty()) {
                String password = request.getParameter(PARAMETER_PASSWORD);

                UserService service = UserServiceImpl.getInstance();
                try {
                    String email = switch (user.getRole()) {
                        case ADMIN, USER -> user.getEmail();
                        default -> (String) session.getAttribute(SESSION_ATTRIBUTE_CHANGE_PASSWORD_EMAIL);
                    };

                    service.changePassword(email, password);

                    session.invalidate();
                    page = SIGN_IN;
                    pageChangeType = REDIRECT;

                } catch (ServiceException e) {
                    throw new CommandException(e.getMessage(), e);
                }
            } else {
                request.setAttribute(REQUEST_ATTRIBUTE_FORM_INVALID, validationFeedback);
            }
        } else {
            page = HOME;
            pageChangeType = REDIRECT;
        }

        return new Router(page, pageChangeType);
    }
}
