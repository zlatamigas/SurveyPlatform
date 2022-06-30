package epam.zlatamigas.surveyplatform.util.validator.impl;

import epam.zlatamigas.surveyplatform.util.validator.FormValidator;
import epam.zlatamigas.surveyplatform.util.validator.PatternValidator;

import java.util.HashMap;
import java.util.Map;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.PARAMETER_THEME_NAME;
import static epam.zlatamigas.surveyplatform.util.locale.LocalisedMessageKey.MESSAGE_THEME_NAME_WRONG;

public class AddThemeFormValidator implements FormValidator {

    private static FormValidator instance;
    private static PatternValidator validator;

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

        if (!validator.validName(data.get(PARAMETER_THEME_NAME)[0])) {
            validationResult.put(PARAMETER_THEME_NAME, MESSAGE_THEME_NAME_WRONG);
        }

        return validationResult;
    }
}
