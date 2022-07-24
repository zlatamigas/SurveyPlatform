package epam.zlatamigas.surveyplatform.util.validator.impl;

import epam.zlatamigas.surveyplatform.util.validator.FormValidator;
import epam.zlatamigas.surveyplatform.util.validator.PatternValidator;

import java.util.HashMap;
import java.util.Map;

import static epam.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder.PARAMETER_SURVEY_DESCRIPTION;
import static epam.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder.PARAMETER_SURVEY_NAME;
import static epam.zlatamigas.surveyplatform.util.locale.LocalisedMessageKey.MESSAGE_INVALID_SURVEY_DESCRIPTION;
import static epam.zlatamigas.surveyplatform.util.locale.LocalisedMessageKey.MESSAGE_INVALID_SURVEY_NAME;

/**
 * Create/edit survey common info form validator. Checks survey name and description.
 */
public class SurveyEditFormValidator implements FormValidator {

    private static FormValidator instance;
    private final PatternValidator validator;

    private SurveyEditFormValidator() {
        validator = PatternValidator.getInstance();
    }

    public static FormValidator getInstance() {
        if (instance == null) {
            instance = new SurveyEditFormValidator();
        }
        return instance;
    }

    @Override
    public Map<String, String> validateForm(Map<String, String[]> data) {
        Map<String, String> validationResult = new HashMap<>();

        if (data.get(PARAMETER_SURVEY_NAME) == null
                || data.get(PARAMETER_SURVEY_NAME).length == 0
                || !validator.validSurveyName(data.get(PARAMETER_SURVEY_NAME)[0])) {
            validationResult.put(PARAMETER_SURVEY_NAME, MESSAGE_INVALID_SURVEY_NAME);
        }
        if (data.get(PARAMETER_SURVEY_DESCRIPTION) == null
                || data.get(PARAMETER_SURVEY_DESCRIPTION).length == 0
                || !validator.validUnlimitedText(data.get(PARAMETER_SURVEY_DESCRIPTION)[0])) {
            validationResult.put(PARAMETER_SURVEY_DESCRIPTION, MESSAGE_INVALID_SURVEY_DESCRIPTION);
        }

        return validationResult;
    }
}
