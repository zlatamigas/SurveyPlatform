package epam.zlatamigas.surveyplatform.util.validator.impl;

import epam.zlatamigas.surveyplatform.util.validator.FormValidator;
import epam.zlatamigas.surveyplatform.util.validator.PatternValidator;

import java.util.HashMap;
import java.util.Map;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.PARAMETER_QUESTION_FORMULATION;
import static epam.zlatamigas.surveyplatform.util.locale.LocalisedMessageKey.MESSAGE_INVALID_QUESTION_FORMULATION;

public class QuestionFormValidator implements FormValidator {

    private static FormValidator instance;
    private static PatternValidator validator;

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

        if (!validator.validName(data.get(PARAMETER_QUESTION_FORMULATION)[0])) {
            validationResult.put(PARAMETER_QUESTION_FORMULATION, MESSAGE_INVALID_QUESTION_FORMULATION);
        }

//        String[] answer;
//        int i = 0;
//        while (true) {
//
//            answer = data.get(PARAMETER_ANSWER_TEXT);
//            if(answer==null){
//                break;
//            }
//
//            if(!answer[0].matches(UNLIMITED_TEXT_PATTERN)){
//                validationResult.put(PARAMETER_ANSWER_TEXT + i, MESSAGE_ANSWER_WRONG);
//            }
//
//            i++;
//        }

        return validationResult;
    }
}
