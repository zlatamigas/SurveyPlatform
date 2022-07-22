package epam.zlatamigas.surveyplatform.controller.command.impl.start;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.entity.Survey;
import epam.zlatamigas.surveyplatform.model.entity.User;
import epam.zlatamigas.surveyplatform.service.SurveyService;
import epam.zlatamigas.surveyplatform.service.UserService;
import epam.zlatamigas.surveyplatform.service.impl.SurveyServiceImpl;
import epam.zlatamigas.surveyplatform.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.*;
import static epam.zlatamigas.surveyplatform.util.search.SearchParameter.*;

public class StartEditUserCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();
        String page = USERS;
        PageChangeType pageChangeType = REDIRECT;

        User admin = (User) session.getAttribute(SESSION_ATTRIBUTE_USER);

        String userIdStr = request.getParameter(REQUEST_ATTRIBUTE_PARAMETER_USER_ID);

        UserService userService = UserServiceImpl.getInstance();
        SurveyService surveyService = SurveyServiceImpl.getInstance();
        try {
            if (userIdStr != null) {
                int userId = Integer.parseInt(userIdStr);

                if (userId != admin.getUserId()) {

                    Optional<User> userOptional = userService.findById(userId);
                    if (userOptional.isPresent()) {
                        User user = userOptional.get();

                        request.setAttribute(REQUEST_ATTRIBUTE_PARAMETER_USER_ID, user.getUserId());
                        request.setAttribute(REQUEST_ATTRIBUTE_PARAMETER_USER_EMAIL, user.getEmail());
                        request.setAttribute(REQUEST_ATTRIBUTE_PARAMETER_USER_ROLE, user.getRole());
                        request.setAttribute(REQUEST_ATTRIBUTE_PARAMETER_USER_STATUS, user.getStatus());

                        List<Survey> surveyList = surveyService.findCreatorSurveysCommonInfoSearch(DEFAULT_FILTER_ID_ALL,
                                DEFAULT_SEARCH_WORDS,
                                DEFAULT_ORDER,
                                DEFAULT_FILTER_STR_ALL,
                                userId);
                        request.setAttribute(REQUEST_ATTRIBUTE_USER_SURVEYS, surveyList);

                        page = EDIT_USER;
                        pageChangeType = FORWARD;
                        session.setAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE,
                                String.format(URL_CONTROLLER_WITH_PARAMETERS_PATTERN, request.getQueryString()));
                    }
                }
            }
        } catch (NumberFormatException e) {
            logger.warn("Passed invalid {} parameter", REQUEST_ATTRIBUTE_PARAMETER_USER_ID);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }


        return new Router(page, pageChangeType);
    }
}
