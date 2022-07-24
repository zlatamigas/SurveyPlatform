package epam.zlatamigas.surveyplatform.controller.command.impl.survey.attempt;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.entity.Survey;
import epam.zlatamigas.surveyplatform.service.SurveyService;
import epam.zlatamigas.surveyplatform.service.impl.SurveyServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static epam.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.REDIRECT;

public class StartSurveyAttemptCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();
        String page = SURVEYS;
        PageChangeType pageChangeType = REDIRECT;

        SurveyService service = SurveyServiceImpl.getInstance();
        String surveyIdStr = request.getParameter(PARAMETER_SURVEY_ID);
        if (surveyIdStr != null) {
            try {
                int surveyId = Integer.parseInt(surveyIdStr);

                Optional<Survey> survey = service.findParticipantSurveyInfo(surveyId);
                if (survey.isPresent()) {
                    session.setAttribute(SESSION_ATTRIBUTE_SURVEY_ATTEMPT, survey.get());

                    page = SURVEY_ATTEMPT;
                    pageChangeType = FORWARD;
                    session.setAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE,
                            String.format(URL_CONTROLLER_WITH_PARAMETERS_PATTERN, request.getQueryString()));
                }
            } catch (NumberFormatException e) {
                logger.warn("Passed invalid {} parameter", PARAMETER_SURVEY_ID);
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        }

        return new Router(page, pageChangeType);
    }
}
