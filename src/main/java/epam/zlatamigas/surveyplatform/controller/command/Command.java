package epam.zlatamigas.surveyplatform.controller.command;

import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import javax.servlet.http.HttpServletRequest;

/**
 * Command interface.
 */
public interface Command {

    /**
     * Executes a given command.
     *
     * @param request HttpServletRequest object.
     * @return Router object that contains the result page and routing type.
     */
    Router execute(HttpServletRequest request) throws CommandException;
}
