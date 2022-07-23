package epam.zlatamigas.surveyplatform.controller.command.impl.survey;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.SESSION_ATTRIBUTE_EDITED_SURVEY;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.EDIT_SURVEY;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.HOME;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.REDIRECT;

public class EditSurveyCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();
        String page = EDIT_SURVEY;
        PageChangeType pageChangeType = FORWARD;

        if (session.getAttribute(SESSION_ATTRIBUTE_EDITED_SURVEY) == null) {
            page = HOME;
            pageChangeType = REDIRECT;
        }

        return new Router(page, pageChangeType);
    }
}
