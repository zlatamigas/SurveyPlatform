package epam.zlatamigas.surveyplatform.controller.command.impl.to;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.DataHolder;
import epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.entity.Survey;
import epam.zlatamigas.surveyplatform.model.entity.Theme;
import epam.zlatamigas.surveyplatform.service.SurveyService;
import epam.zlatamigas.surveyplatform.service.ThemeService;
import epam.zlatamigas.surveyplatform.service.impl.SurveyServiceImpl;
import epam.zlatamigas.surveyplatform.service.impl.ThemeServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;

public class ToSurveysCommand implements Command {

    private static final String DEFAULT_ORDER = "ASC";
    private static final String DEFAULT_SEARCH_WORDS = "";
    private static final int DEFAULT_THEMES_ALL = 0;

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String page = PageNavigation.SURVEYS;

        request.setAttribute(PARAMETER_SEARCH_WORDS, DEFAULT_SEARCH_WORDS);
        request.setAttribute(PARAMETER_FILTER_THEME_ID, DEFAULT_THEMES_ALL);
        request.setAttribute(PARAMETER_ORDER_TYPE, DEFAULT_ORDER);

        SurveyService service = SurveyServiceImpl.getInstance();
        try {
            List<Survey> surveys =
                    service.findParticipantSurveysCommonInfoSearch(DEFAULT_THEMES_ALL, DEFAULT_SEARCH_WORDS, DEFAULT_ORDER);
            session.setAttribute(DataHolder.ATTRIBUTE_SURVEYS, surveys);
            session.setAttribute(ATTRIBUTE_SURVEYS_PAGE, surveys.subList(0, Math.min(SURVEYS_PER_PAGE, surveys.size())));
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        ThemeService themeService = ThemeServiceImpl.getInstance();
        try {
            List<Theme> themes = themeService.findAllConfirmed();
            session.setAttribute(ATTRIBUTE_THEMES, themes);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return new Router(page, FORWARD);
    }
}
