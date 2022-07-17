package epam.zlatamigas.surveyplatform.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "PageBackFilter", urlPatterns = "/*")
public class PageBackFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        HttpServletResponse httpres = (HttpServletResponse) response;
        httpres.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        httpres.setHeader("Pragma", "no-cache");
        httpres.setDateHeader("Expires", 0);

        chain.doFilter(request, response);
    }
}
