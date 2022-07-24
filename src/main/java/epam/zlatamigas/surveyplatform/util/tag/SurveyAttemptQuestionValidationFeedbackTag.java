package epam.zlatamigas.surveyplatform.util.tag;

import epam.zlatamigas.surveyplatform.model.entity.SurveyQuestion;
import epam.zlatamigas.surveyplatform.util.locale.ResourceBundleManager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;

import static epam.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder.*;
import static epam.zlatamigas.surveyplatform.util.locale.LocalisedMessageKey.MESSAGE_INVALID_ANSWER_REQUIRE_SELECT_MULTIPLE;
import static epam.zlatamigas.surveyplatform.util.locale.LocalisedMessageKey.MESSAGE_INVALID_ANSWER_REQUIRE_SELECT_SINGLE;

/**
 * <p>Custom tag without body for checking question selection state. Used for getting localised validation text feedback for 'question' page attribute of {@link SurveyQuestion} class.
 * Prints localised message, if question selection is invalid, otherwise does nothing.</p>
 * <p>Usage at JSP page: &lt;ct:question-validation-feedback/&gt;.</p>
 */
public class SurveyAttemptQuestionValidationFeedbackTag extends TagSupport {

    private static final String PAGE_ATTRIBUTE_QUESTION = "question";

    @Override
    public int doStartTag() throws JspException {

        try {
            JspWriter out = pageContext.getOut();
            SurveyQuestion question = (SurveyQuestion) pageContext.getAttribute(PAGE_ATTRIBUTE_QUESTION);
            Map<String, String> validationFeedback =
                    (Map<String, String>) pageContext.getRequest().getAttribute(REQUEST_ATTRIBUTE_FORM_INVALID);
            if (validationFeedback != null) {
                String fmtMessage = validationFeedback.get(PARAMETER_QUESTION_ID + question.getQuestionId());

                if (fmtMessage != null) {
                    String locale = (String) pageContext.getSession().getAttribute(SESSION_ATTRIBUTE_PARAMETER_LOCALISATION);
                    ResourceBundle resourceBundle = ResourceBundleManager.getInstance().getResourceBundle(locale);

                    if (fmtMessage.equals(MESSAGE_INVALID_ANSWER_REQUIRE_SELECT_MULTIPLE)) {
                        String questionCheckboxInvalid =
                                resourceBundle.getString(MESSAGE_INVALID_ANSWER_REQUIRE_SELECT_MULTIPLE);
                        out.write(questionCheckboxInvalid);
                    } else if (fmtMessage.equals(MESSAGE_INVALID_ANSWER_REQUIRE_SELECT_SINGLE)) {
                        String questionRadiobuttonInvalid =
                                resourceBundle.getString(MESSAGE_INVALID_ANSWER_REQUIRE_SELECT_SINGLE);
                        out.write(questionRadiobuttonInvalid);
                    }
                }
            }
        } catch (IOException e) {
            throw new JspTagException(e.getMessage());
        }

        return SKIP_BODY;
    }

    @Override
    public int doEndTag() {
        return EVAL_PAGE;
    }
}
