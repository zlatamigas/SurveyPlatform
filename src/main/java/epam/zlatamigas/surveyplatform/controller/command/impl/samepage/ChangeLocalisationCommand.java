package epam.zlatamigas.surveyplatform.controller.command.impl.samepage;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.SESSION_ATTRIBUTE_CURRENT_PAGE;
import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.SESSION_ATTRIBUTE_PARAMETER_LOCALISATION;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.REDIRECT;

public class ChangeLocalisationCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();

        String localisation = request.getParameter(SESSION_ATTRIBUTE_PARAMETER_LOCALISATION);
        session.setAttribute(SESSION_ATTRIBUTE_PARAMETER_LOCALISATION, localisation);

        String page = (String) session.getAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE);

        return new Router(page, REDIRECT);
    }
}
