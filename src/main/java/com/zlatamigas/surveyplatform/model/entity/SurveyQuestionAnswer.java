package com.zlatamigas.surveyplatform.model.entity;

public class SurveyQuestionAnswer implements Cloneable{

    private int questionAnswerId;
    private String answer;
    private int selectedCount;

    public SurveyQuestionAnswer() {
        questionAnswerId = 0;
        selectedCount = 0;
    }

    public int getQuestionAnswerId() {
        return questionAnswerId;
    }

    public void setQuestionAnswerId(int questionAnswerId) {
        this.questionAnswerId = questionAnswerId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getSelectedCount() {
        return selectedCount;
    }

    public void setSelectedCount(int selectedCount) {
        this.selectedCount = selectedCount;
    }

    public static class SurveyQuestionAnswerBuilder {
        private final SurveyQuestionAnswer surveyQuestionAnswer;

        public SurveyQuestionAnswerBuilder() {
            surveyQuestionAnswer = new SurveyQuestionAnswer();
        }

        public SurveyQuestionAnswerBuilder setQuestionAnswerId(int questionAnswerId) {
            surveyQuestionAnswer.setQuestionAnswerId(questionAnswerId);
            return this;
        }

        public SurveyQuestionAnswerBuilder setAnswer(String answer) {
            surveyQuestionAnswer.setAnswer(answer);
            return this;
        }

        public SurveyQuestionAnswerBuilder setSelectedCount(int selectedCount) {
            surveyQuestionAnswer.setSelectedCount(selectedCount);
            return this;
        }

        public SurveyQuestionAnswer getSurveyQuestionAnswer() {
            return surveyQuestionAnswer;
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
        SurveyQuestionAnswer that = (SurveyQuestionAnswer) o;
        return questionAnswerId == that.questionAnswerId
                && answer != null
                && answer.equals(that.answer)
                && selectedCount == that.selectedCount;
    }

    @Override
    public int hashCode() {
        int seed = 31;
        int hash = 1;

        hash = seed * hash + questionAnswerId;
        hash = seed * hash + (answer != null ? answer.hashCode() : 0);
        hash = seed * hash + selectedCount;

        return hash;
    }

    @Override
    public SurveyQuestionAnswer clone() {
        return new SurveyQuestionAnswerBuilder()
                .setQuestionAnswerId(questionAnswerId)
                .setAnswer(answer)
                .setSelectedCount(selectedCount)
                .getSurveyQuestionAnswer();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("QuestionAnswer{");
        sb.append("questionAnswerId=").append(questionAnswerId);
        sb.append(", answer='").append(answer).append('\'');
        sb.append(", selectedCount=").append(selectedCount);
        sb.append('}');
        return sb.toString();
    }
}
