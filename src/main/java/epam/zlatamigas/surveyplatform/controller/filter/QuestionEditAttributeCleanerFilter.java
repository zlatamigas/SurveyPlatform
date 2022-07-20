package epam.zlatamigas.surveyplatform.controller.filter;


import epam.zlatamigas.surveyplatform.controller.command.CommandType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.EnumSet;

import static epam.zlatamigas.surveyplatform.controller.command.CommandType.FINISH_EDIT_QUESTION;
import static epam.zlatamigas.surveyplatform.controller.command.CommandType.START_EDIT_QUESTION;
import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.PARAMETER_COMMAND;
import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.SESSION_ATTRIBUTE_EDITED_QUESTION;

@WebFilter(
        filterName = "QuestionEditAttributeCleanerFilter",
        dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST},
        urlPatterns = {"/controller"})
public class QuestionEditAttributeCleanerFilter implements Filter {

    private static final Logger logger = LogManager.getLogger();

    private EnumSet<CommandType> editQuestionCommands;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        editQuestionCommands = EnumSet.of(
                START_EDIT_QUESTION,
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

            logger.info("Clean session attribute: edited question");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
