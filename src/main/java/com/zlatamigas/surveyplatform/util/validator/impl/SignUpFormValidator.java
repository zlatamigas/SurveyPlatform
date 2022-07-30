package com.zlatamigas.surveyplatform.util.validator.impl;

import com.zlatamigas.surveyplatform.util.validator.FormValidator;
import com.zlatamigas.surveyplatform.util.validator.PatternValidator;

import java.util.HashMap;
import java.util.Map;

import static com.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder.*;
import static com.zlatamigas.surveyplatform.util.locale.LocalisedMessageKey.*;
import static com.zlatamigas.surveyplatform.util.validator.PatternValidator.EMAIL_PATTERN;

/**
 * Sign up form validator. Checks email and password validity and equality between password and repeated password.
 */
public class SignUpFormValidator implements FormValidator {

    private static FormValidator instance;
    private final PatternValidator validator;

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

        if (data.get(PARAMETER_EMAIL) == null
                || data.get(PARAMETER_EMAIL).length == 0
                || !validator.validEmail(data.get(PARAMETER_EMAIL)[0])) {
            validationResult.put(PARAMETER_EMAIL, MESSAGE_INVALID_USER_EMAIL);
        }

        if(data.get(PARAMETER_PASSWORD) != null && data.get(PARAMETER_PASSWORD).length != 0){
            String password = data.get(PARAMETER_PASSWORD)[0];

            if (!validator.validPassword(password)) {
                validationResult.put(PARAMETER_PASSWORD, MESSAGE_INVALID_USER_PASSWORD);
            }

            if(data.get(PARAMETER_PASSWORD_REPEAT) != null && data.get(PARAMETER_PASSWORD_REPEAT).length != 0) {
                String passwordRepeat = data.get(PARAMETER_PASSWORD_REPEAT)[0];

                if (!passwordRepeat.equals(password) || !validator.validPassword(passwordRepeat)) {
                    validationResult.put(PARAMETER_PASSWORD_REPEAT, MESSAGE_INVALID_USER_PASSWORD_REPEAT);
                }
            } else {
                validationResult.put(PARAMETER_PASSWORD_REPEAT, MESSAGE_INVALID_USER_PASSWORD_REPEAT);
            }
        } else {
            validationResult.put(PARAMETER_PASSWORD, MESSAGE_INVALID_USER_PASSWORD);
        }

        return validationResult;
    }
}
