package com.zlatamigas.surveyplatform.util.tag;

import com.zlatamigas.surveyplatform.model.entity.SurveyQuestion;
import com.zlatamigas.surveyplatform.model.entity.SurveyQuestionAnswer;
import com.zlatamigas.surveyplatform.util.locale.ResourceBundleManager;
import org.apache.taglibs.standard.tag.common.core.Util;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.util.ResourceBundle;

import static com.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder.SESSION_ATTRIBUTE_PARAMETER_LOCALISATION;
import static com.zlatamigas.surveyplatform.util.locale.LocalisedMessageKey.*;

/**
 * <p>Custom tag with body. Used for visualisation question result in form of a table according to selection mode (single or multiple).</p>
 * <p>Usage at JSP page:</p>
 * <p>&lt;ct:survey-question-result question="${question}"&gt;&lt;/ct:survey-question-result&gt;, where attribute question - {@link SurveyQuestion} object.</p>
 */
public class ResultQuestionTableTag extends BodyTagSupport {

    private static final String TABLE_START_FORMAT = """
            <div class="table-custom">
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
                            <td class=\"col-answer multiline\">%s</td>
                            <td class=\"col-selected-count\">%s</td>
                        </tr>
            """;
    private static final String TABLE_END = """
                    </tbody>
                </table>
            </div>
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

                pageContext.getOut().write(String.format(TABLE_ROW_FORMAT,  writeEscapedXml(answer.getAnswer()), selectedCountStr));
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


    private static String writeEscapedXml(String str) throws IOException {

        StringBuilder resultStr = new StringBuilder();

        char[] buffer = str.toCharArray();
        int length = str.length();

        int start = 0;

        for(int i = 0; i < length; ++i) {
            char c = buffer[i];
            if (c <= '>') {
                char[] escaped = Util.specialCharactersRepresentation[c];
                if (escaped != null) {
                    if (start < i) {
                        resultStr.append(buffer, start, i - start);
                    }

                    for (char e: escaped) {
                        resultStr.append(e);
                    }
                    start = i + 1;
                }
            }
        }

        if (start < length) {
            resultStr.append(buffer, start, length - start);
        }

        return resultStr.toString();
    }

}
