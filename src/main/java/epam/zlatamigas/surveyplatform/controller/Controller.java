package epam.zlatamigas.surveyplatform.controller;

import java.io.*;
import java.sql.Date;

import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.exception.DaoException;
import epam.zlatamigas.surveyplatform.model.command.Command;
import epam.zlatamigas.surveyplatform.model.command.CommandType;
import epam.zlatamigas.surveyplatform.model.dao.impl.UserDaoImpl;
import epam.zlatamigas.surveyplatform.model.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(name = "appController", value = "/controller")
public class Controller extends HttpServlet {

    private static final Logger logger = LogManager.getLogger();

    private static final String COMMAND_PARAMETER = "command";
    private static final String CONTENT_TYPE = "text/html";

    public void init() {
        //ConnectionPool.getInstance();
        logger.info("---------------> Servlet init");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType(CONTENT_TYPE);

        String commandStr = request.getParameter(COMMAND_PARAMETER);
        Command command = CommandType.define(commandStr);
        String page = null;
        try {
            page = command.execute(request);
            request.getRequestDispatcher(page).forward(request, response);
            //response.sendRedirect( page); // защита от F5 - не работает пока что
            // forward - передает пару запрос-ответ дальше, redirect - сбрасывает данные
        } catch (CommandException e) {
            //request.setAttribute("error_msg", e.getMessage());
            //request.getRequestDispatcher(PageNavigation.ERROR_500_PAGE).forward(request, response); // 3 - без выброса ошибки, просто переход
            //response.sendError(500);// 1 - Сообщение от ошибки сюда не уйдет?
            throw new ServletException(e); // 2 - От сервлета: Кидает на страницу ошибки, обозначенной в web.xml по коду с сообщением об ошибке
        }
    }

    public void destroy() {
        //ConnectionPool.getInstance().destroyPool();
        // + закрытие драйверов
        logger.info("---------------> Servlet destroy");
    }
}