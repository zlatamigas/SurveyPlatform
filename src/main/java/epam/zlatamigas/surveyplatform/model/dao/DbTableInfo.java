package epam.zlatamigas.surveyplatform.model.dao;

/**
 * Database info: table, column names.
 */
public final class DbTableInfo {

    //Table: surveys
    public static final String SURVEYS_TABLE = "surveys";
    public static final String SURVEYS_TABLE_PK_COLUMN = "id_survey";
    public static final String SURVEYS_TABLE_NAME_COLUMN = "survey_name";
    public static final String SURVEYS_TABLE_DESCRIPTION_COLUMN = "survey_description";
    public static final String SURVEYS_TABLE_STATUS_COLUMN = "survey_status";
    public static final String SURVEYS_TABLE_START_DATE_TIME_COLUMN = "start_date_time";
    public static final String SURVEYS_TABLE_CLOSE_DATE_TIME_COLUMN = "close_date_time";
    public static final String SURVEYS_TABLE_FK_THEME_ID_COLUMN = "theme_id";
    public static final String SURVEYS_TABLE_FK_CREATOR_ID_COLUMN = "creator_id";

    //Table: questions
    public static final String QUESTIONS_TABLE = "questions";
    public static final String QUESTIONS_TABLE_PK_COLUMN = "id_question";
    public static final String QUESTIONS_TABLE_SELECT_MULTIPLE_COLUMN = "select_multiple";
    public static final String QUESTIONS_TABLE_FORMULATION_COLUMN = "formulation";
    public static final String QUESTIONS_TABLE_FK_SURVEY_ID_COLUMN = "survey_id";

    //Table: question_answers
    public static final String ANSWERS_TABLE = "question_answers";
    public static final String ANSWERS_TABLE_PK_COLUMN = "id_question_answer";
    public static final String ANSWERS_TABLE_ANSWER_COLUMN = "answer";
    public static final String ANSWERS_TABLE_SELECTED_COUNT_COLUMN = "selected_count";
    public static final String ANSWERS_TABLE_FK_QUESTION_ID_COLUMN = "question_id";

    //Table: survey_user_attempts
    public static final String ATTEMPTS_TABLE = "survey_user_attempts";
    public static final String ATTEMPTS_TABLE_PK_COLUMN = "id_survey_user_attempt";
    public static final String ATTEMPTS_TABLE_FINISHED_DATE_TIME_COLUMN = "finished_date_time";
    public static final String ATTEMPTS_TABLE_FK_SURVEY_ID_COLUMN = "survey_id";
    public static final String ATTEMPTS_TABLE_FK_USER_ID_COLUMN = "user_id";

    //Table: themes
    public static final String THEMES_TABLE = "themes";
    public static final String THEMES_TABLE_PK_COLUMN = "id_theme";
    public static final String THEMES_TABLE_NAME_COLUMN = "theme_name";
    public static final String THEMES_TABLE_STATUS_COLUMN = "theme_status";

    //Table: users
    public static final String USERS_TABLE = "users";
    public static final String USERS_TABLE_PK_COLUMN = "id_user";
    public static final String USERS_TABLE_EMAIL_COLUMN = "email";
    public static final String USERS_TABLE_PASSWORD_COLUMN = "password";
    public static final String USERS_TABLE_REGISTRATION_DATE_COLUMN = "registration_date";
    public static final String USERS_TABLE_ROLE_COLUMN = "user_role";
    public static final String USERS_TABLE_STATUS_COLUMN = "user_status";


    private DbTableInfo() {
    }
}
