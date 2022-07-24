package epam.zlatamigas.surveyplatform.controller.command.impl.survey.attempt;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.entity.Survey;
import epam.zlatamigas.surveyplatform.model.entity.SurveyQuestion;
import epam.zlatamigas.surveyplatform.model.entity.SurveyUserAttempt;
import epam.zlatamigas.surveyplatform.model.entity.User;
import epam.zlatamigas.surveyplatform.service.SurveyService;
import epam.zlatamigas.surveyplatform.service.impl.SurveyServiceImpl;
import epam.zlatamigas.surveyplatform.util.validator.FormValidator;
import epam.zlatamigas.surveyplatform.util.validator.impl.SurveyUserAttemptFormValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static epam.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.SURVEYS;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.SURVEY_ATTEMPT;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.REDIRECT;

public class FinishSurveyAttemptCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();
        String page = SURVEY_ATTEMPT;
        PageChangeType pageChangeType = FORWARD;

        Survey survey = (Survey) session.getAttribute(SESSION_ATTRIBUTE_SURVEY_ATTEMPT);
        if (survey != null) {
            List<SurveyQuestion> questions = survey.getQuestions();
            for (SurveyQuestion question : questions) {
                if (question.isSelectMultiple()) {
                    String[] checkboxes = request.getParameterValues(BUTTONGROUP_NAME_CHECKBOX_ANSWERS + question.getQuestionId());

                    if (checkboxes != null) {
                        Set<Integer> selectedAnswerIds = new HashSet<>();
                        for (String checkbox : checkboxes) {
                            selectedAnswerIds.add(Integer.parseInt(checkbox));
                        }
                        question.getAnswers().forEach(answer
                                -> answer.setSelectedCount(selectedAnswerIds.contains(answer.getQuestionAnswerId()) ? 1 : 0));
                    } else {
                        question.getAnswers().forEach(answer -> answer.setSelectedCount(0));
                    }
                } else {
                    String radiobutton = request.getParameter(BUTTONGROUP_NAME_RADIO_ANSWERS + question.getQuestionId());

                    if (radiobutton != null) {
                        int selectedAnswerId = Integer.parseInt(radiobutton);
                        question.getAnswers().forEach(answer
                                -> answer.setSelectedCount(selectedAnswerId == answer.getQuestionAnswerId() ? 1 : 0));
                    }
                }
            }

            session.setAttribute(SESSION_ATTRIBUTE_SURVEY_ATTEMPT, survey);

            FormValidator validator = SurveyUserAttemptFormValidator.getInstance();
            Map<String, String[]> requestParameters = request.getParameterMap();
            Map<String, String> validationFeedback = validator.validateForm(requestParameters);

            if (validationFeedback.isEmpty()) {
                User user = (User) session.getAttribute(SESSION_ATTRIBUTE_USER);

                SurveyService surveyService = SurveyServiceImpl.getInstance();
                try {
                    SurveyUserAttempt surveyAttempt = new SurveyUserAttempt();
                    surveyAttempt.setSurvey(survey);
                    if (user != null) {
                        surveyAttempt.setUser(user);
                    }
                    surveyAttempt.setFinishedDate(LocalDateTime.now());
                    surveyService.updateParticipantSurveyResult(surveyAttempt);

                    page = SURVEYS;
                    pageChangeType = REDIRECT;
                } catch (ServiceException e) {
                    throw new CommandException(e);
                }

                session.removeAttribute(SESSION_ATTRIBUTE_SURVEY_ATTEMPT);
            } else {
                request.setAttribute(REQUEST_ATTRIBUTE_FORM_INVALID, validationFeedback);
            }
        } else {
            page = SURVEYS;
            pageChangeType = REDIRECT;
        }

        return new Router(page, pageChangeType);
    }
}