package epam.zlatamigas.surveyplatform.util.validator;

public final class PatternValidator {

    public static final String EMAIL_PATTERN = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";
    public static final String PASSWORD_PATTERN = "^[\\p{Alnum}\\p{Punct}]{8,20}$";

    public static final String NAME_PATTERN = "^[\\p{Alnum}\\p{Punct}а-яА-Я][\\p{Alnum}\\p{Punct}а-яА-Я ]{0,197}[\\p{Alnum}\\p{Punct}а-яА-Я]$";
    public static final String UNLIMITED_TEXT_PATTERN = "^[\\p{Alnum}\\p{Punct}а-яА-Я ]*$";

    private static final PatternValidator instance = new PatternValidator();

    private PatternValidator(){}

    public static PatternValidator getInstance() {
        return instance;
    }

    public boolean validEmail(String email){
        return email.matches(EMAIL_PATTERN);
    }

    public boolean validPassword(String password){
        return password.matches(PASSWORD_PATTERN);
    }

    public boolean validName(String name){
        return name.matches(NAME_PATTERN);
    }

    public boolean validUnlimitedText(String text){
        return text.matches(UNLIMITED_TEXT_PATTERN);
    }
}
