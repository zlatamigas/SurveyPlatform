package epam.zlatamigas.surveyplatform.controller.navigation;

public final class DataHolder {

    // Session attributes
    public static final String SESSION_ATTRIBUTE_PARAMETER_LOCALISATION = "localisation";
    public static final String SESSION_ATTRIBUTE_USER = "user";
    public static final String SESSION_ATTRIBUTE_CURRENT_PAGE = "current_page";
    // Listed data
    public static final String SESSION_ATTRIBUTE_THEMES = "themes";
    // Edited data
    public static final String SESSION_ATTRIBUTE_EDITED_SURVEY = "edited_survey";
    public static final String SESSION_ATTRIBUTE_EDITED_QUESTION = "edited_question";
    public static final String SESSION_ATTRIBUTE_SURVEY_ATTEMPT = "survey_attempt";


    // TODO: Change password
    public static final String SESSION_ATTRIBUTE_CHANGE_PASSWORD_KEY = "change_password_key";
    public static final String SESSION_ATTRIBUTE_CHANGE_PASSWORD_EMAIL = "change_password_email";


    // Request parameter-attribute
    // Search data
    public static final String REQUEST_ATTRIBUTE_PARAMETER_SEARCH_WORDS = "search_words";
    public static final String REQUEST_ATTRIBUTE_PARAMETER_ORDER_TYPE = "order_type";
    public static final String REQUEST_ATTRIBUTE_PARAMETER_FILTER_THEME_ID = "filter_theme_id";
    public static final String REQUEST_ATTRIBUTE_PARAMETER_FILTER_SURVEY_STATUS = "filter_survey_status";
    public static final String REQUEST_ATTRIBUTE_PARAMETER_FILTER_USER_ROLE = "filter_user_role";
    public static final String REQUEST_ATTRIBUTE_PARAMETER_FILTER_USER_STATUS = "filter_user_status";
    // Edit existing user (admin)
    public static final String REQUEST_ATTRIBUTE_PARAMETER_USER_ID = "user_id";
    public static final String REQUEST_ATTRIBUTE_PARAMETER_USER_EMAIL = "user_email";
    public static final String REQUEST_ATTRIBUTE_PARAMETER_USER_ROLE = "user_role";
    public static final String REQUEST_ATTRIBUTE_PARAMETER_USER_STATUS = "user_status";


    // Request attribute
    // Listed data
    public static final String REQUEST_ATTRIBUTE_AVAILABLE_THEMES_LIST = "available_themes_list";
    public static final String REQUEST_ATTRIBUTE_SURVEYS = "surveys";
    public static final String REQUEST_ATTRIBUTE_USER_SURVEYS = "user_surveys";
    public static final String REQUEST_ATTRIBUTE_USERS = "users";
    public static final String REQUEST_ATTRIBUTE_REQUESTED_THEMES = "requested_themes";
    public static final String REQUEST_ATTRIBUTE_SURVEY_RESULT = "survey_result";
    // Validation feedback
    public static final String REQUEST_ATTRIBUTE_FORM_INVALID = "form_invalid";
    public static final String REQUEST_ATTRIBUTE_USER_INVALID = "user_invalid";
    public static final String REQUEST_ATTRIBUTE_USER_EXISTS = "user_exists";
    public static final String REQUEST_ATTRIBUTE_USER_BANNED = "user_banned";
    public static final String REQUEST_ATTRIBUTE_THEME_EXISTS = "theme_exists";


    // Button groups
    // Survey attempt
    public static final String BUTTONGROUP_NAME_CHECKBOX_ANSWERS = "check_question";
    public static final String BUTTONGROUP_NAME_RADIO_ANSWERS = "radio_question";


    // Request parameters
    // Shared
    public static final String PARAMETER_COMMAND = "command";
    // User
    public static final String PARAMETER_USER_ID = "user_id";
    public static final String PARAMETER_EMAIL = "email";
    public static final String PARAMETER_PASSWORD = "password";
    public static final String PARAMETER_PASSWORD_REPEAT = "password_repeat";
    public static final String PARAMETER_FORGOT_PASSWORD_CHANGE_KEY = "forgot_password_change_key";
    // Survey
    public static final String PARAMETER_CREATE_NEW_SURVEY = "create_new_survey";
    public static final String PARAMETER_CREATE_NEW_QUESTION = "create_new_question";
    public static final String PARAMETER_SURVEY_ID = "survey_id";
    public static final String PARAMETER_SURVEY_NAME = "survey_name";
    public static final String PARAMETER_SURVEY_THEME_ID = "survey_theme_id";
    public static final String PARAMETER_SURVEY_DESCRIPTION = "survey_description";
    public static final String PARAMETER_QUESTION_ID = "question_id";
    public static final String PARAMETER_QUESTION_FORMULATION = "question_formulation";
    public static final String PARAMETER_QUESTION_SELECT_MULTIPLE = "question_select_multiple";
    public static final String PARAMETER_ANSWER_TEXT = "answer_text";
    public static final String PARAMETER_LAST_ANSWER_POSITION = "last_answer_pos";
    // Theme
    public static final String PARAMETER_THEME_ID = "theme_id";
    public static final String PARAMETER_THEME_NAME = "theme_name";

    private DataHolder() {
    }

    ;

}
