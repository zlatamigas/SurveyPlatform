package epam.zlatamigas.surveyplatform.controller.command.impl.samepage;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.entity.User;
import epam.zlatamigas.surveyplatform.service.SurveyService;
import epam.zlatamigas.surveyplatform.service.impl.SurveyServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.URL_REDIRECT_USER_SURVEYS;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.REDIRECT;

public class AdminDeleteSurveyCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();

        SurveyService surveyService = SurveyServiceImpl.getInstance();
        String surveyIdStr = request.getParameter(PARAMETER_SURVEY_ID);
        String userIdStr = request.getParameter(PARAMETER_USER_ID);
        if (surveyIdStr != null && userIdStr != null) {
            try {
                int surveyId = Integer.parseInt(surveyIdStr);
                int creatorId = Integer.parseInt(userIdStr);

                surveyService.delete(surveyId, creatorId);
            } catch (NumberFormatException e) {
                logger.warn("Passed invalid id parameter: {}", e.getMessage());
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        }

        return new Router((String) session.getAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE), REDIRECT);
    }
}
