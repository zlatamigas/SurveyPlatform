package epam.zlatamigas.surveyplatform.controller.filter;


import epam.zlatamigas.surveyplatform.controller.command.CommandType;
import epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation;
import epam.zlatamigas.surveyplatform.model.entity.User;
import epam.zlatamigas.surveyplatform.model.entity.UserRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Map;

import static epam.zlatamigas.surveyplatform.controller.command.CommandType.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.ATTRIBUTE_USER;
import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.PARAMETER_COMMAND;
import static epam.zlatamigas.surveyplatform.model.entity.UserRole.*;

//@WebFilter(
//        filterName = "CheckUserRoleFilter",
//        dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST},
//        urlPatterns = {"/controller", "/pages/controller"})
public class CheckUserRoleFilter implements Filter {

    private static final Logger logger = LogManager.getLogger();

    private Map<UserRole, EnumSet<CommandType>> userCommands ;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        userCommands = Map.of(
                ADMIN, EnumSet.of(
                        DEFAULT,
                        HOME,
                        CHANGE_LOCALISATION,
                        LOGOUT,
                        LIST_USERS,
                        LIST_SURVEYS,
                        LIST_USER_CREATED_SURVEYS,
                        START_EDIT_SURVEY,
                        FINISH_EDIT_SURVEY,
                        START_EDIT_QUESTION,
                        STOP_SURVEY
                ),
                USER, EnumSet.of(
                        DEFAULT,
                        HOME,
                        CHANGE_LOCALISATION,
                        LOGOUT,
                        LIST_SURVEYS,
                        LIST_USER_CREATED_SURVEYS,
                        START_EDIT_SURVEY,
                        FINISH_EDIT_SURVEY,
                        START_EDIT_QUESTION,
                        STOP_SURVEY
                ),
                GUEST, EnumSet.of(
                        DEFAULT,
                        HOME,
                        CHANGE_LOCALISATION,
                        LOGIN,
                        START_AUTHENTICATION,
                        LIST_SURVEYS
                )
        );
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String commandStr = request.getParameter(PARAMETER_COMMAND);

        User user = (User) session.getAttribute(ATTRIBUTE_USER);
        UserRole userRole = user != null ? user.getRole() : GUEST;

        EnumSet<CommandType> allowedCommands = userCommands.get(userRole);
        CommandType command = CommandType.defineCommandType(commandStr);

//        String previousPage = (String) session.getAttribute(ATTRIBUTE_CURRENT_PAGE);
//        session.setAttribute(ATTRIBUTE_PREVIOUS_PAGE, previousPage);

        if (!allowedCommands.contains(command)) {
//            session.setAttribute(ATTRIBUTE_CURRENT_PAGE, PageNavigation.DEFAULT);
            response.sendRedirect(request.getContextPath() + PageNavigation.DEFAULT);
        } else {
//        session.setAttribute(ATTRIBUTE_CURRENT_PAGE, request.getServletPath());
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
    }
}
