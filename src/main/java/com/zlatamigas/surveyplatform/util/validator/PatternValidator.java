package com.zlatamigas.surveyplatform.util.validator;

/**
 * Pattern holder and single value validator.
 */
public final class PatternValidator {

    public static final int EMAIL_MAX_LENGTH = 45;
    public static final String EMAIL_PATTERN =
            "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";
    public static final String PASSWORD_PATTERN =
            "^[\\p{Alnum}\\p{Punct}]{8,20}$";
    public static final String THEME_NAME_PATTERN =
            "^[\\p{Alnum}\\p{Punct}А-яЁё][\\p{Alnum}\\p{Punct}А-яЁё ]{3,199}$";
    public static final String SURVEY_NAME_PATTERN =
            "^[\\p{Alnum}\\p{Punct}А-яЁё][\\p{Alnum}\\p{Punct}А-яЁё ]{3,}$";
    public static final String QUESTION_FORMULATION_PATTERN =
            "^[\\p{Alnum}\\p{Punct}А-яЁё][\\p{Alnum}\\p{Punct}А-яЁё\\s]{3,}$";
    public static final String UNLIMITED_TEXT_PATTERN =
            "^[\\p{Alnum}\\p{Punct}А-яЁё\\s]*$";

    private static final PatternValidator instance = new PatternValidator();

    private PatternValidator() {
    }

    public static PatternValidator getInstance() {
        return instance;
    }

    public boolean validEmail(String email) {
        return email != null && email.matches(EMAIL_PATTERN) && email.length() <= EMAIL_MAX_LENGTH;
    }

    public boolean validPassword(String password) {
        return password != null && password.matches(PASSWORD_PATTERN);
    }

    public boolean validThemeName(String name) {
        return name != null && name.matches(THEME_NAME_PATTERN);
    }

    public boolean validSurveyName(String name) {
        return name != null && name.matches(SURVEY_NAME_PATTERN);
    }

    public boolean validQuestionFormulation(String name) {
        return name != null && name.matches(QUESTION_FORMULATION_PATTERN);
    }

    public boolean validUnlimitedText(String text) {
        return text != null && text.matches(UNLIMITED_TEXT_PATTERN);
    }
}
