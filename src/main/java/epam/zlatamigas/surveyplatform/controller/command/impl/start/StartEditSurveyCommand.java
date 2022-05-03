package epam.zlatamigas.surveyplatform.controller.command.impl.start;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.entity.Survey;
import epam.zlatamigas.surveyplatform.model.entity.Theme;
import epam.zlatamigas.surveyplatform.model.entity.User;
import epam.zlatamigas.surveyplatform.service.SurveyService;
import epam.zlatamigas.surveyplatform.service.ThemeService;
import epam.zlatamigas.surveyplatform.service.UserService;
import epam.zlatamigas.surveyplatform.service.impl.SurveyServiceImpl;
import epam.zlatamigas.surveyplatform.service.impl.ThemeServiceImpl;
import epam.zlatamigas.surveyplatform.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.AUTHORISATION;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.HOME;
import static epam.zlatamigas.surveyplatform.controller.navigation.RequestParameterHolder.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;
import static epam.zlatamigas.surveyplatform.controller.navigation.SessionAttributeHolder.*;

public class StartEditSurveyCommand implements Command {

    private static final String DEFAULT_SURVEY_NAME = "Title";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        boolean createNew = Boolean.parseBoolean(request.getParameter(CREATE_NEW));

        Survey survey = null;
        if(createNew){
            survey = new Survey();
        } else {
            int surveyId = Integer.parseInt(request.getParameter(SURVEY_ID));

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

        session.setAttribute(CURRENT_SURVEY, survey);
        session.setAttribute(CURRENT_PAGE, page);
        session.setAttribute(THEMES, themes);

        return new Router(page, FORWARD);
    }
}
