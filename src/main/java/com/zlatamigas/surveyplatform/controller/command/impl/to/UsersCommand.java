package com.zlatamigas.surveyplatform.controller.command.impl.to;

import com.zlatamigas.surveyplatform.controller.command.Command;
import com.zlatamigas.surveyplatform.controller.navigation.Router;
import com.zlatamigas.surveyplatform.exception.CommandException;
import com.zlatamigas.surveyplatform.exception.ServiceException;
import com.zlatamigas.surveyplatform.model.entity.User;
import com.zlatamigas.surveyplatform.service.UserService;
import com.zlatamigas.surveyplatform.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

import static com.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder.*;
import static com.zlatamigas.surveyplatform.controller.navigation.PageNavigation.URL_CONTROLLER_WITH_PARAMETERS_PATTERN;
import static com.zlatamigas.surveyplatform.controller.navigation.PageNavigation.USERS;
import static com.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;
import static com.zlatamigas.surveyplatform.util.search.SearchParameter.*;

public class UsersCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();

        String searchWordsStr = request.getParameter(REQUEST_ATTRIBUTE_PARAMETER_SEARCH_WORDS);
        if (searchWordsStr == null) {
            searchWordsStr = DEFAULT_SEARCH_WORDS;
        }

        String userRoleName = request.getParameter(REQUEST_ATTRIBUTE_PARAMETER_FILTER_USER_ROLE);
        if (userRoleName == null) {
            userRoleName = DEFAULT_FILTER_STR_ALL;
        }
        String userStatusName = request.getParameter(REQUEST_ATTRIBUTE_PARAMETER_FILTER_USER_STATUS);
        if (userStatusName == null) {
            userStatusName = DEFAULT_FILTER_STR_ALL;
        }

        String orderTypeName = request.getParameter(REQUEST_ATTRIBUTE_PARAMETER_ORDER_TYPE);
        if (orderTypeName == null) {
            orderTypeName = DEFAULT_ORDER;
        }

        request.setAttribute(REQUEST_ATTRIBUTE_PARAMETER_SEARCH_WORDS, searchWordsStr);
        request.setAttribute(REQUEST_ATTRIBUTE_PARAMETER_FILTER_USER_ROLE, userRoleName);
        request.setAttribute(REQUEST_ATTRIBUTE_PARAMETER_FILTER_USER_STATUS, userStatusName);
        request.setAttribute(REQUEST_ATTRIBUTE_PARAMETER_ORDER_TYPE, orderTypeName);

        User admin = (User) session.getAttribute(SESSION_ATTRIBUTE_USER);

        UserService service = UserServiceImpl.getInstance();

        try {
            List<User> users = service.findUsersBySearch(userRoleName, userStatusName, searchWordsStr, orderTypeName)
                    .stream().filter(user -> user.getUserId() != admin.getUserId()).collect(Collectors.toList());
            request.setAttribute(REQUEST_ATTRIBUTE_USERS, users);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        session.setAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE,
                String.format(URL_CONTROLLER_WITH_PARAMETERS_PATTERN, request.getQueryString()));

        return new Router(USERS, FORWARD);
    }
}
