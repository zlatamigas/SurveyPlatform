package epam.zlatamigas.surveyplatform.controller.command.impl.finish;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.DataHolder;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.entity.Survey;
import epam.zlatamigas.surveyplatform.model.entity.SurveyStatus;
import epam.zlatamigas.surveyplatform.model.entity.Theme;
import epam.zlatamigas.surveyplatform.model.entity.User;
import epam.zlatamigas.surveyplatform.service.SurveyService;
import epam.zlatamigas.surveyplatform.service.impl.SurveyServiceImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.USER_SURVEYS;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;

public class FinishEditSurveyCommand implements Command {

    private static final String DEFAULT_SURVEY_NAME = "Title";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();
        String page = USER_SURVEYS;

        Survey survey = (Survey) session.getAttribute(ATTRIBUTE_EDITED_SURVEY);
        survey.setName(request.getParameter(PARAMETER_SURVEY_NAME));
        survey.setTheme(new Theme(Integer.parseInt(request.getParameter(PARAMETER_SURVEY_THEME_ID))));
        survey.setDescription(request.getParameter(PARAMETER_SURVEY_DESCRIPTION));
        survey.setName(request.getParameter(PARAMETER_SURVEY_NAME));

        User creator = (User)session.getAttribute(ATTRIBUTE_USER);
        survey.setCreator(creator);
        survey.setStatus(SurveyStatus.NOT_STARTED);

        SurveyService surveyService = SurveyServiceImpl.getInstance();
        try{
            if(survey.getSurveyId() == 0){
                surveyService.insert(survey);
            } else {
                surveyService.update(survey);
            }

            List<Survey> surveys = surveyService.findCreatorSurveysCommonInfo(creator.getUserId());
            session.setAttribute(ATTRIBUTE_USER_SURVEYS, surveys);

        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        
        session.removeAttribute(ATTRIBUTE_EDITED_SURVEY);
        session.removeAttribute(ATTRIBUTE_THEMES);
        session.setAttribute(ATTRIBUTE_CURRENT_PAGE, page);


        return new Router(page, FORWARD);
    }
}
