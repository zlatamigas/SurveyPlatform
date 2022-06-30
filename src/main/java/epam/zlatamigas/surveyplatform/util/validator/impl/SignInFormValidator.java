package epam.zlatamigas.surveyplatform.util.validator.impl;

import epam.zlatamigas.surveyplatform.util.validator.FormValidator;
import epam.zlatamigas.surveyplatform.util.validator.PatternValidator;

import java.util.HashMap;
import java.util.Map;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.PARAMETER_EMAIL;
import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.PARAMETER_PASSWORD;
import static epam.zlatamigas.surveyplatform.util.locale.LocalisedMessageKey.MESSAGE_EMAIL_WRONG;
import static epam.zlatamigas.surveyplatform.util.locale.LocalisedMessageKey.MESSAGE_PASSWORD_WRONG;

public class SignInFormValidator implements FormValidator {

    private static FormValidator instance;
    private static PatternValidator validator;

    private SignInFormValidator() {
        validator = PatternValidator.getInstance();
    }

    public static FormValidator getInstance() {
        if (instance == null) {
            instance = new SignInFormValidator();
        }
        return instance;
    }

    @Override
    public Map<String, String> validateForm(Map<String, String[]> data) {

        Map<String, String> validationResult = new HashMap<>();

        if (!validator.validEmail(data.get(PARAMETER_EMAIL)[0])) {
            validationResult.put(PARAMETER_EMAIL, MESSAGE_EMAIL_WRONG);
        }
        if (!validator.validPassword(data.get(PARAMETER_PASSWORD)[0])) {
            validationResult.put(PARAMETER_PASSWORD, MESSAGE_PASSWORD_WRONG);
        }

        return validationResult;
    }
}
