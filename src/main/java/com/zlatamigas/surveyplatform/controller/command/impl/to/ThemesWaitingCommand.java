package com.zlatamigas.surveyplatform.controller.command.impl.to;

import com.zlatamigas.surveyplatform.controller.command.Command;
import com.zlatamigas.surveyplatform.controller.navigation.Router;
import com.zlatamigas.surveyplatform.exception.CommandException;
import com.zlatamigas.surveyplatform.exception.ServiceException;
import com.zlatamigas.surveyplatform.model.entity.Theme;
import com.zlatamigas.surveyplatform.service.ThemeService;
import com.zlatamigas.surveyplatform.service.impl.ThemeServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder.REQUEST_ATTRIBUTE_REQUESTED_THEMES;
import static com.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder.SESSION_ATTRIBUTE_CURRENT_PAGE;
import static com.zlatamigas.surveyplatform.controller.navigation.PageNavigation.THEMES_WAITING;
import static com.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;

public class ThemesWaitingCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();
        String page = THEMES_WAITING;

        ThemeService themeService = ThemeServiceImpl.getInstance();
        try {
            List<Theme> themes = themeService.findAllWaiting();
            request.setAttribute(REQUEST_ATTRIBUTE_REQUESTED_THEMES, themes);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        session.setAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE, page);

        return new Router(page, FORWARD);
    }
}
