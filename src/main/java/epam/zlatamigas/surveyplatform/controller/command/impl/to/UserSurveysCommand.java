package epam.zlatamigas.surveyplatform.controller.command.impl.to;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.DataHolder;
import epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.entity.Survey;
import epam.zlatamigas.surveyplatform.model.entity.Theme;
import epam.zlatamigas.surveyplatform.model.entity.User;
import epam.zlatamigas.surveyplatform.service.SurveyService;
import epam.zlatamigas.surveyplatform.service.ThemeService;
import epam.zlatamigas.surveyplatform.service.impl.SurveyServiceImpl;
import epam.zlatamigas.surveyplatform.service.impl.ThemeServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.URL_CONTROLLER_WITH_PARAMETERS_PATTERN;
import static epam.zlatamigas.surveyplatform.util.search.SearchParameter.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;

public class UserSurveysCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();
        String page = USER_SURVEYS;

        String searchWordsStr = request.getParameter(REQUEST_ATTRIBUTE_PARAMETER_SEARCH_WORDS);
        if(searchWordsStr == null){
            searchWordsStr = DEFAULT_SEARCH_WORDS;
        }

        int filterThemeId;
        try {
           filterThemeId = Integer.parseInt(request.getParameter(REQUEST_ATTRIBUTE_PARAMETER_FILTER_THEME_ID));
        } catch (NumberFormatException e){
            filterThemeId = DEFAULT_FILTER_ID_ALL;
        }
        String surveyStatusName = request.getParameter(REQUEST_ATTRIBUTE_PARAMETER_FILTER_SURVEY_STATUS);
        if(surveyStatusName == null){
            surveyStatusName = DEFAULT_FILTER_STR_ALL;
        }
        String orderTypeName = request.getParameter(REQUEST_ATTRIBUTE_PARAMETER_ORDER_TYPE);
        if(orderTypeName == null){
            orderTypeName = DEFAULT_ORDER;
        }

        User user = (User) session.getAttribute(SESSION_ATTRIBUTE_USER);

        request.setAttribute(REQUEST_ATTRIBUTE_PARAMETER_SEARCH_WORDS, searchWordsStr);
        request.setAttribute(REQUEST_ATTRIBUTE_PARAMETER_FILTER_THEME_ID, filterThemeId);
        request.setAttribute(REQUEST_ATTRIBUTE_PARAMETER_FILTER_SURVEY_STATUS, surveyStatusName);
        request.setAttribute(REQUEST_ATTRIBUTE_PARAMETER_ORDER_TYPE, orderTypeName);

        SurveyService service = SurveyServiceImpl.getInstance();
        try {
            List<Survey> surveys = service.findCreatorSurveysCommonInfoSearch(filterThemeId, searchWordsStr, orderTypeName, surveyStatusName, user.getUserId());
            request.setAttribute(DataHolder.REQUEST_ATTRIBUTE_USER_SURVEYS, surveys);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        ThemeService themeService = ThemeServiceImpl.getInstance();
        try {
            List<Theme> themes = themeService.findAllConfirmed();
            request.setAttribute(REQUEST_ATTRIBUTE_AVAILABLE_THEMES_LIST, themes);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        session.setAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE,
                String.format(URL_CONTROLLER_WITH_PARAMETERS_PATTERN, request.getQueryString()));

        return new Router(page, FORWARD);
    }
}