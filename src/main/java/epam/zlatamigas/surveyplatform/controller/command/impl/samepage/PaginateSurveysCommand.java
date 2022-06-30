package epam.zlatamigas.surveyplatform.controller.command.impl.samepage;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.DataHolder;
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

import static epam.zlatamigas.surveyplatform.controller.command.SearchParameter.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.SURVEYS;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;

public class PaginateSurveysCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String page = SURVEYS;

        boolean requireLoadNewData = false;

        // Get requested parameters

        String searchWordsStrNew = request.getParameter(PARAMETER_ATTRIBUTE_SEARCH_WORDS);
        if(searchWordsStrNew == null){
            searchWordsStrNew = DEFAULT_SEARCH_WORDS;
        }
        int filterThemeIdNew;
        try {
            filterThemeIdNew = Integer.parseInt(request.getParameter(PARAMETER_ATTRIBUTE_FILTER_THEME_ID));
        } catch (NumberFormatException e){
            filterThemeIdNew = DEFAULT_FILTER_ALL;
        }
        String orderTypeNameNew = request.getParameter(PARAMETER_ATTRIBUTE_ORDER_TYPE);
        if(orderTypeNameNew == null){
            orderTypeNameNew = DEFAULT_ORDER;
        }

        // Get search attributes, stored in session

        String searchWordsStrOld = (String) session.getAttribute(PARAMETER_ATTRIBUTE_SEARCH_WORDS);
        if(searchWordsStrOld == null){
            searchWordsStrOld = DEFAULT_SEARCH_WORDS;
            requireLoadNewData = true;
        }
        Integer themeIdParameter = (Integer) session.getAttribute(PARAMETER_ATTRIBUTE_FILTER_THEME_ID);
        int filterThemeIdOld = DEFAULT_FILTER_ALL;
        if (themeIdParameter != null){
            filterThemeIdOld = themeIdParameter;
        } else {
            requireLoadNewData = true;
        }
        String orderTypeNameOld = (String) session.getAttribute(PARAMETER_ATTRIBUTE_ORDER_TYPE);
        if(orderTypeNameOld == null){
            orderTypeNameOld = DEFAULT_ORDER;
            requireLoadNewData = true;
        }

        // Compare search parameters and load new data if they are different

        List<Survey> surveys = (List<Survey>) session.getAttribute(ATTRIBUTE_SURVEYS);
        if(surveys == null){
            requireLoadNewData = true;
        }

        if(requireLoadNewData
            || !searchWordsStrOld.equals(searchWordsStrNew)
            || filterThemeIdOld != filterThemeIdNew
            || !orderTypeNameOld.equals(orderTypeNameNew)){

            SurveyService service = SurveyServiceImpl.getInstance();
            try {
                surveys = service.findParticipantSurveysCommonInfoSearch(filterThemeIdNew, searchWordsStrNew, orderTypeNameNew);
                session.setAttribute(DataHolder.ATTRIBUTE_SURVEYS, surveys);
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        }


        session.setAttribute(PARAMETER_ATTRIBUTE_SEARCH_WORDS, searchWordsStrNew);
        session.setAttribute(PARAMETER_ATTRIBUTE_FILTER_THEME_ID, filterThemeIdNew);
        session.setAttribute(PARAMETER_ATTRIBUTE_ORDER_TYPE, orderTypeNameNew);

        // Paginate using up to date survey list

        String requestedPageStr = request.getParameter(PARAMETER_PAGINATION_PAGE_OFFSET);
        int requestedPage = !(requestedPageStr == null || requestedPageStr.isBlank()) ? Integer.parseInt(requestedPageStr) : 0;

        int firstElementPos = requestedPage * PAGINATION_ITEMS_PER_PAGE;
        if(firstElementPos >= surveys.size()){
            firstElementPos = 0;
            requestedPage = 0;
        }

        List<Survey> surveysPerPage =
                    surveys.subList(firstElementPos, firstElementPos +  Math.min(PAGINATION_ITEMS_PER_PAGE, surveys.size() - firstElementPos));
        session.setAttribute(ATTRIBUTE_SURVEYS_PAGE, surveysPerPage);
        session.setAttribute(ATTRIBUTE_PAGINATION_CURRENT_PAGE, requestedPage);

        ThemeService themeService = ThemeServiceImpl.getInstance();
        try {
            List<Theme> themes = themeService.findAllConfirmed();
            session.setAttribute(ATTRIBUTE_THEMES, themes);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        session.setAttribute(ATTRIBUTE_CURRENT_PAGE, page);

        return new Router(page, FORWARD);
    }
}
