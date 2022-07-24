package epam.zlatamigas.surveyplatform.util.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static epam.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder.SESSION_ATTRIBUTE_PARAMETER_LOCALISATION;

/**
 * <p>Custom tag without body for representing survey start and close date and time.</p>
 * <p>Usage at JSP page: &lt;ct:local-date-time datetime="${datetime}"/&gt;, where ${datetime} -
 * {@link LocalDateTime} object</p>
 */
public class LocalDateTimeTag extends TagSupport {

    private static final String NULL_DATE_TIME = "???";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm, dd MMMM yyyy ");

    private LocalDateTime datetime;

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    @Override
    public int doStartTag() throws JspException {

        try {
            DateTimeFormatter localisedDateTimeFormatter = DATE_TIME_FORMATTER;

            String locale = (String) pageContext.getSession().getAttribute(SESSION_ATTRIBUTE_PARAMETER_LOCALISATION);
            if (locale != null && !locale.isBlank()) {
                localisedDateTimeFormatter = DATE_TIME_FORMATTER.withLocale(new Locale(locale, locale));
            }

            pageContext.getOut().write(datetime != null ? datetime.format(localisedDateTimeFormatter) : NULL_DATE_TIME);

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
