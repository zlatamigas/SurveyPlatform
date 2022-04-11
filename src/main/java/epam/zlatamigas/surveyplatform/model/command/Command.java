package epam.zlatamigas.surveyplatform.model.command;

import epam.zlatamigas.surveyplatform.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public interface Command {
    String execute(HttpServletRequest request) throws CommandException;
}
