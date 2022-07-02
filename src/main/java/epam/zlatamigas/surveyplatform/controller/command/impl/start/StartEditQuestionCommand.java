package epam.zlatamigas.surveyplatform.controller.command.impl.start;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.model.entity.Survey;
import epam.zlatamigas.surveyplatform.model.entity.SurveyQuestion;
import epam.zlatamigas.surveyplatform.model.entity.Theme;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.EDIT_QUESTION;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;

public class StartEditQuestionCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();

        boolean createNew = Boolean.parseBoolean(request.getParameter(PARAMETER_CREATE_NEW_QUESTION));

        Survey survey = (Survey) session.getAttribute(ATTRIBUTE_EDITED_SURVEY);
        survey.setName(request.getParameter(PARAMETER_SURVEY_NAME));
        survey.setTheme(new Theme.ThemeBuilder().setThemeId(Integer.parseInt(request.getParameter(PARAMETER_SURVEY_THEME_ID))).getTheme());
        survey.setDescription(request.getParameter(PARAMETER_SURVEY_DESCRIPTION));
        survey.setName(request.getParameter(PARAMETER_SURVEY_NAME));

        SurveyQuestion question = null;
        if(createNew){
            question = new SurveyQuestion();
        } else {
            int questionId = Integer.parseInt(request.getParameter(PARAMETER_QUESTION_ID));

            Optional<SurveyQuestion> surveyQuestion = survey.getQuestions().stream().filter(q -> q.getQuestionId() == questionId).findFirst();
            question = surveyQuestion.isPresent() ? surveyQuestion.get().clone() : new SurveyQuestion();
        }

        String page = EDIT_QUESTION;

        session.setAttribute(ATTRIBUTE_EDITED_SURVEY, survey);
        session.setAttribute(ATTRIBUTE_EDITED_QUESTION, question);
        session.setAttribute(ATTRIBUTE_CURRENT_PAGE, page);

        return new Router(page, FORWARD);
    }
}
