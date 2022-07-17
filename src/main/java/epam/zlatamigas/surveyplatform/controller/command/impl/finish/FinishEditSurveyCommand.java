package epam.zlatamigas.surveyplatform.controller.command.impl.finish;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Map;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.EDIT_SURVEY;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.USER_SURVEYS;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.REDIRECT;

public class FinishEditSurveyCommand implements Command {

    private static final String DEFAULT_SURVEY_NAME = "Title";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();
        String page = EDIT_SURVEY;
        Router.PageChangeType changeType = FORWARD;

        FormValidator validator = SurveyEditFormValidator.getInstance();
        Map<String, String[]> requestParameters = request.getParameterMap();
        Map<String, String> validationFeedback = validator.validateForm(requestParameters);

        Survey survey = (Survey) session.getAttribute(SESSION_ATTRIBUTE_EDITED_SURVEY);
        survey.setName(request.getParameter(PARAMETER_SURVEY_NAME));
        survey.setDescription(request.getParameter(PARAMETER_SURVEY_DESCRIPTION));
        survey.setTheme(new Theme.ThemeBuilder().setThemeId(Integer.parseInt(request.getParameter(PARAMETER_SURVEY_THEME_ID))).getTheme());
        survey.setStatus(SurveyStatus.NOT_STARTED);
        User creator = (User)session.getAttribute(SESSION_ATTRIBUTE_USER);
        survey.setCreator(creator);

        session.setAttribute(SESSION_ATTRIBUTE_EDITED_SURVEY, survey);

        if(validationFeedback.isEmpty()){

            SurveyService surveyService = SurveyServiceImpl.getInstance();
            try{
                if(survey.getSurveyId() == 0){
                    surveyService.insert(survey);
                } else {
                    surveyService.update(survey);
                }

            } catch (ServiceException e) {
                throw new CommandException(e);
            }

            page = USER_SURVEYS;
            changeType = REDIRECT;

            session.removeAttribute(SESSION_ATTRIBUTE_EDITED_SURVEY);
            session.removeAttribute(SESSION_ATTRIBUTE_THEMES);
            session.setAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE, page);
        } else {
            request.setAttribute(REQUEST_ATTRIBUTE_FORM_INVALID, validationFeedback);
        }

        return new Router(page, changeType);
    }
}
