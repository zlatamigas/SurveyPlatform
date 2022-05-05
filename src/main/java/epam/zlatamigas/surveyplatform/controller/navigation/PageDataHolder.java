package epam.zlatamigas.surveyplatform.controller.navigation;

public final class PageDataHolder {

    // Request parameter

    public static final String PARAMETER_LOCALISATION = "localisation";
    public static final String PARAMETER_COMMAND = "command";

    public static final String PARAMETER_EMAIL = "email";
    public static final String PARAMETER_PASSWORD = "password";

    public static final String PARAMETER_CREATE_NEW = "create_new";
    public static final String PARAMETER_SURVEY_ID = "survey_id";
    public static final String PARAMETER_SURVEY_NAME = "survey_name";
    public static final String PARAMETER_SURVEY_THEME_ID = "survey_theme_id";
    public static final String PARAMETER_SURVEY_DESCRIPTION = "survey_description";

    // Session attribute

    public static final String ATTRIBUTE_USER = "user";
    public static final String ATTRIBUTE_LOCALISATION = "localisation";
    public static final String ATTRIBUTE_CURRENT_PAGE = "current_page";
    public static final String ATTRIBUTE_PREVIOUS_PAGE = "previous_page";
    public static final String ATTRIBUTE_SURVEYS = "surveys";
    public static final String ATTRIBUTE_THEMES = "themes";
    public static final String ATTRIBUTE_USER_SURVEYS = "user_surveys";
    public static final String ATTRIBUTE_CURRENT_SURVEY = "current_survey";

    private PageDataHolder() {};
}
