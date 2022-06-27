package epam.zlatamigas.surveyplatform.controller.command.impl.samepage;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.DataHolder;
import epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.entity.Survey;
import epam.zlatamigas.surveyplatform.service.SurveyService;
import epam.zlatamigas.surveyplatform.service.impl.SurveyServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;

public class SearchSurveysCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String page = PageNavigation.SURVEYS;

        String searchWordsStr = request.getParameter(PARAMETER_ATTRIBUTE_SEARCH_WORDS);
        int filterThemeId = Integer.parseInt(request.getParameter(PARAMETER_ATTRIBUTE_FILTER_THEME_ID));
        String orderTypeName = request.getParameter(PARAMETER_ATTRIBUTE_ORDER_TYPE);

        session.setAttribute(PARAMETER_ATTRIBUTE_SEARCH_WORDS, searchWordsStr);
        session.setAttribute(PARAMETER_ATTRIBUTE_FILTER_THEME_ID, filterThemeId);
        session.setAttribute(PARAMETER_ATTRIBUTE_ORDER_TYPE, orderTypeName);

        SurveyService service = SurveyServiceImpl.getInstance();
        try {
            List<Survey> surveys = service.findParticipantSurveysCommonInfoSearch(filterThemeId, searchWordsStr, orderTypeName);
            session.setAttribute(DataHolder.ATTRIBUTE_SURVEYS, surveys);
            session.setAttribute(ATTRIBUTE_SURVEYS_PAGE, surveys.subList(0, Math.min(SURVEYS_PER_PAGE, surveys.size())));
            session.setAttribute(ATTRIBUTE_PAGINATION_CURRENT_PAGE, 0);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return new Router(page, FORWARD);
    }
}
