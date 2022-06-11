package epam.zlatamigas.surveyplatform.controller.command.impl.finish;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.entity.*;
import epam.zlatamigas.surveyplatform.service.SurveyService;
import epam.zlatamigas.surveyplatform.service.impl.SurveyServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.SURVEYS;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;

public class FinishSurveyAttemptCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String page = SURVEYS;

        Survey survey = (Survey) session.getAttribute(ATTRIBUTE_SURVEY_ATTEMPT);
        User user = (User) session.getAttribute(ATTRIBUTE_USER);


        // TODO: validation

        // TODO: update survey values
        List<SurveyQuestion> questions = survey.getQuestions();
        for (SurveyQuestion question : questions) {
            if (question.isSelectMultiple()) {
                String[] checkboxes = request.getParameterValues(BUTTONGROUP_NAME_CHECKBOX_ANSWERS + question.getQuestionId());
                if (checkboxes.length > 0) {
                    Set<Integer> selectedAnswerIds = new HashSet<>();
                    for (String checkbox : checkboxes) {
                        selectedAnswerIds.add(Integer.parseInt(checkbox));
                    }
                    question.getAnswers().forEach(answer
                            -> answer.setSelectedCount(selectedAnswerIds.contains(answer.getQuestionAnswerId()) ? 1 : 0));
                } else {
                    // TODO: No checkbox selected - validation
                }
            } else {
                String radiobutton = request.getParameter(BUTTONGROUP_NAME_RADIO_ANSWERS + question.getQuestionId());
                try {
                    int selectedAnswerId = Integer.parseInt(radiobutton);
                    question.getAnswers().forEach(answer
                            -> answer.setSelectedCount(selectedAnswerId == answer.getQuestionAnswerId() ? 1 : 0));
                } catch (NumberFormatException e) {
                    // TODO: No radio selected - validation
                }
            }
        }

        // TODO: service
        SurveyService surveyService = SurveyServiceImpl.getInstance();
        try {
            SurveyUserAttempt surveyAttempt = new SurveyUserAttempt();
            surveyAttempt.setSurvey(survey);
            if(user!=null){
                surveyAttempt.setUser(user);
            }
            surveyAttempt.setFinishedDate(LocalDateTime.now());

            surveyService.updateParticipantSurveyResult(surveyAttempt);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        session.removeAttribute(ATTRIBUTE_SURVEY_ATTEMPT);
        session.setAttribute(ATTRIBUTE_CURRENT_PAGE, page);

        return new Router(page, FORWARD);
    }
}
