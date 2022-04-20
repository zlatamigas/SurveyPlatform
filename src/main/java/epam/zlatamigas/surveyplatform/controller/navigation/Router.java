package epam.zlatamigas.surveyplatform.controller.navigation;

public class Router {
    private String page = PageNavigation.DEFAULT_PAGE;
    private PageChangeType type = PageChangeType.FORWARD;

    enum PageChangeType{
        FORWARD, REDIRECT;
    }

    public Router() {
    }

    public Router(String page) {
        this.page = page;
    }

    public Router(String page, PageChangeType type) {
        this.page = page;
        this.type = type;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setRedirect() {
        this.type = PageChangeType.REDIRECT;
    }
}
