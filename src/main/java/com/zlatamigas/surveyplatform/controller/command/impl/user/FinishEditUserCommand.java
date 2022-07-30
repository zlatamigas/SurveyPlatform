package com.zlatamigas.surveyplatform.controller.command.impl.user;

import com.zlatamigas.surveyplatform.controller.command.Command;
import com.zlatamigas.surveyplatform.controller.navigation.Router;
import com.zlatamigas.surveyplatform.exception.CommandException;
import com.zlatamigas.surveyplatform.exception.ServiceException;
import com.zlatamigas.surveyplatform.model.entity.User;
import com.zlatamigas.surveyplatform.service.UserService;
import com.zlatamigas.surveyplatform.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder.*;
import static com.zlatamigas.surveyplatform.controller.navigation.PageNavigation.USERS;
import static com.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.REDIRECT;

public class FinishEditUserCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();

        User admin = (User) session.getAttribute(SESSION_ATTRIBUTE_USER);

        String userIdStr = request.getParameter(REQUEST_ATTRIBUTE_PARAMETER_USER_ID);

        UserService service = UserServiceImpl.getInstance();
        try {
            if (userIdStr != null) {
                int userId = Integer.parseInt(userIdStr);

                if (userId != admin.getUserId()) {
                    String roleName = request.getParameter(REQUEST_ATTRIBUTE_PARAMETER_USER_ROLE);
                    String statusName = request.getParameter(REQUEST_ATTRIBUTE_PARAMETER_USER_STATUS);
                    service.updateRoleStatus(userId, roleName, statusName);
                }
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return new Router(USERS, REDIRECT);
    }
}
