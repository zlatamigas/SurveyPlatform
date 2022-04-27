package epam.zlatamigas.surveyplatform.controller;

import java.io.*;

import epam.zlatamigas.surveyplatform.controller.navigation.Router;
import epam.zlatamigas.surveyplatform.exception.CommandException;
import epam.zlatamigas.surveyplatform.controller.command.Command;
import epam.zlatamigas.surveyplatform.controller.command.CommandType;
import epam.zlatamigas.surveyplatform.model.connection.ConnectionPool;
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

    @Override
    public void init() {
        ConnectionPool.getInstance();
        logger.info("---------------> Servlet init");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processCommand(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processCommand(request, response);
    }

    private void processCommand(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType(CONTENT_TYPE);

        String commandStr = request.getParameter(COMMAND_PARAMETER);
        Command command = CommandType.define(commandStr);

        try {

            Router router = command.execute(request);
            String page = router.getPage();
            switch(router.getType()){
                case FORWARD -> request.getRequestDispatcher(page).forward(request, response);
                case REDIRECT -> response.sendRedirect(request.getContextPath() + page);
                default -> {
                    logger.error("Invalid routing type!");
                    response.sendError(500);
                }
            }

        } catch (CommandException e) {
            logger.error("Error while command execution: " + commandStr, e);
            throw new ServletException(e);
        }
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().destroyPool();
        logger.info("---------------> Servlet destroy");
    }
}
