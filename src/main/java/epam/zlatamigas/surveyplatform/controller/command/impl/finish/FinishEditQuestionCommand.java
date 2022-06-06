package epam.zlatamigas.surveyplatform.controller.command.impl.finish;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.DataHolder;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.model.entity.Survey;
import epam.zlatamigas.surveyplatform.model.entity.SurveyQuestion;
import epam.zlatamigas.surveyplatform.model.entity.SurveyQuestionAnswer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.EDIT_QUESTION;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.EDIT_SURVEY;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;

public class FinishEditQuestionCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();

        SurveyQuestion question = (SurveyQuestion) session.getAttribute(DataHolder.ATTRIBUTE_EDITED_QUESTION);
        String formulation = request.getParameter(DataHolder.PARAMETER_QUESTION_FORMULATION);

        String[] multipleSelect = request.getParameterValues(DataHolder.PARAMETER_QUESTION_SELECT_MULTIPLE);
        boolean selectMultiple = multipleSelect != null && multipleSelect.length == 1;

        int answerNumber = question.getAnswers().size();
        List<SurveyQuestionAnswer> answers = new LinkedList<>();
        String answer;
        for(int i = 0; i < answerNumber; i++){
            answer = request.getParameter(DataHolder.PARAMETER_ANSWER_TEXT + i);
            answers.add(new SurveyQuestionAnswer(answer));
        }

        question.setFormulation(formulation);
        question.setSelectMultiple(selectMultiple);
        question.setAnswers(answers);

        Survey survey = (Survey) session.getAttribute(ATTRIBUTE_EDITED_SURVEY);
        if(question.getQuestionId() != 0){
            List<SurveyQuestion> questions = new ArrayList<>(survey.getQuestions());
            int i = 0;
            while (i < questions.size()){
                if(questions.get(i).getQuestionId() == question.getQuestionId()){
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

        String page = EDIT_SURVEY;

        session.removeAttribute(ATTRIBUTE_EDITED_QUESTION);
        session.setAttribute(EDIT_SURVEY, survey);
        session.setAttribute(ATTRIBUTE_CURRENT_PAGE, page);

        return new Router(page, FORWARD);
    }
}
