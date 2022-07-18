package epam.zlatamigas.surveyplatform.controller.command.impl.samepage;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.entity.Theme;
import epam.zlatamigas.surveyplatform.service.ThemeService;
import epam.zlatamigas.surveyplatform.service.impl.ThemeServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.REDIRECT;
import static epam.zlatamigas.surveyplatform.util.search.SearchParameter.DEFAULT_ORDER;
import static epam.zlatamigas.surveyplatform.util.search.SearchParameter.DEFAULT_SEARCH_WORDS;

public class DeleteThemeCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        ThemeService themeService = ThemeServiceImpl.getInstance();
        try {
            String themeIdParameter = request.getParameter(PARAMETER_THEME_ID);
            int themeId = Integer.parseInt(themeIdParameter);

            themeService.delete(themeId);
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException(e);
        }

        return new Router(URL_REDIRECT_THEMES_CONFIRMED, FORWARD);
    }
}
