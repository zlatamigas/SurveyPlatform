package epam.zlatamigas.surveyplatform.controller.command.impl.samepage;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation;
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
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.THEMES_CONFIRMED;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;

public class DeleteThemeCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();
        String page = THEMES_CONFIRMED;

        ThemeService themeService = ThemeServiceImpl.getInstance();
        try {
            String themeIdParameter = request.getParameter(PARAMETER_THEME_ID);
            try {
                int themeId = Integer.parseInt(themeIdParameter);
                themeService.delete(themeId);
            } catch (NumberFormatException e) {
                // TODO: request parameter feedback
            }
            List<Theme> themes = themeService.findAllConfirmed();
            session.setAttribute(ATTRIBUTE_REQUESTED_THEMES, themes);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        session.setAttribute(ATTRIBUTE_CURRENT_PAGE, page);

        return new Router(page, FORWARD);
    }
}