package com.zlatamigas.surveyplatform.controller.command.impl.survey.question;

import com.zlatamigas.surveyplatform.controller.command.Command;
import com.zlatamigas.surveyplatform.controller.navigation.Router;
import com.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType;
import com.zlatamigas.surveyplatform.exception.CommandException;
import com.zlatamigas.surveyplatform.model.entity.Survey;
import com.zlatamigas.surveyplatform.model.entity.SurveyQuestion;
import com.zlatamigas.surveyplatform.model.entity.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static com.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder.*;
import static com.zlatamigas.surveyplatform.controller.navigation.PageNavigation.*;
import static com.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;
import static com.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.REDIRECT;

public class StartEditQuestionCommand implements Command {

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

            String createNewStr = request.getParameter(PARAMETER_CREATE_NEW_QUESTION);
            if (Boolean.TRUE.toString().equalsIgnoreCase(createNewStr)
                    || Boolean.FALSE.toString().equalsIgnoreCase(createNewStr)) {

                boolean questionCreateFound = false;

                SurveyQuestion question = null;
                boolean createNew = Boolean.parseBoolean(createNewStr);
                if (!createNew) {
                    String questionIdStr = request.getParameter(PARAMETER_QUESTION_ID);
                    try {
                        int questionId = Integer.parseInt(questionIdStr);

                        Optional<SurveyQuestion> surveyQuestion = survey.getQuestions().stream()
                                .filter(q -> q.getQuestionId() == questionId)
                                .findFirst();
                        if (surveyQuestion.isPresent()) {
                            question = surveyQuestion.get().clone();
                            questionCreateFound = true;
                        }
                    } catch (NumberFormatException e) {
                        logger.warn("Passed invalid {} parameter", PARAMETER_QUESTION_ID);
                    }
                } else {
                    question = new SurveyQuestion();
                    questionCreateFound = true;
                }

                if (questionCreateFound) {
                    page = EDIT_QUESTION;
                    pageChangeType = FORWARD;

                    session.setAttribute(SESSION_ATTRIBUTE_EDITED_QUESTION, question);
                }
            }
        } else {
            page = HOME;
            pageChangeType = REDIRECT;
        }

        return new Router(page, pageChangeType);
    }
}
