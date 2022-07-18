package epam.zlatamigas.surveyplatform.controller.command.impl.to;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.entity.Survey;
import epam.zlatamigas.surveyplatform.model.entity.User;
import epam.zlatamigas.surveyplatform.service.SurveyService;
import epam.zlatamigas.surveyplatform.service.impl.SurveyServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.SURVEY_RESULT;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;

public class SurveyResultCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();
        String page = SURVEY_RESULT;

        User user = (User) session.getAttribute(SESSION_ATTRIBUTE_USER);

        int surveyId = Integer.parseInt(request.getParameter(PARAMETER_SURVEY_ID));
        SurveyService surveyService = SurveyServiceImpl.getInstance();
        try {
            Survey survey = surveyService.findCreatorSurveyInfo(surveyId, user.getUserId()).orElse(new Survey());
            survey.getQuestions().forEach(surveyQuestion -> surveyQuestion.getAnswers().sort( (a1, a2) -> a2.getSelectedCount() - a1.getSelectedCount()));
            request.setAttribute(REQUEST_ATTRIBUTE_SURVEY_RESULT, survey);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        session.setAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE, page);

        return new Router(page, FORWARD);
    }
}
