package epam.zlatamigas.surveyplatform.model.command.impl;

import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.model.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation;
import jakarta.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        request.getSession().invalidate();
        return PageNavigation.DEFAULT_PAGE;
    }
}
