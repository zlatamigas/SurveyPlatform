package epam.zlatamigas.surveyplatform.controller.command.impl.samepage;

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

public class SearchUsersCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();
        String page = USERS;

        String searchWordsStr = request.getParameter(REQUEST_ATTRIBUTE_PARAMETER_SEARCH_WORDS);
        if(searchWordsStr == null){
            searchWordsStr = DEFAULT_SEARCH_WORDS;
        }

        String userRoleName = request.getParameter(REQUEST_ATTRIBUTE_PARAMETER_FILTER_USER_ROLE);
        if(userRoleName == null){
            userRoleName = DEFAULT_FILTER_STR_ALL;
        }
        String userStatusName = request.getParameter(REQUEST_ATTRIBUTE_PARAMETER_FILTER_USER_STATUS);
        if(userStatusName == null){
            userStatusName = DEFAULT_FILTER_STR_ALL;
        }

        String orderTypeName = request.getParameter(REQUEST_ATTRIBUTE_PARAMETER_ORDER_TYPE);
        if(orderTypeName == null){
            orderTypeName = DEFAULT_ORDER;
        }

        request.setAttribute(REQUEST_ATTRIBUTE_PARAMETER_SEARCH_WORDS, searchWordsStr);
        request.setAttribute(REQUEST_ATTRIBUTE_PARAMETER_FILTER_USER_ROLE, userRoleName);
        request.setAttribute(REQUEST_ATTRIBUTE_PARAMETER_FILTER_USER_STATUS, userStatusName);
        request.setAttribute(REQUEST_ATTRIBUTE_PARAMETER_ORDER_TYPE, orderTypeName);


        User creator = (User) session.getAttribute(ATTRIBUTE_USER);

        UserService service = UserServiceImpl.getInstance();

        try{
            List<User> users = service.findUsersBySearch(userRoleName, userStatusName, searchWordsStr, orderTypeName)
                    .stream().filter(user -> user.getUserId() != creator.getUserId()).collect(Collectors.toList());
            request.setAttribute(REQUEST_ATTRIBUTE_USERS, users);
        } catch (ServiceException e){
            throw new CommandException(e);
        }

        session.setAttribute(ATTRIBUTE_CURRENT_PAGE, page);

        return new Router(page, FORWARD);
    }
}
