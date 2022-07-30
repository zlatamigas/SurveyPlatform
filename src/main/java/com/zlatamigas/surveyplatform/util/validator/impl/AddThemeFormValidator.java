package com.zlatamigas.surveyplatform.util.validator.impl;

import com.zlatamigas.surveyplatform.util.validator.FormValidator;
import com.zlatamigas.surveyplatform.util.validator.PatternValidator;

import java.util.HashMap;
import java.util.Map;

import static com.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder.PARAMETER_THEME_NAME;
import static com.zlatamigas.surveyplatform.util.locale.LocalisedMessageKey.MESSAGE_INVALID_THEME_NAME;

/**
 * Create theme form validator. Checks validity of theme name.
 */
public class AddThemeFormValidator implements FormValidator {

    private static FormValidator instance;
    private final PatternValidator validator;

    private AddThemeFormValidator() {
        validator = PatternValidator.getInstance();
    }

    public static FormValidator getInstance() {
        if (instance == null) {
            instance = new AddThemeFormValidator();
        }
        return instance;
    }

    @Override
    public Map<String, String> validateForm(Map<String, String[]> data) {

        Map<String, String> validationResult = new HashMap<>();

        if (data.get(PARAMETER_THEME_NAME) == null
                || data.get(PARAMETER_THEME_NAME).length == 0
                || !validator.validThemeName(data.get(PARAMETER_THEME_NAME)[0])) {
            validationResult.put(PARAMETER_THEME_NAME, MESSAGE_INVALID_THEME_NAME);
        }

        return validationResult;
    }
}
