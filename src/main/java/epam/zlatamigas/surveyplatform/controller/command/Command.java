package epam.zlatamigas.surveyplatform.controller.command;

import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import javax.servlet.http.HttpServletRequest;

public interface Command {
    Router execute(HttpServletRequest request) throws CommandException;
}
