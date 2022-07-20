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

import static epam.zlatamigas.surveyplatform.controller.command.CommandType.FINISH_SURVEY_ATTEMPT;
import static epam.zlatamigas.surveyplatform.controller.command.CommandType.START_SURVEY_ATTEMPT;
import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.PARAMETER_COMMAND;
import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.SESSION_ATTRIBUTE_SURVEY_ATTEMPT;

@WebFilter(
        filterName = "SurveyAttemptAttributeCleanerFilter",
        dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST},
        urlPatterns = {"/controller"})
public class SurveyAttemptAttributeCleanerFilter implements Filter {

    private static final Logger logger = LogManager.getLogger();

    private EnumSet<CommandType> attemptSurveyCommands;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        attemptSurveyCommands = EnumSet.of(
                START_SURVEY_ATTEMPT,
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

            logger.info("Clean session attribute: survey attempt");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
