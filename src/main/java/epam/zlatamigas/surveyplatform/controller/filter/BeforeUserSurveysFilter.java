package epam.zlatamigas.surveyplatform.controller.filter;

import epam.zlatamigas.surveyplatform.controller.command.CommandType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.*;
import static epam.zlatamigas.surveyplatform.util.search.SearchParameter.*;

@WebFilter(filterName = "BeforeUserSurveysFilter",
        urlPatterns = URL_REDIRECT_USER_SURVEYS,
        dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST})
public class BeforeUserSurveysFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String searchWordsStr = request.getParameter(REQUEST_ATTRIBUTE_PARAMETER_SEARCH_WORDS);
        if (searchWordsStr != null) {
            searchWordsStr = URLEncoder.encode(searchWordsStr, StandardCharsets.UTF_8.toString());
        } else {
            searchWordsStr = DEFAULT_SEARCH_WORDS;
        }
        int filterThemeId;
        try {
            filterThemeId = Integer.parseInt(request.getParameter(REQUEST_ATTRIBUTE_PARAMETER_FILTER_THEME_ID));
        } catch (NumberFormatException e) {
            filterThemeId = DEFAULT_FILTER_ID_ALL;
        }
        String surveyStatusName = request.getParameter(REQUEST_ATTRIBUTE_PARAMETER_FILTER_SURVEY_STATUS);
        if (surveyStatusName == null) {
            surveyStatusName = DEFAULT_FILTER_STR_ALL;
        }
        String orderTypeName = request.getParameter(REQUEST_ATTRIBUTE_PARAMETER_ORDER_TYPE);
        if (orderTypeName == null) {
            orderTypeName = DEFAULT_ORDER;
        }
        String page = String.format(URL_REDIRECT_BASE_PATTERN + URL_REDIRECT_PARAMETER_PATTERN.repeat(4),
                CommandType.USER_SURVEYS.name(),
                REQUEST_ATTRIBUTE_PARAMETER_SEARCH_WORDS, searchWordsStr,
                REQUEST_ATTRIBUTE_PARAMETER_FILTER_THEME_ID, String.valueOf(filterThemeId),
                REQUEST_ATTRIBUTE_PARAMETER_FILTER_SURVEY_STATUS, surveyStatusName,
                REQUEST_ATTRIBUTE_PARAMETER_ORDER_TYPE, orderTypeName);

        response.sendRedirect(request.getContextPath() + page);
    }
}
