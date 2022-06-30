package epam.zlatamigas.surveyplatform.controller.navigation;

public final class DataHolder {

    public static final int PAGINATION_ITEMS_PER_PAGE = 4;

    // Request parameters

    // Shared
    public static final String PARAMETER_LOCALISATION = "localisation";
    public static final String PARAMETER_COMMAND = "command";
    // Log in/up
    public static final String PARAMETER_EMAIL = "email";
    public static final String PARAMETER_PASSWORD = "password";
    public static final String PARAMETER_PASSWORD_REPEAT = "password_repeat";
    public static final String PARAMETER_FORGOT_PASSWORD_CHANGE_KEY = "forgot_password_change_key";

    // CRUD survey
    public static final String PARAMETER_CREATE_NEW_SURVEY = "create_new_survey";
    public static final String PARAMETER_CREATE_NEW_QUESTION = "create_new_question";

    public static final String PARAMETER_SURVEY_POSITION = "survey_position";
    public static final String PARAMETER_QUESTION_POSITION = "question_position";
    public static final String PARAMETER_ANSWER_POSITION = "answer_position";

    public static final String PARAMETER_SURVEY_ID = "survey_id";
    public static final String PARAMETER_SURVEY_NAME = "survey_name";
    public static final String PARAMETER_SURVEY_THEME_ID = "survey_theme_id";
    public static final String PARAMETER_SURVEY_DESCRIPTION = "survey_description";

    public static final String PARAMETER_QUESTION_FORMULATION = "question_formulation";
    public static final String PARAMETER_QUESTION_SELECT_MULTIPLE = "question_select_multiple";
    public static final String PARAMETER_QUESTION_ID = "question_id";

    public static final String PARAMETER_ANSWER_TEXT = "answer_text";

    public static final String PARAMETER_ATTRIBUTE_SEARCH_WORDS = "search_words";
    public static final String PARAMETER_ATTRIBUTE_FILTER_THEME_ID = "filter_theme_id";
    public static final String PARAMETER_ATTRIBUTE_ORDER_TYPE = "order_type";

    public static final String PARAMETER_PAGINATION_PAGE_OFFSET = "pagination_page_offset";

    public static final String PARAMETER_THEME_ID = "theme_id";
    public static final String PARAMETER_THEME_NAME = "theme_name";

    public static final String PARAMETER_USER_ID = "user_id";
    public static final String PARAMETER_USER_EMAIL = "user_email";
    public static final String PARAMETER_USER_PASSWORD = "user_password";
    public static final String PARAMETER_USER_ROLE = "user_role";
    public static final String PARAMETER_USER_STATUS = "user_status";

    // Session attributes

    // Shared
    public static final String ATTRIBUTE_USER = "user";
    public static final String ATTRIBUTE_LOCALISATION = "localisation";
    public static final String ATTRIBUTE_CURRENT_PAGE = "current_page";
    public static final String ATTRIBUTE_PREVIOUS_PAGE = "previous_page";
    // Listed data
    public static final String ATTRIBUTE_THEMES = "themes";
    public static final String ATTRIBUTE_USER_SURVEYS = "user_surveys";
    public static final String ATTRIBUTE_SURVEYS = "surveys";

    public static final String ATTRIBUTE_SURVEYS_PAGE = "surveys_page";

    // Edit survey
    public static final String ATTRIBUTE_EDITED_SURVEY = "edited_survey";
    public static final String ATTRIBUTE_EDITED_QUESTION = "edited_question";
    public static final String ATTRIBUTE_EDITED_ANSWER = "edited_answer";
    // Survey result
    public static final String ATTRIBUTE_SURVEY_RESULT = "survey_result";
    // User edit (ADMIN)
    public static final String ATTRIBUTE_EDITED_USER = "edited_user";
    // User attempt
    public static final String ATTRIBUTE_SURVEY_ATTEMPT = "survey_attempt";
    // Change password key
    public static final String ATTRIBUTE_CHANGE_PASSWORD_KEY = "change_password_key";
    public static final String ATTRIBUTE_CHANGE_PASSWORD_EMAIL = "change_password_email";

    public static final String ATTRIBUTE_REQUESTED_THEMES = "requested_themes";


    public static final String ATTRIBUTE_USERS = "users";

    // Button groups

    public static final String BUTTONGROUP_NAME_CHECKBOX_ANSWERS = "check_question";
    public static final String BUTTONGROUP_NAME_RADIO_ANSWERS = "radio_question";

    // Request

    public static final String REQUEST_ATTRIBUTE_USER_EXISTS = "user_exists";
    public static final String REQUEST_ATTRIBUTE_THEME_EXISTS = "theme_exists";
    public static final String REQUEST_ATTRIBUTE_USER_INVALID = "user_invalid";
    public static final String REQUEST_ATTRIBUTE_FORM_INVALID = "form_invalid";

    public static final String ATTRIBUTE_PAGINATION_CURRENT_PAGE = "pagination_current_page";

    private DataHolder() {};

}
