package com.zlatamigas.surveyplatform.controller.command.impl.to;

import com.zlatamigas.surveyplatform.controller.command.Command;
import com.zlatamigas.surveyplatform.controller.navigation.Router;
import com.zlatamigas.surveyplatform.exception.CommandException;
import com.zlatamigas.surveyplatform.exception.ServiceException;
import com.zlatamigas.surveyplatform.model.entity.Theme;
import com.zlatamigas.surveyplatform.service.ThemeService;
import com.zlatamigas.surveyplatform.service.impl.ThemeServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder.*;
import static com.zlatamigas.surveyplatform.controller.navigation.PageNavigation.THEMES_CONFIRMED;
import static com.zlatamigas.surveyplatform.controller.navigation.PageNavigation.URL_CONTROLLER_WITH_PARAMETERS_PATTERN;
import static com.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;
import static com.zlatamigas.surveyplatform.util.search.SearchParameter.DEFAULT_ORDER;
import static com.zlatamigas.surveyplatform.util.search.SearchParameter.DEFAULT_SEARCH_WORDS;

public class ThemesConfirmedCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();

        String searchWordsStr = request.getParameter(REQUEST_ATTRIBUTE_PARAMETER_SEARCH_WORDS);
        if (searchWordsStr == null) {
            searchWordsStr = DEFAULT_SEARCH_WORDS;
        }
        String orderTypeName = request.getParameter(REQUEST_ATTRIBUTE_PARAMETER_ORDER_TYPE);
        if (orderTypeName == null) {
            orderTypeName = DEFAULT_ORDER;
        }

        request.setAttribute(REQUEST_ATTRIBUTE_PARAMETER_SEARCH_WORDS, searchWordsStr);
        request.setAttribute(REQUEST_ATTRIBUTE_PARAMETER_ORDER_TYPE, orderTypeName);

        ThemeService themeService = ThemeServiceImpl.getInstance();
        try {
            List<Theme> themes = themeService.findConfirmedSearch(searchWordsStr, orderTypeName);
            request.setAttribute(REQUEST_ATTRIBUTE_REQUESTED_THEMES, themes);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        session.setAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE,
                String.format(URL_CONTROLLER_WITH_PARAMETERS_PATTERN, request.getQueryString()));

        return new Router(THEMES_CONFIRMED, FORWARD);
    }
}
