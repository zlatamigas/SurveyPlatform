package epam.zlatamigas.surveyplatform.controller.command.impl.user;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.entity.User;
import epam.zlatamigas.surveyplatform.service.UserService;
import epam.zlatamigas.surveyplatform.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static epam.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.USERS;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.REDIRECT;

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
