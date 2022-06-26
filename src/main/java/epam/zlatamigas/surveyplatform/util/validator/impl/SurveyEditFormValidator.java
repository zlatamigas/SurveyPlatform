package epam.zlatamigas.surveyplatform.util.validator.impl;

import epam.zlatamigas.surveyplatform.util.validator.FormValidator;

import java.util.HashMap;
import java.util.Map;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.*;
import static epam.zlatamigas.surveyplatform.util.locale.LocalisedMessageKey.*;
import static epam.zlatamigas.surveyplatform.util.validator.ValidatorPatternHolder.*;

public class SurveyEditFormValidator implements FormValidator {
    private static FormValidator instance;

    private SurveyEditFormValidator(){}

    public static FormValidator getInstance() {
        if(instance == null){
            instance = new SurveyEditFormValidator();
        }
        return instance;
    }

    @Override
    public Map<String, String> validateForm(Map<String, String[]> data) {
        Map<String, String> validationResult = new HashMap<>();

        if(!data.get(PARAMETER_SURVEY_NAME)[0].matches(NAME_PATTERN)){
            validationResult.put(PARAMETER_SURVEY_NAME, MESSAGE_SURVEY_NAME_WRONG);
        }
        if(!data.get(PARAMETER_SURVEY_DESCRIPTION)[0].matches(UNLIMITED_TEXT_PATTERN)){
            validationResult.put(PARAMETER_SURVEY_DESCRIPTION, MESSAGE_SURVEY_DESCRIPTION_WRONG);
        }

        return validationResult;
    }
}
