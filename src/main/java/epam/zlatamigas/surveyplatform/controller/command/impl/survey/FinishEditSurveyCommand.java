package epam.zlatamigas.surveyplatform.controller.command.impl.survey;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.command.CommandType;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.entity.Survey;
import epam.zlatamigas.surveyplatform.model.entity.SurveyStatus;
import epam.zlatamigas.surveyplatform.model.entity.Theme;
import epam.zlatamigas.surveyplatform.model.entity.User;
import epam.zlatamigas.surveyplatform.service.SurveyService;
import epam.zlatamigas.surveyplatform.service.impl.SurveyServiceImpl;
import epam.zlatamigas.surveyplatform.util.validator.FormValidator;
import epam.zlatamigas.surveyplatform.util.validator.impl.SurveyEditFormValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static epam.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.REDIRECT;

public class FinishEditSurveyCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();
        String page = String.format(URL_REDIRECT_BASE_PATTERN, CommandType.EDIT_SURVEY.name());
        PageChangeType pageChangeType = FORWARD;

        User creator = (User) session.getAttribute(SESSION_ATTRIBUTE_USER);

        Survey survey = (Survey) session.getAttribute(SESSION_ATTRIBUTE_EDITED_SURVEY);
        if (survey != null
                && request.getParameter(PARAMETER_SURVEY_NAME) != null
                && request.getParameter(PARAMETER_SURVEY_DESCRIPTION) != null) {

            survey.setName(request.getParameter(PARAMETER_SURVEY_NAME));
            survey.setDescription(request.getParameter(PARAMETER_SURVEY_DESCRIPTION));
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
            survey.setStatus(SurveyStatus.NOT_STARTED);
            survey.setCreator(creator);
            session.setAttribute(SESSION_ATTRIBUTE_EDITED_SURVEY, survey);

            FormValidator validator = SurveyEditFormValidator.getInstance();
            Map<String, String[]> requestParameters = request.getParameterMap();
            Map<String, String> validationFeedback = validator.validateForm(requestParameters);

            if (validationFeedback.isEmpty()) {
                SurveyService surveyService = SurveyServiceImpl.getInstance();
                try {
                    if (survey.getSurveyId() == 0) {
                        surveyService.insert(survey);
                    } else {
                        surveyService.update(survey, creator.getUserId());
                    }
                } catch (ServiceException e) {
                    throw new CommandException(e);
                }

                page = USER_SURVEYS;
                pageChangeType = REDIRECT;

                session.removeAttribute(SESSION_ATTRIBUTE_EDITED_SURVEY);
                session.removeAttribute(SESSION_ATTRIBUTE_THEMES);
            } else {
                request.setAttribute(REQUEST_ATTRIBUTE_FORM_INVALID, validationFeedback);
            }
        } else {
            page = HOME;
            pageChangeType = REDIRECT;
        }

        return new Router(page, pageChangeType);
    }
}
