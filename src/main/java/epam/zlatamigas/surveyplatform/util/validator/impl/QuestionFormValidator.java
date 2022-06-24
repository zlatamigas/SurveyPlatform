package epam.zlatamigas.surveyplatform.util.validator.impl;

import epam.zlatamigas.surveyplatform.util.validator.FormValidator;

import java.util.HashMap;
import java.util.Map;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.*;
import static epam.zlatamigas.surveyplatform.util.locale.LocalisedMessageKey.*;
import static epam.zlatamigas.surveyplatform.util.validator.ValidatorPatternHolder.NAME_PATTERN;
import static epam.zlatamigas.surveyplatform.util.validator.ValidatorPatternHolder.UNLIMITED_TEXT_PATTERN;

public class QuestionFormValidator implements FormValidator {

    private static FormValidator instance;

    private QuestionFormValidator(){}

    public static FormValidator getInstance() {
        if(instance == null){
            instance = new QuestionFormValidator();
        }
        return instance;
    }

    @Override
    public Map<String, String> validateForm(Map<String, String[]> data) {
        Map<String, String> validationResult = new HashMap<>();

        if(!data.get(PARAMETER_QUESTION_FORMULATION)[0].matches(NAME_PATTERN)){
            validationResult.put(PARAMETER_QUESTION_FORMULATION, MESSAGE_QUESTION_FORMULATION_WRONG);
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
