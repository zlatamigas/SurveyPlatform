package epam.zlatamigas.surveyplatform.controller.command.impl.cancel;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.SESSION_ATTRIBUTE_CURRENT_PAGE;
import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.SESSION_ATTRIBUTE_SURVEY_ATTEMPT;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.SURVEYS;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.REDIRECT;

public class CancelSurveyAttemptCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();

        session.removeAttribute(SESSION_ATTRIBUTE_SURVEY_ATTEMPT);

        return new Router(SURVEYS, REDIRECT);
    }
}
