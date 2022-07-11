package epam.zlatamigas.surveyplatform.util.tag;

import epam.zlatamigas.surveyplatform.model.entity.SurveyQuestion;
import epam.zlatamigas.surveyplatform.model.entity.SurveyQuestionAnswer;
import epam.zlatamigas.surveyplatform.util.locale.ResourceBundleManager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.util.ResourceBundle;

import static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.SESSION_ATTRIBUTE_PARAMETER_LOCALISATION;
import static epam.zlatamigas.surveyplatform.util.locale.LocalisedMessageKey.*;

/**
 * <p>Custom tag with body. Used for visualisation question result in form of a table according to selection mode (single or multiple).</p>
 * <p>Usage at JSP page:</p>
 * <p>&lt;sa:survey-question-result question="${question}"&gt;&lt;/sa:survey-question-result&gt;, where attribute question - {@link SurveyQuestion} object.</p>
 */
public class ResultTableTag extends BodyTagSupport {

    private static final String TABLE_START_FORMAT = """
            <table class=\"table\">
                <thead>
                    <tr>
                        <th scope=\"col\">%s</th>
                        <th scope=\"col\">%s</th>
                    </tr>
                </thead>
                <tbody>
            """;
    private static final String TABLE_ROW_FORMAT = """
                    <tr>
                        <td class=\"col-answer\">%s</td>
                        <td class=\"col-selected-count\">%s</td>
                    </tr>
            """;
    private static final String TABLE_END = """
                </tbody>
            </table>
            """;
    private static final String PERCENT_FORMAT = "%.2f";
    private static final String PERCENTAGE = "%";

    private SurveyQuestion question;
    private int sumSelected;
    private int itemPos;

    private ResourceBundle resourceBundle;

    public void setQuestion(SurveyQuestion question) {
        this.question = question;
        sumSelected = question.getAnswers().stream()
                .mapToInt(SurveyQuestionAnswer::getSelectedCount)
                .sum();
        itemPos = 0;

        String locale = (String) pageContext.getSession().getAttribute(SESSION_ATTRIBUTE_PARAMETER_LOCALISATION);
        resourceBundle = ResourceBundleManager.getInstance().getResourceBundle(locale);
    }

    @Override
    public int doStartTag() throws JspException {

        try{
            String colName = resourceBundle.getString(question.isSelectMultiple() ? TABLE_TH_MULTIPLE : TABLE_TH_SINGLE);
            pageContext.getOut().write(
                    String.format(TABLE_START_FORMAT, resourceBundle.getString(TABLE_TH_ANSWER), colName));
        } catch (IOException e) {
            throw  new JspTagException(e.getMessage());
        }

        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doAfterBody() throws JspException {

        if(itemPos < question.getAnswers().size()){
            SurveyQuestionAnswer answer = question.getAnswers().get(itemPos);
            itemPos++;
            try{
                String selectedCountStr = question.isSelectMultiple()
                        ? String.valueOf(answer.getSelectedCount())
                        : String.format(PERCENT_FORMAT, (
                                sumSelected != 0
                                        ? (double)answer.getSelectedCount() / sumSelected * 100
                                        : sumSelected
                        )) + PERCENTAGE;
                pageContext.getOut().write(String.format(TABLE_ROW_FORMAT, answer.getAnswer(), selectedCountStr));
            } catch (IOException e){
                throw new JspTagException(e.getMessage());
            }
            return EVAL_BODY_AGAIN;
        } else {
            return SKIP_BODY;
        }
    }


    @Override
    public int doEndTag() throws JspException {
        try {
            pageContext.getOut().write(TABLE_END);
        } catch (IOException e) {
            throw new JspTagException(e.getMessage());
        }
        return SKIP_BODY;
    }
}
