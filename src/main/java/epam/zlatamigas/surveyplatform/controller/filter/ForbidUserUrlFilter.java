package epam.zlatamigas.surveyplatform.controller.filter;

import epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.PARAMETER_COMMAND;

//@WebFilter(filterName = "ForbidUserUrlFilter",
//        dispatcherTypes = {DispatcherType.REQUEST},
//        urlPatterns = {"/pages/*"})
public class ForbidUserUrlFilter implements Filter {

    private static final Logger logger = LogManager.getLogger();

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String commandStr = request.getParameter(PARAMETER_COMMAND);
        if(commandStr == null){
            response.sendRedirect(request.getContextPath());
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
