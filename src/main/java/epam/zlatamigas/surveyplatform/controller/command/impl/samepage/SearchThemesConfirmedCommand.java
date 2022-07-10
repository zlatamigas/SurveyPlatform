package epam.zlatamigas.surveyplatform.controller.command.impl.samepage;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.entity.Theme;
import epam.zlatamigas.surveyplatform.service.ThemeService;
import epam.zlatamigas.surveyplatform.service.impl.ThemeServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.REQUEST_ATTRIBUTE_PARAMETER_ORDER_TYPE;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.THEMES_CONFIRMED;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;
import static epam.zlatamigas.surveyplatform.util.search.SearchParameter.*;

public class SearchThemesConfirmedCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();
        String page = THEMES_CONFIRMED;

        String searchWordsStr = request.getParameter(REQUEST_ATTRIBUTE_PARAMETER_SEARCH_WORDS);
        if(searchWordsStr == null){
            searchWordsStr = DEFAULT_SEARCH_WORDS;
        }
        String orderTypeName = request.getParameter(REQUEST_ATTRIBUTE_PARAMETER_ORDER_TYPE);
        if(orderTypeName == null){
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

        session.setAttribute(ATTRIBUTE_CURRENT_PAGE, page);

        return new Router(page, FORWARD);
    }
}
