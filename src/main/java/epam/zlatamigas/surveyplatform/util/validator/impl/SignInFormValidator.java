package epam.zlatamigas.surveyplatform.util.validator.impl;

import epam.zlatamigas.surveyplatform.util.validator.FormValidator;
import epam.zlatamigas.surveyplatform.util.validator.PatternValidator;

import java.util.HashMap;
import java.util.Map;

import static epam.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder.PARAMETER_EMAIL;
import static epam.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder.PARAMETER_PASSWORD;
import static epam.zlatamigas.surveyplatform.util.locale.LocalisedMessageKey.MESSAGE_INVALID_USER_EMAIL;
import static epam.zlatamigas.surveyplatform.util.locale.LocalisedMessageKey.MESSAGE_INVALID_USER_PASSWORD;

/**
 * Sign in form validator. Checks email and password validity.
 */
public class SignInFormValidator implements FormValidator {

    private static FormValidator instance;
    private final PatternValidator validator;

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

        if (data.get(PARAMETER_EMAIL) == null
                || data.get(PARAMETER_EMAIL).length == 0
                || !validator.validEmail(data.get(PARAMETER_EMAIL)[0])) {
            validationResult.put(PARAMETER_EMAIL, MESSAGE_INVALID_USER_EMAIL);
        }
        if (data.get(PARAMETER_PASSWORD) == null
                || data.get(PARAMETER_PASSWORD).length == 0
                || !validator.validPassword(data.get(PARAMETER_PASSWORD)[0])) {
            validationResult.put(PARAMETER_PASSWORD, MESSAGE_INVALID_USER_PASSWORD);
        }

        return validationResult;
    }
}
