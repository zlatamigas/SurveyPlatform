package epam.zlatamigas.surveyplatform.controller.command.impl.samepage;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.DataHolder;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.model.entity.SurveyQuestion;
import epam.zlatamigas.surveyplatform.model.entity.SurveyQuestionAnswer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.ATTRIBUTE_CURRENT_PAGE;
import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.ATTRIBUTE_EDITED_QUESTION;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.EDIT_QUESTION;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;

public class AddAnswerCommand implements Command {
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
        answers.add(new SurveyQuestionAnswer());

        question.setFormulation(formulation);
        question.setSelectMultiple(selectMultiple);
        question.setAnswers(answers);

        String page = EDIT_QUESTION;

        session.setAttribute(ATTRIBUTE_EDITED_QUESTION, question);
        session.setAttribute(ATTRIBUTE_CURRENT_PAGE, page);

        return new Router(page, FORWARD);
    }
}
