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

import static epam.zlatamigas.surveyplatform.controller.command.CommandType.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.*;

@WebFilter(
        filterName = "SurveyEditAttributeCleanerFilter",
        dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST},
        urlPatterns = {"/controller"})
public class SurveyEditAttributeCleanerFilter implements Filter {

    private static final Logger logger = LogManager.getLogger();

    private EnumSet<CommandType> editSurveyCommands;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        editSurveyCommands = EnumSet.of(
                START_EDIT_SURVEY,
                EDIT_SURVEY,
                FINISH_EDIT_SURVEY,
                START_EDIT_QUESTION,
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

            logger.info("Clean session attributes: edited survey, themes list");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
