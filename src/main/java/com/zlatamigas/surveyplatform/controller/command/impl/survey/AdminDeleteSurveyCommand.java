package com.zlatamigas.surveyplatform.controller.command.impl.survey;

import com.zlatamigas.surveyplatform.controller.command.Command;
import com.zlatamigas.surveyplatform.controller.navigation.Router;
import com.zlatamigas.surveyplatform.exception.CommandException;
import com.zlatamigas.surveyplatform.exception.ServiceException;
import com.zlatamigas.surveyplatform.service.SurveyService;
import com.zlatamigas.surveyplatform.service.impl.SurveyServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder.*;
import static com.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.REDIRECT;

public class AdminDeleteSurveyCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();

        SurveyService surveyService = SurveyServiceImpl.getInstance();
        String surveyIdStr = request.getParameter(PARAMETER_SURVEY_ID);
        String userIdStr = request.getParameter(PARAMETER_USER_ID);
        if (surveyIdStr != null && userIdStr != null) {
            try {
                int surveyId = Integer.parseInt(surveyIdStr);
                int creatorId = Integer.parseInt(userIdStr);

                surveyService.delete(surveyId, creatorId);
            } catch (NumberFormatException e) {
                logger.warn("Passed invalid id parameter: {}", e.getMessage());
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        }

        return new Router((String) session.getAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE), REDIRECT);
    }
}
