package com.zlatamigas.surveyplatform.util.validator.impl;

import com.zlatamigas.surveyplatform.util.validator.FormValidator;

import java.util.HashMap;
import java.util.Map;

import static com.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder.*;
import static com.zlatamigas.surveyplatform.util.locale.LocalisedMessageKey.MESSAGE_INVALID_ANSWER_REQUIRE_SELECT_MULTIPLE;
import static com.zlatamigas.surveyplatform.util.locale.LocalisedMessageKey.MESSAGE_INVALID_ANSWER_REQUIRE_SELECT_SINGLE;

/**
 * Survey attempt form validator. Checks radiobutton and checkbox groups for required at least single selection.
 */
public class SurveyUserAttemptFormValidator implements FormValidator {

    private static FormValidator instance;

    private SurveyUserAttemptFormValidator() {
    }

    public static FormValidator getInstance() {
        if (instance == null) {
            instance = new SurveyUserAttemptFormValidator();
        }
        return instance;
    }

    @Override
    public Map<String, String> validateForm(Map<String, String[]> data) {
        Map<String, String> validationResult = new HashMap<>();

        for (Map.Entry<String, String[]> parameter : data.entrySet()) {
            if (parameter.getKey().startsWith(PARAMETER_QUESTION_SELECT_MULTIPLE)) {
                String questionId = parameter.getKey().split(PARAMETER_QUESTION_SELECT_MULTIPLE)[1];

                if (Boolean.parseBoolean(parameter.getValue()[0])) {
                    // Can select multiple: checkboxes
                    String[] checkboxes = data.get(BUTTONGROUP_NAME_CHECKBOX_ANSWERS + questionId);
                    if (checkboxes == null || checkboxes.length == 0) {
                        validationResult.put(PARAMETER_QUESTION_ID + questionId, MESSAGE_INVALID_ANSWER_REQUIRE_SELECT_MULTIPLE);
                    }
                } else {
                    // Can select single: radio button
                    String[] radiobuttons = data.get(BUTTONGROUP_NAME_RADIO_ANSWERS + questionId);
                    if (radiobuttons == null || radiobuttons.length != 1) {
                        validationResult.put(PARAMETER_QUESTION_ID + questionId, MESSAGE_INVALID_ANSWER_REQUIRE_SELECT_SINGLE);
                    }
                }
            }
        }

        return validationResult;
    }
}
