package epam.zlatamigas.surveyplatform.controller.command.impl.finish;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.entity.User;
import epam.zlatamigas.surveyplatform.model.entity.UserStatus;
import epam.zlatamigas.surveyplatform.service.UserService;
import epam.zlatamigas.surveyplatform.service.impl.UserServiceImpl;
import epam.zlatamigas.surveyplatform.util.validator.FormValidator;
import epam.zlatamigas.surveyplatform.util.validator.impl.SignInFormValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.HOME;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.SIGN_IN;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.REDIRECT;
import static epam.zlatamigas.surveyplatform.util.locale.LocalisedMessageKey.MESSAGE_INVALID_USER_EXISTS_BANNED;
import static epam.zlatamigas.surveyplatform.util.locale.LocalisedMessageKey.MESSAGE_INVALID_USER_EXISTS_LOGIN;

public class FinishSignInCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();
        String page = SIGN_IN;
        PageChangeType pageChangeType = FORWARD;

        FormValidator validator = SignInFormValidator.getInstance();
        Map<String, String[]> requestParameters = request.getParameterMap();
        Map<String, String> validationFeedback = validator.validateForm(requestParameters);

        if (validationFeedback.isEmpty()) {
            String email = request.getParameter(PARAMETER_EMAIL);
            String password = request.getParameter(PARAMETER_PASSWORD);

            UserService service = UserServiceImpl.getInstance();
            try {
                Optional<User> userFromDb = service.authenticate(email, password);

                if (userFromDb.isPresent()) {
                    User user = userFromDb.get();

                    if (user.getStatus() == UserStatus.ACTIVE) {
                        session.setAttribute(SESSION_ATTRIBUTE_USER, user);

                        page = HOME;
                        pageChangeType = REDIRECT;
                    } else {
                        request.setAttribute(REQUEST_ATTRIBUTE_USER_BANNED, MESSAGE_INVALID_USER_EXISTS_BANNED);
                    }
                } else {
                    request.setAttribute(REQUEST_ATTRIBUTE_USER_INVALID, MESSAGE_INVALID_USER_EXISTS_LOGIN);
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
