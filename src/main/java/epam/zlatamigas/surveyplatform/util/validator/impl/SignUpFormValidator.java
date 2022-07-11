package epam.zlatamigas.surveyplatform.util.validator.impl;

import epam.zlatamigas.surveyplatform.util.validator.FormValidator;
import epam.zlatamigas.surveyplatform.util.validator.PatternValidator;

import java.util.HashMap;
import java.util.Map;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.*;
import static epam.zlatamigas.surveyplatform.util.locale.LocalisedMessageKey.*;
import static epam.zlatamigas.surveyplatform.util.validator.PatternValidator.EMAIL_PATTERN;

public class SignUpFormValidator implements FormValidator {

    private static FormValidator instance;
    private PatternValidator validator;

    private SignUpFormValidator() {
        validator = PatternValidator.getInstance();
    }

    public static FormValidator getInstance() {
        if (instance == null) {
            instance = new SignUpFormValidator();
        }
        return instance;
    }

    @Override
    public Map<String, String> validateForm(Map<String, String[]> data) {

        Map<String, String> validationResult = new HashMap<>();

        if (!data.get(PARAMETER_EMAIL)[0].matches(EMAIL_PATTERN)) {
            validationResult.put(PARAMETER_EMAIL, MESSAGE_INVALID_USER_EMAIL);
        }
        String password = data.get(PARAMETER_PASSWORD)[0];
        String passwordRepeat = data.get(PARAMETER_PASSWORD_REPEAT)[0];
        if (!validator.validPassword(password)) {
            validationResult.put(PARAMETER_PASSWORD, MESSAGE_INVALID_USER_PASSWORD);
        }
        if (!passwordRepeat.equals(password) || !validator.validPassword(passwordRepeat)) {
            validationResult.put(PARAMETER_PASSWORD_REPEAT, MESSAGE_INVALID_USER_PASSWORD_REPEAT);
        }

        return validationResult;
    }
}
