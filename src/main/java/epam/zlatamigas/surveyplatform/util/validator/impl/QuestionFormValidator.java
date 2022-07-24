package epam.zlatamigas.surveyplatform.util.validator.impl;

import epam.zlatamigas.surveyplatform.util.validator.FormValidator;
import epam.zlatamigas.surveyplatform.util.validator.PatternValidator;

import java.util.HashMap;
import java.util.Map;

import static epam.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder.PARAMETER_QUESTION_FORMULATION;
import static epam.zlatamigas.surveyplatform.util.locale.LocalisedMessageKey.MESSAGE_INVALID_QUESTION_FORMULATION;

/**
 * Create/edit question form validator. Check validity of question formulation.
 */
public class QuestionFormValidator implements FormValidator {

    private static FormValidator instance;
    private final PatternValidator validator;

    private QuestionFormValidator() {
        validator = PatternValidator.getInstance();
    }

    public static FormValidator getInstance() {
        if (instance == null) {
            instance = new QuestionFormValidator();
        }
        return instance;
    }

    @Override
    public Map<String, String> validateForm(Map<String, String[]> data) {
        Map<String, String> validationResult = new HashMap<>();

        if (data.get(PARAMETER_QUESTION_FORMULATION) == null
                || data.get(PARAMETER_QUESTION_FORMULATION).length == 0
                || !validator.validQuestionFormulation(data.get(PARAMETER_QUESTION_FORMULATION)[0])) {
            validationResult.put(PARAMETER_QUESTION_FORMULATION, MESSAGE_INVALID_QUESTION_FORMULATION);
        }

        return validationResult;
    }
}
