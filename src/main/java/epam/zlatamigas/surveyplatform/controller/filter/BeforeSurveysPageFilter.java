package epam.zlatamigas.surveyplatform.controller.filter;


import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.entity.Survey;
import epam.zlatamigas.surveyplatform.model.entity.User;
import epam.zlatamigas.surveyplatform.model.entity.UserRole;
import epam.zlatamigas.surveyplatform.service.SurveyService;
import epam.zlatamigas.surveyplatform.service.impl.SurveyServiceImpl;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.ATTRIBUTE_SURVEYS;
import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.ATTRIBUTE_USER;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.SURVEYS;

@WebFilter(filterName = "BeforeSurveysPageFilter", dispatcherTypes = {DispatcherType.FORWARD}, urlPatterns = "/pages/surveys.jsp")
public class BeforeSurveysPageFilter implements Filter {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession(false);

        try {
            SurveyService productService = SurveyServiceImpl.getInstance();
            List<Survey> products =  productService.findParticipantSurveysCommonInfo();
            session.setAttribute(ATTRIBUTE_SURVEYS, products);
        } catch (ServiceException e) {
           logger.error(e);
        }

        filterChain.doFilter(servletRequest, servletResponse);


//        User user = (User) session.getAttribute(ATTRIBUTE_USER);
//
//        if( user!= null && user.getRole() == UserRole.ADMIN){
//            HttpServletResponse response = (HttpServletResponse) servletResponse;
//            response.sendRedirect(request.getContextPath() + SURVEYS);
//        } else {
//            filterChain.doFilter(servletRequest, servletResponse);
//        }
    }

    @Override
    public void destroy() {
    }
}
