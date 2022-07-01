package epam.zlatamigas.surveyplatform.controller.command.impl.finish;

import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.entity.User;
import epam.zlatamigas.surveyplatform.service.UserService;
import epam.zlatamigas.surveyplatform.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

import static epam.zlatamigas.surveyplatform.controller.command.SearchParameter.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.EDIT_USER;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.USERS;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;

public class FinishEditUserCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();
        String page = USERS;

        int userId = Integer.parseInt(request.getParameter(PARAMETER_USER_ID));
        String roleName = request.getParameter(PARAMETER_USER_ROLE);
        String statusName = request.getParameter(PARAMETER_USER_STATUS);

        UserService service = UserServiceImpl.getInstance();
        try {
            service.updateRoleStatus(userId, roleName, statusName);
            session.removeAttribute(ATTRIBUTE_EDITED_USER);

            List<User> users = service.findUsersBySearch(DEFAULT_FILTER_ALL, DEFAULT_FILTER_ALL, DEFAULT_SEARCH_WORDS, DEFAULT_ORDER);
            session.setAttribute(ATTRIBUTE_USERS, users);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        session.setAttribute(ATTRIBUTE_CURRENT_PAGE, page);

        return new Router(page, FORWARD);
    }
}
