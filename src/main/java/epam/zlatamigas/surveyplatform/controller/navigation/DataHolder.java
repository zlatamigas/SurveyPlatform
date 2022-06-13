package epam.zlatamigas.surveyplatform.controller.navigation;

public final class DataHolder {

    // Request parameters

    // Shared
    public static final String PARAMETER_LOCALISATION = "localisation";
    public static final String PARAMETER_COMMAND = "command";
    // Log in/up
    public static final String PARAMETER_EMAIL = "email";
    public static final String PARAMETER_PASSWORD = "password";
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


    // Button groups

    public static final String BUTTONGROUP_NAME_CHECKBOX_ANSWERS = "check_question";
    public static final String BUTTONGROUP_NAME_RADIO_ANSWERS = "radio_question";

    private DataHolder() {};

}
