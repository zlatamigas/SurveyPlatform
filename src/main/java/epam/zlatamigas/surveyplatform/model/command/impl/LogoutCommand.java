package epam.zlatamigas.surveyplatform.model.command.impl;

import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.model.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation;
import jakarta.servlet.http.HttpServletRequest;

import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.*;

public class LogoutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        request.getSession().invalidate();
        return new Router(DEFAULT, FORWARD);
    }
}
