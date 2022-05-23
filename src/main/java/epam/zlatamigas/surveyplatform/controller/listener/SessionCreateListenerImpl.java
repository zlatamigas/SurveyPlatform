package epam.zlatamigas.surveyplatform.controller.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebListener
public class SessionCreateListenerImpl implements HttpSessionListener {

    private static final Logger logger = LogManager.getLogger();

    private static final String LOCALISATION_ATTRIBUTE = "localisation";
    private static final String DEFAULT_LOCALISATION = "en";

    @Override
    public void sessionCreated(HttpSessionEvent se) {

        HttpSession session = se.getSession();
        session.setAttribute(LOCALISATION_ATTRIBUTE, DEFAULT_LOCALISATION);

        /* Session is created. */
        logger.info("---------------> session created: " + se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        /* Session is destroyed. */
        logger.info("---------------> session destroyed: " + se.getSession().getId());
    }
}
