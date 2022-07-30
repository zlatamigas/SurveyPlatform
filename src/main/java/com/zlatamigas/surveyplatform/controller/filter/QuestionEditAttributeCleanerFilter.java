package com.zlatamigas.surveyplatform.controller.filter;


import com.zlatamigas.surveyplatform.controller.command.CommandType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.EnumSet;

import static com.zlatamigas.surveyplatform.controller.command.CommandType.EDIT_QUESTION;
import static com.zlatamigas.surveyplatform.controller.command.CommandType.FINISH_EDIT_QUESTION;
import static com.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder.PARAMETER_COMMAND;
import static com.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder.SESSION_ATTRIBUTE_EDITED_QUESTION;

/**
 * Filter for cleaning edit question session attributes.
 */
@WebFilter(
        filterName = "QuestionEditAttributeCleanerFilter",
        dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST},
        urlPatterns = {"/controller"})
public class QuestionEditAttributeCleanerFilter implements Filter {

    private EnumSet<CommandType> editQuestionCommands;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        editQuestionCommands = EnumSet.of(
                EDIT_QUESTION,
                FINISH_EDIT_QUESTION
        );
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();

        String commandStr = request.getParameter(PARAMETER_COMMAND);
        CommandType command = CommandType.defineCommandType(commandStr);

        if (!editQuestionCommands.contains(command)) {
            session.removeAttribute(SESSION_ATTRIBUTE_EDITED_QUESTION);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
