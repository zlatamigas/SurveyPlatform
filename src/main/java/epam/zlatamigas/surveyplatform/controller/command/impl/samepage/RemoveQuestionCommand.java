package epam.zlatamigas.surveyplatform.controller.command.impl.samepage;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.model.entity.Survey;
import epam.zlatamigas.surveyplatform.model.entity.SurveyQuestion;
import epam.zlatamigas.surveyplatform.model.entity.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.EDIT_SURVEY;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.HOME;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.REDIRECT;

public class RemoveQuestionCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();
        String page = EDIT_SURVEY;
        PageChangeType pageChangeType = FORWARD;

        Survey survey = (Survey) session.getAttribute(SESSION_ATTRIBUTE_EDITED_SURVEY);
        if (survey != null) {
            if (request.getParameter(PARAMETER_SURVEY_NAME) != null) {
                survey.setName(request.getParameter(PARAMETER_SURVEY_NAME));
            }
            if (request.getParameter(PARAMETER_SURVEY_DESCRIPTION) != null) {
                survey.setDescription(request.getParameter(PARAMETER_SURVEY_DESCRIPTION));
            }
            String themeIdStr = request.getParameter(PARAMETER_SURVEY_THEME_ID);
            if (themeIdStr != null) {
                try {
                    int themeId = Integer.parseInt(themeIdStr);
                    survey.setTheme(new Theme.ThemeBuilder()
                            .setThemeId(themeId)
                            .getTheme());
                } catch (NumberFormatException e) {
                    logger.warn("Passed invalid {} parameter", PARAMETER_SURVEY_THEME_ID);
                }
            }
            session.setAttribute(SESSION_ATTRIBUTE_EDITED_SURVEY, survey);

            String removeQuestionIdStr = request.getParameter(PARAMETER_QUESTION_ID);
            try {
                int removeQuestionId = Integer.parseInt(removeQuestionIdStr);

                List<SurveyQuestion> surveyQuestions = survey.getQuestions().stream()
                        .filter(q -> q.getQuestionId() != removeQuestionId)
                        .collect(Collectors.toList());
                survey.setQuestions(surveyQuestions);

                session.setAttribute(SESSION_ATTRIBUTE_EDITED_SURVEY, survey);
            } catch (NumberFormatException e) {
                logger.warn("Passed invalid {} parameter", PARAMETER_QUESTION_ID);
            }
        } else {
            page = HOME;
            pageChangeType = REDIRECT;
        }

        return new Router(page, pageChangeType);
    }
}
