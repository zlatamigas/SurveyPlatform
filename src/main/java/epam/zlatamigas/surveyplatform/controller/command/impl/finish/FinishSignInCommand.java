package epam.zlatamigas.surveyplatform.controller.command.impl.finish;

import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.controller.command.Command;
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
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.REDIRECT;
import static epam.zlatamigas.surveyplatform.util.locale.LocalisedMessageKey.MESSAGE_USER_BANNED;
import static epam.zlatamigas.surveyplatform.util.locale.LocalisedMessageKey.MESSAGE_USER_INVALID;

public class FinishSignInCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();
        String page = SIGN_IN;
        PageChangeType pageChangeType = FORWARD;

        String email = request.getParameter(PARAMETER_EMAIL);
        String password = request.getParameter(PARAMETER_PASSWORD);

        FormValidator validator = SignInFormValidator.getInstance();
        Map<String, String[]> requestParameters = request.getParameterMap();
        Map<String, String> validationFeedback = validator.validateForm(requestParameters);

        if(validationFeedback.isEmpty()){
            UserService service = UserServiceImpl.getInstance();
            try {
                Optional<User> userFromDb = service.authenticate(email, password);

                if (userFromDb.isPresent()) {
                    User user = userFromDb.get();

                    if(user.getStatus() == UserStatus.ACTIVE){
                        session.setAttribute(ATTRIBUTE_USER, user);
                        page = HOME;
                        pageChangeType = REDIRECT;
                    } else {

                    }
                    request.setAttribute(REQUEST_ATTRIBUTE_USER_BANNED, MESSAGE_USER_BANNED);
                } else {
                    request.setAttribute(REQUEST_ATTRIBUTE_USER_INVALID, MESSAGE_USER_INVALID);
                }

                session.setAttribute(ATTRIBUTE_CURRENT_PAGE, page);
            } catch (ServiceException e) {
                throw new CommandException(e.getMessage(), e);
            }
        } else {
            request.setAttribute(REQUEST_ATTRIBUTE_FORM_INVALID, validationFeedback);
        }

        return new Router(page, pageChangeType);
    }
}
