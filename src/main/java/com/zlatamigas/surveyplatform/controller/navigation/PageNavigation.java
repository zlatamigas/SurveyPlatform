package com.zlatamigas.surveyplatform.controller.navigation;

/**
 * Class with paths to pages and patterns for routing requests.
 */
public final class PageNavigation {

    public static final String DEFAULT = "/view/page/home.jsp";
    public static final String HOME = "/view/page/home.jsp";

    public static final String SIGN_IN = "/view/page/sign_in.jsp";
    public static final String SIGN_UP = "/view/page/sign_up.jsp";

    public static final String SEND_KEY_TO_EMAIL = "/view/page/send_key_to_email.jsp";
    public static final String RECEIVE_KEY = "/view/page/receive_key.jsp";
    public static final String CHANGE_PASSWORD = "/view/page/change_password.jsp";

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

    public static final String URL_REDIRECT_USER_SURVEYS = "/SEARCH_SURVEY_CREATED_BY_USER";
    public static final String URL_REDIRECT_THEMES_CONFIRMED = "/THEMES_CONFIRMED";

    public static final String URL_CONTROLLER_WITH_PARAMETERS_PATTERN = "/controller?%s";
    public static final String URL_REDIRECT_BASE_PATTERN = "/controller?command=%s";
    public static final String URL_REDIRECT_PARAMETER_PATTERN = "&%s=%s";

    private PageNavigation() {
    }
}
