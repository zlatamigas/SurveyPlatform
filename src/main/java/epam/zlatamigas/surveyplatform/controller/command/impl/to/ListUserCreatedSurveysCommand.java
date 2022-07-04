package epam.zlatamigas.surveyplatform.controller.command.impl.to;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.entity.Survey;
import epam.zlatamigas.surveyplatform.model.entity.User;
import epam.zlatamigas.surveyplatform.service.SurveyService;
import epam.zlatamigas.surveyplatform.service.impl.SurveyServiceImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

import static epam.zlatamigas.surveyplatform.controller.command.SearchParameter.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;
import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.ATTRIBUTE_CURRENT_PAGE;

public class ListUserCreatedSurveysCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        SurveyService service = SurveyServiceImpl.getInstance();
        HttpSession session = request.getSession();
        String page = PageNavigation.USER_SURVEYS;
        try {

            int creatorId = ((User)session.getAttribute(ATTRIBUTE_USER)).getUserId();
            List<Survey> surveys = service.findCreatorSurveysCommonInfoSearch(DEFAULT_FILTER_ALL, DEFAULT_SEARCH_WORDS, DEFAULT_ORDER, creatorId);

            request.setAttribute(REQUEST_ATTRIBUTE_USER_SURVEYS, surveys);

            session.setAttribute(ATTRIBUTE_CURRENT_PAGE, page);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }

        return new Router(page, FORWARD);
    }
}
