package epam.zlatamigas.surveyplatform.util.validator.impl;

import epam.zlatamigas.surveyplatform.util.validator.FormValidator;

import java.util.HashMap;
import java.util.Map;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.*;
import static epam.zlatamigas.surveyplatform.util.locale.LocalisedMessageKey.*;
import static epam.zlatamigas.surveyplatform.util.validator.ValidatorPatternHolder.EMAIL_PATTERN;
import static epam.zlatamigas.surveyplatform.util.validator.ValidatorPatternHolder.PASSWORD_PATTERN;

public class SignUpFormValidator implements FormValidator {

    private static FormValidator instance;

    private SignUpFormValidator(){}

    public static FormValidator getInstance() {
        if(instance == null){
            instance = new SignUpFormValidator();
        }
        return instance;
    }

    @Override
    public Map<String, String> validateForm(Map<String, String[]> data) {

        Map<String, String> validationResult = new HashMap<>();

        if(!data.get(PARAMETER_EMAIL)[0].matches(EMAIL_PATTERN)){
            validationResult.put(PARAMETER_EMAIL, MESSAGE_EMAIL_WRONG);
        }
        String password = data.get(PARAMETER_PASSWORD)[0];
        String passwordRepeat = data.get(PARAMETER_PASSWORD_REPEAT)[0];
        if(!password.matches(PASSWORD_PATTERN)){
            validationResult.put(PARAMETER_PASSWORD, MESSAGE_PASSWORD_WRONG);
        }
        if(!passwordRepeat.equals(password) || !passwordRepeat.matches(PASSWORD_PATTERN)){
            validationResult.put(PARAMETER_PASSWORD_REPEAT, MESSAGE_PASSWORD_REPEAT_WRONG);
        }

        return validationResult;
    }
}
