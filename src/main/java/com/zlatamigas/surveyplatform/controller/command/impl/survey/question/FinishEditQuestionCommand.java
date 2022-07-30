package com.zlatamigas.surveyplatform.controller.command.impl.survey.question;

import com.zlatamigas.surveyplatform.controller.command.Command;
import com.zlatamigas.surveyplatform.controller.command.CommandType;
import com.zlatamigas.surveyplatform.controller.navigation.Router;
import com.zlatamigas.surveyplatform.exception.CommandException;
import com.zlatamigas.surveyplatform.model.entity.Survey;
import com.zlatamigas.surveyplatform.model.entity.SurveyQuestion;
import com.zlatamigas.surveyplatform.model.entity.SurveyQuestionAnswer;
import com.zlatamigas.surveyplatform.util.validator.FormValidator;
import com.zlatamigas.surveyplatform.util.validator.impl.QuestionFormValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder.*;
import static com.zlatamigas.surveyplatform.controller.navigation.PageNavigation.*;
import static com.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType;
import static com.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;
import static com.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.REDIRECT;

public class FinishEditQuestionCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();
        String page = String.format(URL_REDIRECT_BASE_PATTERN, CommandType.EDIT_SURVEY.name());
        PageChangeType pageChangeType = FORWARD;

        Survey survey = (Survey) session.getAttribute(SESSION_ATTRIBUTE_EDITED_SURVEY);
        SurveyQuestion question = (SurveyQuestion) session.getAttribute(SESSION_ATTRIBUTE_EDITED_QUESTION);
        if (survey != null
                && question != null
                && request.getParameter(PARAMETER_QUESTION_FORMULATION) != null) {

            question.setFormulation(request.getParameter(PARAMETER_QUESTION_FORMULATION));
            String[] multipleSelect = request.getParameterValues(PARAMETER_QUESTION_SELECT_MULTIPLE);
            question.setSelectMultiple(multipleSelect != null && multipleSelect.length == 1);
            String answerLastPosStr = request.getParameter(PARAMETER_LAST_ANSWER_POSITION);
            if (answerLastPosStr != null) {
                int answerLastPos;
                try {
                    answerLastPos = Integer.parseInt(answerLastPosStr);
                } catch (NumberFormatException e) {
                    answerLastPos = 0;
                }
                List<SurveyQuestionAnswer> answers = new LinkedList<>();
                String answer;
                for (int i = 0; i <= answerLastPos; i++) {
                    answer = request.getParameter(PARAMETER_ANSWER_TEXT + i);
                    if (answer != null && !answer.isBlank()) {
                        answers.add(new SurveyQuestionAnswer.SurveyQuestionAnswerBuilder()
                                .setAnswer(answer)
                                .getSurveyQuestionAnswer());
                    }
                }
                question.setAnswers(answers);
            }
            session.setAttribute(SESSION_ATTRIBUTE_EDITED_QUESTION, question);

            FormValidator validator = QuestionFormValidator.getInstance();
            Map<String, String[]> requestParameters = request.getParameterMap();
            Map<String, String> validationFeedback = validator.validateForm(requestParameters);

            if (validationFeedback.isEmpty()) {
                if (question.getQuestionId() != 0) {
                    List<SurveyQuestion> questions = survey.getQuestions();
                    int i = 0;
                    while (i < questions.size()) {
                        if (questions.get(i).getQuestionId() == question.getQuestionId()) {
                            questions.set(i, question);
                            break;
                        }
                        i++;
                    }
                } else {
                    int minId = survey.getQuestions().stream()
                            .map(SurveyQuestion::getQuestionId)
                            .min(Integer::compare).orElse(0);
                    int id = minId < 0 ? --minId : -1;
                    question.setQuestionId(id);
                    survey.addQuestion(question);
                }

                session.removeAttribute(SESSION_ATTRIBUTE_EDITED_QUESTION);
                session.setAttribute(SESSION_ATTRIBUTE_EDITED_SURVEY, survey);
            } else {
                page = EDIT_QUESTION;
                request.setAttribute(REQUEST_ATTRIBUTE_FORM_INVALID, validationFeedback);
            }
        } else {
            page = HOME;
            pageChangeType = REDIRECT;
        }

        return new Router(page, pageChangeType);
    }
}
