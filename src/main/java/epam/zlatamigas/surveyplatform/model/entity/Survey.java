package epam.zlatamigas.surveyplatform.model.entity;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Survey extends AbstractEntity implements Cloneable {

    private int surveyId;
    private String name;
    private String description;
    private SurveyStatus status;
    private Theme theme;
    private User creator;
    private List<SurveyQuestion> questions;
    private LocalDateTime startDateTime;
    private LocalDateTime closeDateTime;

    public Survey() {
        surveyId = 0;
        questions = new ArrayList<>();
    }

    public int getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SurveyStatus getStatus() {
        return status;
    }

    public void setStatus(SurveyStatus status) {
        this.status = status;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public List<SurveyQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<SurveyQuestion> questions) {
        this.questions = questions;
    }

    public boolean addQuestion(SurveyQuestion surveyQuestion) {
        return questions.add(surveyQuestion);
    }

    public boolean removeQuestion(SurveyQuestion surveyQuestion) {
        return questions.remove(surveyQuestion);
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getCloseDateTime() {
        return closeDateTime;
    }

    public void setCloseDateTime(LocalDateTime closeDateTime) {
        this.closeDateTime = closeDateTime;
    }

    public static class SurveyBuilder {
        private final Survey survey;

        public SurveyBuilder() {
            survey = new Survey();
        }

        public SurveyBuilder setSurveyId(int surveyId) {
            survey.setSurveyId(surveyId);
            return this;
        }

        public SurveyBuilder setName(String name) {
            survey.setName(name);
            return this;
        }

        public SurveyBuilder setDescription(String description) {
            survey.setDescription(description);
            return this;
        }

        public SurveyBuilder setStatus(SurveyStatus status) {
            survey.setStatus(status);
            return this;
        }

        public SurveyBuilder setTheme(Theme theme) {
            survey.setTheme(theme);
            return this;
        }

        public SurveyBuilder setCreator(User creator) {
            survey.setCreator(creator);
            return this;
        }

        public SurveyBuilder setQuestions(List<SurveyQuestion> questions) {
            survey.setQuestions(questions);
            return this;
        }

        public SurveyBuilder setStartDateTime(LocalDateTime startDateTime) {
            survey.setStartDateTime(startDateTime);
            return this;
        }

        public SurveyBuilder setCloseDateTime(LocalDateTime closeDateTime) {
            survey.setCloseDateTime(closeDateTime);
            return this;
        }

        public Survey getSurvey() {
            return survey;
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
        Survey survey = (Survey) o;
        return surveyId == survey.surveyId
                && name != null
                && name.equals(survey.name)
                && description != null
                && description.equals(survey.description)
                && theme != null
                && theme.equals(survey.theme)
                && creator != null
                && creator.equals(survey.creator)
                && status == survey.status
                && startDateTime != null
                && startDateTime.equals(survey.startDateTime)
                && closeDateTime != null
                && closeDateTime.equals(survey.closeDateTime);
    }

    @Override
    public int hashCode() {

        int seed = 31;
        int hash = 1;

        hash = seed * hash + surveyId;
        hash = seed * hash + (name != null ? name.hashCode() : 0);
        hash = seed * hash + (description != null ? description.hashCode() : 0);
        hash = seed * hash + (status != null ? status.hashCode() : 0);
        hash = seed * hash + (theme != null ? theme.hashCode() : 0);
        hash = seed * hash + (creator != null ? creator.hashCode() : 0);
        hash = seed * hash + (startDateTime != null ? startDateTime.hashCode() : 0);
        hash = seed * hash + (closeDateTime != null ? closeDateTime.hashCode() : 0);
        for (SurveyQuestion question : questions) {
            hash = seed * hash + (question != null ? question.hashCode() : 0);
        }

        return hash;
    }

    @Override
    public Survey clone() {

        List<SurveyQuestion> surveyQuestions = questions.stream()
                .map(SurveyQuestion::clone)
                .collect(Collectors.toList());

        Survey survey = new Survey.SurveyBuilder()
                .setSurveyId(surveyId)
                .setName(name)
                .setDescription(description)
                .setStatus(status)
                .setTheme(theme)
                .setCreator(creator)
                .setQuestions(surveyQuestions)
                .setStartDateTime(startDateTime)
                .setCloseDateTime(closeDateTime)
                .getSurvey();

        return survey;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Survey{");
        sb.append("surveyId=").append(surveyId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", status=").append(status);
        sb.append(", theme=").append(theme);
        sb.append(", creator=").append(creator);
        sb.append(", start=").append(startDateTime);
        sb.append(", close=").append(closeDateTime);
        sb.append(", questions:\n").append(questions.stream().map(q -> '\t' + q.toString() + '\n').collect(Collectors.joining()));
        sb.append('}');
        return sb.toString();
    }
}
