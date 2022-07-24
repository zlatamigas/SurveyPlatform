package epam.zlatamigas.surveyplatform.controller.navigation;

import static epam.zlatamigas.surveyplatform.controller.navigation.PageNavigation.DEFAULT;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.FORWARD;
import static epam.zlatamigas.surveyplatform.controller.navigation.Router.PageChangeType.REDIRECT;

/**
 * Router class, containing page to move to and routing type.
 */
public class Router {

    private String page = DEFAULT;
    private PageChangeType type = FORWARD;

    public enum PageChangeType {
        FORWARD, REDIRECT;
    }

    public Router() {
    }

    public Router(String page) {
        this.page = (page != null ? page : DEFAULT);
    }

    public Router(String page, PageChangeType type) {
        this.page = (page != null ? page : DEFAULT);
        this.type = (type != null ? type : FORWARD);
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = (page != null ? page : DEFAULT);
    }

    public void setRedirect() {
        this.type = REDIRECT;
    }

    public void setForward() {
        this.type = FORWARD;
    }

    public PageChangeType getType() {
        return type;
    }
}
