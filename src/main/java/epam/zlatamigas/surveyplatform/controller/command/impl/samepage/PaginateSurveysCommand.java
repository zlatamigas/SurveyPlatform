package epam.zlatamigas.surveyplatform.controller.command.impl.samepage;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.model.entity.Survey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.SURVEYS;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;

public class PaginateSurveysCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String page = SURVEYS;

        List<Survey> surveys = (List<Survey>) session.getAttribute(ATTRIBUTE_SURVEYS);

        String requestedPageStr = request.getParameter(PARAMETER_PAGINATION_PAGE_OFFSET);
        int requestedPage = !(requestedPageStr == null || requestedPageStr.isBlank()) ? Integer.parseInt(requestedPageStr) : 0;

        int firstElementPos = requestedPage * SURVEYS_PER_PAGE;
        if(firstElementPos < surveys.size()){
            List<Survey> surveysPerPage =
                    surveys.subList(firstElementPos, firstElementPos +  Math.min(SURVEYS_PER_PAGE, surveys.size() - firstElementPos));
            session.setAttribute(ATTRIBUTE_SURVEYS_PAGE, surveysPerPage);
            session.setAttribute(ATTRIBUTE_PAGINATION_CURRENT_PAGE, requestedPage);
        }

        return new Router(page, FORWARD);
    }
}
