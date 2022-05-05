package epam.zlatamigas.surveyplatform.controller.command.impl.start;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.entity.Survey;
import epam.zlatamigas.surveyplatform.model.entity.Theme;
import epam.zlatamigas.surveyplatform.service.SurveyService;
import epam.zlatamigas.surveyplatform.service.ThemeService;
import epam.zlatamigas.surveyplatform.service.impl.SurveyServiceImpl;
import epam.zlatamigas.surveyplatform.service.impl.ThemeServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import static epam.zlatamigas.surveyplatform.controller.navigation.PageDataHolder.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;

public class StartEditSurveyCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        boolean createNew = Boolean.parseBoolean(request.getParameter(PARAMETER_CREATE_NEW));

        Survey survey = null;
        if(createNew){
            survey = new Survey();
        } else {
            int surveyId = Integer.parseInt(request.getParameter(PARAMETER_SURVEY_ID));

            SurveyService surveyService = SurveyServiceImpl.getInstance();
            try {
               survey  = surveyService.findById(surveyId);
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        }

        ThemeService themeService = ThemeServiceImpl.getInstance();
        List<Theme> themes;
        try {
            themes = themeService.findAllConfirmed();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        HttpSession session = request.getSession();
        String page = EDIT_SURVEY;

        session.setAttribute(ATTRIBUTE_CURRENT_SURVEY, survey);
        session.setAttribute(ATTRIBUTE_CURRENT_PAGE, page);
        session.setAttribute(ATTRIBUTE_THEMES, themes);

        return new Router(page, FORWARD);
    }
}
