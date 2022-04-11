package epam.zlatamigas.surveyplatform.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

// Защита от повторного входа без авторизации
@WebFilter(filterName = "PgeRedirectSecurityFilter",
urlPatterns = "/pages/*",
initParams = {@WebInitParam(name="INDEX_PATH", value = "/index.jsp")})
public class PageRedirectSecurityFilter implements Filter {

    private static final Logger logger = LogManager.getLogger();

    private String indexPath;

    public void init(FilterConfig config) throws ServletException {
    indexPath = config.getInitParameter("INDEX_PATH");
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        httpResponse.sendRedirect(httpRequest.getContextPath() + indexPath);
        logger.info("---------------> PageRedirectSecurityFilter");

        chain.doFilter(request, response);

    }
}
