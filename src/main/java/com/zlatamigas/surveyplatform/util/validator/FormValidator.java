package com.zlatamigas.surveyplatform.util.validator;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Form validator. Used for validation request parameters.
 */
public interface FormValidator {

    /**
     * Validate request parameters from form.
     *
     * @param data Request parameters, received from form. Return value of {@link HttpServletRequest#getParameterMap()}.
     * @return Validation result: empty map if all parameters valid, otherwise map with pairs:
     * key - parameter with invalid value, value - message, describing invalid data.
     */
    Map<String, String> validateForm(Map <String, String[]> data);
}
