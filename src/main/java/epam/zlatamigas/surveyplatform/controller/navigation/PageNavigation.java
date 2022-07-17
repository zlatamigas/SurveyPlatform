package epam.zlatamigas.surveyplatform.controller.navigation;

public final class PageNavigation {

    public static final String DEFAULT = "/view/page/home.jsp";
    public static final String HOME = "/view/page/home.jsp";

    public static final String SIGN_IN = "/view/page/sign_in.jsp";
    public static final String SIGN_UP = "/view/page/sign_up.jsp";
    public static final String FORGOT_PASSWORD = "/view/page/forgot_password.jsp";
    public static final String FORGOT_PASSWORD_RECEIVE_KEY = "/view/page/forgot_password_receive_key.jsp";
    public static final String FORGOT_PASSWORD_CHANGE_PASSWORD = "/view/page/forgot_password_change_password.jsp";

    public static final String SURVEYS = "/view/page/surveys.jsp";
    public static final String USER_SURVEYS = "/view/page/user_surveys.jsp";
    public static final String USERS = "/view/page/users.jsp";

    public static final String EDIT_SURVEY = "/view/page/edit_survey.jsp";
    public static final String EDIT_QUESTION = "/view/page/edit_question.jsp";

    public static final String SURVEY_ATTEMPT = "/view/page/survey_attempt.jsp";
    public static final String SURVEY_RESULT = "/view/page/survey_result.jsp";

    public static final String THEMES_CONFIRMED = "/view/page/themes_confirmed.jsp";
    public static final String THEMES_WAITING = "/view/page/themes_waiting.jsp";

    public static final String ACCOUNT = "/view/page/account.jsp";

    public static final String EDIT_USER = "/view/page/edit_user.jsp";
    public static final String CREATE_USER = "/view/page/create_user.jsp";

    public static final String URL_CONTROLLER_WITH_PARAMETERS_PATTERN = "/controller?%s";
    public static final String URL_REDIRECT_BASE_PATTERN = "/controller?command=%s";
    public static final String URL_REDIRECT_PARAMETER_PATTERN = "&%s=%s";

    private PageNavigation(){}
}
