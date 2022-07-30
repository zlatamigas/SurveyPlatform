package com.zlatamigas.surveyplatform.controller.command.impl.to;

import com.zlatamigas.surveyplatform.controller.command.Command;
import com.zlatamigas.surveyplatform.controller.navigation.Router;
import com.zlatamigas.surveyplatform.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder.SESSION_ATTRIBUTE_CURRENT_PAGE;
import static com.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder.SESSION_ATTRIBUTE_PARAMETER_LOCALISATION;
import static com.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.REDIRECT;

public class ChangeLocalisationCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();

        String localisation = request.getParameter(SESSION_ATTRIBUTE_PARAMETER_LOCALISATION);
        session.setAttribute(SESSION_ATTRIBUTE_PARAMETER_LOCALISATION, localisation);

        String page = (String) session.getAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE);

        return new Router(page, REDIRECT);
    }
}
