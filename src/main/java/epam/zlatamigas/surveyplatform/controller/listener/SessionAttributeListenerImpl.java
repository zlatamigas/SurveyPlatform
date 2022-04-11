package epam.zlatamigas.surveyplatform.controller.listener;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebListener
public class SessionAttributeListenerImpl implements HttpSessionAttributeListener {

    private static final Logger logger = LogManager.getLogger();
    @Override
    public void attributeAdded(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is added to a session. */
        logger.info("---------------> session attribute added: " + sbe.getSession().getAttribute("user_name"));
        logger.info("---------------> session attribute added: " + sbe.getSession().getAttribute("current_page"));
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is removed from a session. */
        logger.info("---------------> session attribute removed");
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is replaced in a session. */
        logger.info("---------------> session attribute replaced: " + sbe.getSession().getAttribute("user_name"));
        logger.info("---------------> session attribute replaced: " + sbe.getSession().getAttribute("current_page"));
    }
}
