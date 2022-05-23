package epam.zlatamigas.surveyplatform.controller.command.impl;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.DataHolder;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;
import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.ATTRIBUTE_CURRENT_PAGE;

public class ChangeLocalisationCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();

        String localisation = request.getParameter(DataHolder.PARAMETER_LOCALISATION);
        session.setAttribute(DataHolder.ATTRIBUTE_LOCALISATION, localisation);
        String page = (String) session.getAttribute(ATTRIBUTE_CURRENT_PAGE);

        return new Router(page, FORWARD);
    }
}
