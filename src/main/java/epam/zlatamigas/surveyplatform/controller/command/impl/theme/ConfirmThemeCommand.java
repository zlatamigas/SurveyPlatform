package epam.zlatamigas.surveyplatform.controller.command.impl.theme;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.service.ThemeService;
import epam.zlatamigas.surveyplatform.service.impl.ThemeServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static epam.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder.PARAMETER_THEME_ID;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.THEMES_WAITING;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.REDIRECT;

public class ConfirmThemeCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        ThemeService themeService = ThemeServiceImpl.getInstance();
        String themeIdStr = request.getParameter(PARAMETER_THEME_ID);
        if (themeIdStr != null) {
            try {
                int themeId = Integer.parseInt(themeIdStr);
                themeService.confirmTheme(themeId);
            } catch (NumberFormatException e) {
                logger.warn("Passed invalid {} parameter", PARAMETER_THEME_ID);
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        }

        return new Router(THEMES_WAITING, REDIRECT);
    }
}
