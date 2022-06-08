package epam.zlatamigas.surveyplatform.model.entity;

import java.util.Date;

public class SurveyUserAttempt {

    private int surveyAttemptId;
    private Date finishedDate;
    private User user;
    private Survey survey;

    public SurveyUserAttempt() {
        surveyAttemptId = 0;
        user = new User();
        survey = new Survey();
    }

    public int getSurveyAttemptId() {
        return surveyAttemptId;
    }

    public void setSurveyAttemptId(int surveyAttemptId) {
        this.surveyAttemptId = surveyAttemptId;
    }

    public Date getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(Date finishedDate) {
        this.finishedDate = finishedDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public static class SurveyUserAttemptBuilder {
        private final SurveyUserAttempt surveyUserAttempt;

        public SurveyUserAttemptBuilder() {
            surveyUserAttempt = new SurveyUserAttempt();
        }


        public SurveyUserAttemptBuilder setSurveyAttemptId(int surveyAttemptId) {
            surveyUserAttempt.setSurveyAttemptId(surveyAttemptId);
            return this;
        }

        public SurveyUserAttemptBuilder setFinishedDate(Date finishedDate) {
            surveyUserAttempt.setFinishedDate(finishedDate);
            return this;
        }

        public SurveyUserAttemptBuilder setUser(User user) {
            surveyUserAttempt.setUser(user);
            return this;
        }

        public SurveyUserAttemptBuilder setSurvey(Survey survey) {
            surveyUserAttempt.setSurvey(survey);
            return this;
        }

        public SurveyUserAttempt getSurveyUserAttempt() {
            return surveyUserAttempt;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SurveyUserAttempt that = (SurveyUserAttempt) o;
        return surveyAttemptId == that.surveyAttemptId
                && finishedDate != null
                && finishedDate.equals(that.finishedDate)
                && user != null
                && user.equals(that.user)
                && survey != null
                && survey.equals(that.survey);
    }

    @Override
    public int hashCode() {

        int seed = 31;
        int hash = 1;

        hash = seed * hash + surveyAttemptId;
        hash = seed * hash + (finishedDate != null ? finishedDate.hashCode() : 0);
        hash = seed * hash + (user != null ? user.hashCode() : 0);
        hash = seed * hash + (survey != null ? survey.hashCode() : 0);

        return hash;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SurveyUserAttempt{");
        sb.append("surveyAttemptId=").append(surveyAttemptId);
        sb.append(", finishedDate=").append(finishedDate);
        sb.append(", user=").append(user);
        sb.append(", survey=").append(survey);
        sb.append('}');
        return sb.toString();
    }
}
