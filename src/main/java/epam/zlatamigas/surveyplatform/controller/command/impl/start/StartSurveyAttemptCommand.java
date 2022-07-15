package epam.zlatamigas.surveyplatform.controller.command.impl.start;

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

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.SURVEYS;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.SURVEY_ATTEMPT;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.REDIRECT;

public class StartSurveyAttemptCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String page = SURVEYS;
        PageChangeType pageChangeType = REDIRECT;

        String surveyIdStr = request.getParameter(PARAMETER_SURVEY_ID);

        if(surveyIdStr != null && !surveyIdStr.isBlank()){
            int surveyId = Integer.parseInt(surveyIdStr);
            try {
                SurveyService service = SurveyServiceImpl.getInstance();
                Optional<Survey> survey = service.findParticipantSurveyInfo(surveyId);

                if(survey.isPresent()){
                    page = SURVEY_ATTEMPT;
                    pageChangeType = FORWARD;
                    session.setAttribute(ATTRIBUTE_SURVEY_ATTEMPT, survey.get());
                }

            } catch (ServiceException e) {
                logger.error(e);
            }

        }

        session.setAttribute(ATTRIBUTE_CURRENT_PAGE, page);

        return new Router(page, pageChangeType);
    }
}
