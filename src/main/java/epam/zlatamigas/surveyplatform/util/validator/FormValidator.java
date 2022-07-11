package epam.zlatamigas.surveyplatform.util.validator;

import java.util.Map;

/**
 * Form validator. Used for validation request parameters.
 */
public interface FormValidator {

    Map<String, String> validateForm(Map <String, String[]> data);
}
