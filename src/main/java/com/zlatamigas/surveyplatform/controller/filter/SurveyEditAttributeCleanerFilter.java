package com.zlatamigas.surveyplatform.controller.filter;


import com.zlatamigas.surveyplatform.controller.command.CommandType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.EnumSet;

import static com.zlatamigas.surveyplatform.controller.command.CommandType.*;
import static com.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder.*;

/**
 * Filter for cleaning edit survey session attributes.
 */
@WebFilter(
        filterName = "SurveyEditAttributeCleanerFilter",
        dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST},
        urlPatterns = {"/controller"})
public class SurveyEditAttributeCleanerFilter implements Filter {

    private EnumSet<CommandType> editSurveyCommands;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        editSurveyCommands = EnumSet.of(
                START_EDIT_SURVEY,
                EDIT_SURVEY,
                FINISH_EDIT_SURVEY,
                EDIT_QUESTION,
                FINISH_EDIT_QUESTION,
                REMOVE_QUESTION
        );
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();

        String commandStr = request.getParameter(PARAMETER_COMMAND);
        CommandType command = CommandType.defineCommandType(commandStr);

        if (!editSurveyCommands.contains(command)) {
            session.removeAttribute(SESSION_ATTRIBUTE_EDITED_SURVEY);
            session.removeAttribute(SESSION_ATTRIBUTE_THEMES);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
