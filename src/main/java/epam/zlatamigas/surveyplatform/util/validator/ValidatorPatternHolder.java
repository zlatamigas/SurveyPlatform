package epam.zlatamigas.surveyplatform.util.validator;

public final class ValidatorPatternHolder {

    public static final String EMAIL_PATTERN = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";
    public static final String PASSWORD_PATTERN = "^[\\p{Alnum}\\p{Punct}]{6,20}$";

    private ValidatorPatternHolder(){}
}
