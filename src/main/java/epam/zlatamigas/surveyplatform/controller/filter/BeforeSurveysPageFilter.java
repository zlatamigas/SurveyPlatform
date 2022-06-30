package epam.zlatamigas.surveyplatform.controller.filter;


import epam.zlatamigas.surveyplatform.controller.navigation.DataHolder;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.entity.Survey;
import epam.zlatamigas.surveyplatform.service.SurveyService;
import epam.zlatamigas.surveyplatform.service.impl.SurveyServiceImpl;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

import static epam.zlatamigas.surveyplatform.controller.command.SearchParameter.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.*;

//@WebFilter(filterName = "BeforeSurveysPageFilter", dispatcherTypes = {DispatcherType.FORWARD}, urlPatterns = "/view/page/surveys.jsp")
public class BeforeSurveysPageFilter implements Filter {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession(false);

        try {
            SurveyService service = SurveyServiceImpl.getInstance();
            List<Survey> surveys =
                    service.findParticipantSurveysCommonInfoSearch(DEFAULT_FILTER_ALL, DEFAULT_SEARCH_WORDS, DEFAULT_ORDER);
            session.setAttribute(DataHolder.ATTRIBUTE_SURVEYS, surveys);
            session.setAttribute(ATTRIBUTE_SURVEYS_PAGE, surveys.subList(0, Math.min(PAGINATION_ITEMS_PER_PAGE, surveys.size())));
            session.setAttribute(ATTRIBUTE_PAGINATION_CURRENT_PAGE, 0);
        } catch (ServiceException e) {
           logger.error(e);
        }

        filterChain.doFilter(servletRequest, servletResponse);

//
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
