package com.zlatamigas.surveyplatform.controller.filter;


import com.zlatamigas.surveyplatform.controller.command.CommandType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.EnumSet;

import static com.zlatamigas.surveyplatform.controller.command.CommandType.FINISH_SURVEY_ATTEMPT;
import static com.zlatamigas.surveyplatform.controller.command.CommandType.SURVEY_ATTEMPT;
import static com.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder.PARAMETER_COMMAND;
import static com.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder.SESSION_ATTRIBUTE_SURVEY_ATTEMPT;

/**
 * Filter for cleaning survey attempt session attributes.
 */
@WebFilter(
        filterName = "SurveyAttemptAttributeCleanerFilter",
        dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST},
        urlPatterns = {"/controller"})
public class SurveyAttemptAttributeCleanerFilter implements Filter {

    private EnumSet<CommandType> attemptSurveyCommands;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        attemptSurveyCommands = EnumSet.of(
                SURVEY_ATTEMPT,
                FINISH_SURVEY_ATTEMPT
        );
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();

        String commandStr = request.getParameter(PARAMETER_COMMAND);
        CommandType command = CommandType.defineCommandType(commandStr);

        if (!attemptSurveyCommands.contains(command)) {
            session.removeAttribute(SESSION_ATTRIBUTE_SURVEY_ATTEMPT);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
