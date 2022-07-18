package epam.zlatamigas.surveyplatform.controller.command.impl.samepage;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.entity.Survey;
import epam.zlatamigas.surveyplatform.model.entity.SurveyStatus;
import epam.zlatamigas.surveyplatform.model.entity.User;
import epam.zlatamigas.surveyplatform.service.SurveyService;
import epam.zlatamigas.surveyplatform.service.impl.SurveyServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Optional;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.PARAMETER_SURVEY_ID;
import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.SESSION_ATTRIBUTE_USER;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.URL_REDIRECT_USER_SURVEYS;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;

public class RestartSurveyCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();

        SurveyService surveyService = SurveyServiceImpl.getInstance();
        try {
            int creatorId = ((User) session.getAttribute(SESSION_ATTRIBUTE_USER)).getUserId();
            int surveyId = Integer.parseInt(request.getParameter(PARAMETER_SURVEY_ID));

            Optional<Survey> survey = surveyService.findCreatorSurveyInfo(surveyId, creatorId);
            if(survey.isPresent()){
                surveyService.insert(survey.get());
            }

        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException(e);
        }

        return new Router(URL_REDIRECT_USER_SURVEYS, FORWARD);
    }
}