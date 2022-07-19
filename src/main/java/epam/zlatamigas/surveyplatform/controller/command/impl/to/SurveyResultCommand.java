package epam.zlatamigas.surveyplatform.controller.command.impl.to;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.entity.Survey;
import epam.zlatamigas.surveyplatform.model.entity.SurveyStatus;
import epam.zlatamigas.surveyplatform.model.entity.User;
import epam.zlatamigas.surveyplatform.service.SurveyService;
import epam.zlatamigas.surveyplatform.service.impl.SurveyServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.REDIRECT;

public class SurveyResultCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();
        String page = HOME;
        PageChangeType pageChangeType = REDIRECT;

        User creator = (User) session.getAttribute(SESSION_ATTRIBUTE_USER);

        String surveyIdStr = request.getParameter(PARAMETER_SURVEY_ID);

        SurveyService surveyService = SurveyServiceImpl.getInstance();
        try {
            if (surveyIdStr != null) {
                int surveyId = Integer.parseInt(surveyIdStr);

                Optional<Survey> survey = surveyService.findCreatorSurveyInfo(surveyId, creator.getUserId());
                if (survey.isPresent() && survey.get().getStatus() == SurveyStatus.CLOSED) {
                    survey.get().getQuestions()
                            .forEach(surveyQuestion -> surveyQuestion.getAnswers()
                                    .sort((a1, a2) -> a2.getSelectedCount() - a1.getSelectedCount()));
                    request.setAttribute(REQUEST_ATTRIBUTE_SURVEY_RESULT, survey.get());

                    page = SURVEY_RESULT;
                    pageChangeType = FORWARD;
                    session.setAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE,
                            String.format(URL_CONTROLLER_WITH_PARAMETERS_PATTERN, request.getQueryString()));
                }
            }
        } catch (NumberFormatException e) {
            logger.warn("Passed invalid {} parameter", PARAMETER_SURVEY_ID);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return new Router(page, pageChangeType);
    }
}
