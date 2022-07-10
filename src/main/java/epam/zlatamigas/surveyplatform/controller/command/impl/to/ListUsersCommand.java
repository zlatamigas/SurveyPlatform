package epam.zlatamigas.surveyplatform.controller.command.impl.to;

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
import java.util.stream.Collectors;

import static epam.zlatamigas.surveyplatform.util.search.SearchParameter.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.*;
import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.USERS;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;

public class ListUsersCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();
        String page = USERS;

        request.setAttribute(REQUEST_ATTRIBUTE_PARAMETER_SEARCH_WORDS, DEFAULT_SEARCH_WORDS);
        request.setAttribute(REQUEST_ATTRIBUTE_PARAMETER_FILTER_USER_ROLE, DEFAULT_FILTER_STR_ALL);
        request.setAttribute(REQUEST_ATTRIBUTE_PARAMETER_FILTER_USER_STATUS, DEFAULT_FILTER_STR_ALL);
        request.setAttribute(REQUEST_ATTRIBUTE_PARAMETER_ORDER_TYPE, DEFAULT_ORDER);

        User creator = (User) session.getAttribute(ATTRIBUTE_USER);

        UserService service = UserServiceImpl.getInstance();

        try{
            List<User> users = service.findUsersBySearch(DEFAULT_FILTER_STR_ALL, DEFAULT_FILTER_STR_ALL, DEFAULT_SEARCH_WORDS, DEFAULT_ORDER)
                    .stream().filter(user -> user.getUserId() != creator.getUserId()).collect(Collectors.toList());
            request.setAttribute(REQUEST_ATTRIBUTE_USERS, users);
        } catch (ServiceException e){
            throw new CommandException(e);
        }

        session.setAttribute(ATTRIBUTE_CURRENT_PAGE, page);

        return new Router(page, FORWARD);
    }
}
