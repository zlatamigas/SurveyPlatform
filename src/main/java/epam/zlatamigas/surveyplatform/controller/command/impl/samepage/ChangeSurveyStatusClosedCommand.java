package epam.zlatamigas.surveyplatform.controller.command.impl.samepage;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.command.CommandType;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.entity.SurveyStatus;
import epam.zlatamigas.surveyplatform.model.entity.Theme;
import epam.zlatamigas.surveyplatform.model.entity.User;
import epam.zlatamigas.surveyplatform.service.SurveyService;
import epam.zlatamigas.surveyplatform.service.ThemeService;
import epam.zlatamigas.surveyplatform.service.impl.SurveyServiceImpl;
import epam.zlatamigas.surveyplatform.service.impl.ThemeServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.REDIRECT;
import static epam.zlatamigas.surveyplatform.util.search.SearchParameter.*;
import static epam.zlatamigas.surveyplatform.util.search.SearchParameter.DEFAULT_FILTER_STR_ALL;

public class ChangeSurveyStatusClosedCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();

        int surveyId = Integer.parseInt(request.getParameter(PARAMETER_SURVEY_ID));

        SurveyService surveyService = SurveyServiceImpl.getInstance();
        try {
            surveyService.updateSurveyStatus(surveyId, SurveyStatus.CLOSED);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        String searchWordsStr = request.getParameter(REQUEST_ATTRIBUTE_PARAMETER_SEARCH_WORDS);
        searchWordsStr = (searchWordsStr != null)
                ? searchWordsStr.replaceAll(DEFAULT_SEARCH_WORDS_DELIMITER_PATTERN, DEFAULT_SEARCH_WORDS_DELIMITER_URL)
                : DEFAULT_SEARCH_WORDS;
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
        String page = String.format(URL_REDIRECT_BASE_PATTERN + URL_REDIRECT_PARAMETER_PATTERN.repeat(4),
                CommandType.SEARCH_SURVEY_CREATED_BY_USER.name(),
                REQUEST_ATTRIBUTE_PARAMETER_SEARCH_WORDS, searchWordsStr,
                REQUEST_ATTRIBUTE_PARAMETER_FILTER_THEME_ID, String.valueOf(filterThemeId),
                REQUEST_ATTRIBUTE_PARAMETER_FILTER_SURVEY_STATUS, surveyStatusName,
                REQUEST_ATTRIBUTE_PARAMETER_ORDER_TYPE, orderTypeName);
        session.setAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE, page);

        session.setAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE, page);

        return new Router(page, REDIRECT);
    }
}
