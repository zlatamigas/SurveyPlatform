package epam.zlatamigas.surveyplatform.util.validator;

public final class ValidatorPatternHolder {

    public static final String EMAIL_PATTERN = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";
    public static final String PASSWORD_PATTERN = "^[\\p{Alnum}\\p{Punct}]{8,20}$";

    public static final String NAME_PATTERN = "^[\\p{Alnum}\\p{Punct}а-яА-Я][\\p{Alnum}\\p{Punct}а-яА-Я ]{0,198}[\\p{Alnum}\\p{Punct}а-яА-Я]$";
    public static final String UNLIMITED_TEXT_PATTERN = "^[\\p{Alnum}\\p{Punct}а-яА-Я ]*$";

    private ValidatorPatternHolder(){}
}
