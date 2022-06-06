package epam.zlatamigas.surveyplatform.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebFilter(filterName = "CharacterEncodingFilter", urlPatterns = "/*")
public class CharacterEncodingFilter implements Filter {

    private static final String CHARACTER_ENCODING = "UTF-8";

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        request.setCharacterEncoding(CHARACTER_ENCODING);
        response.setCharacterEncoding(CHARACTER_ENCODING);
        chain.doFilter(request, response);
    }
}
