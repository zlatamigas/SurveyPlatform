package epam.zlatamigas.surveyplatform.controller.command.impl.samepage;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.model.entity.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.ATTRIBUTE_CURRENT_PAGE;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.EDIT_SURVEY;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;

public class RemoveQuestionCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();
        String page = EDIT_SURVEY;

        Survey survey = (Survey) session.getAttribute(ATTRIBUTE_EDITED_SURVEY);
        survey.setName(request.getParameter(PARAMETER_SURVEY_NAME));
        survey.setTheme(new Theme.ThemeBuilder().setThemeId(Integer.parseInt(request.getParameter(PARAMETER_SURVEY_THEME_ID))).getTheme());
        survey.setDescription(request.getParameter(PARAMETER_SURVEY_DESCRIPTION));
        survey.setName(request.getParameter(PARAMETER_SURVEY_NAME));

        int removeQuestionId = Integer.parseInt(request.getParameter(PARAMETER_QUESTION_ID));
        List<SurveyQuestion> surveyQuestions = survey.getQuestions().stream().filter(q -> q.getQuestionId() != removeQuestionId).collect(Collectors.toList());
        survey.setQuestions(surveyQuestions);

        session.setAttribute(ATTRIBUTE_EDITED_SURVEY, survey);
        session.setAttribute(ATTRIBUTE_CURRENT_PAGE, page);

        return new Router(page, FORWARD);
    }
}
