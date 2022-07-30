package com.zlatamigas.surveyplatform.model.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SurveyQuestion implements Cloneable {

    private int questionId;
    private String formulation;
    private boolean selectMultiple;
    private List<SurveyQuestionAnswer> answers;

    public SurveyQuestion() {
        questionId = 0;
        selectMultiple = false;
        answers = new ArrayList<>();
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getFormulation() {
        return formulation;
    }

    public void setFormulation(String formulation) {
        this.formulation = formulation;
    }

    public boolean isSelectMultiple() {
        return selectMultiple;
    }

    public void setSelectMultiple(boolean selectMultiple) {
        this.selectMultiple = selectMultiple;
    }

    public List<SurveyQuestionAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<SurveyQuestionAnswer> answers) {
        this.answers = answers;
    }

    public boolean addAnswer(SurveyQuestionAnswer answer) {
        return answers.add(answer);
    }

    public boolean removeAnswer(SurveyQuestionAnswer answer) {
        return answers.remove(answer);
    }

    public static class SurveyQuestionBuilder {
        private final SurveyQuestion surveyQuestion;

        public SurveyQuestionBuilder() {
            surveyQuestion = new SurveyQuestion();
        }

        public SurveyQuestionBuilder setQuestionId(int questionId) {
            surveyQuestion.setQuestionId(questionId);
            return this;
        }

        public SurveyQuestionBuilder setFormulation(String formulation) {
            surveyQuestion.setFormulation(formulation);
            return this;
        }

        public SurveyQuestionBuilder setSelectMultiple(boolean selectMultiple) {
            surveyQuestion.setSelectMultiple(selectMultiple);
            return this;
        }

        public SurveyQuestionBuilder setAnswers(List<SurveyQuestionAnswer> answers) {
            surveyQuestion.setAnswers(answers);
            return this;
        }

        public SurveyQuestion getSurveyQuestion() {
            return surveyQuestion;
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
        SurveyQuestion surveyQuestion = (SurveyQuestion) o;
        return questionId == surveyQuestion.questionId
                && formulation != null
                && formulation.equals(surveyQuestion.formulation)
                && selectMultiple == surveyQuestion.selectMultiple
                && answers != null
                && answers.equals(surveyQuestion.answers);
    }

    @Override
    public int hashCode() {
        int seed = 31;
        int hash = 1;

        hash = seed * hash + questionId;
        hash = seed * hash + (formulation != null ? formulation.hashCode() : 0);
        hash = seed * hash + (selectMultiple ? 1 : 0);
        hash = seed * hash + (answers != null ? answers.hashCode() : 0);

        return hash;
    }

    @Override
    public SurveyQuestion clone() {

        List<SurveyQuestionAnswer> surveyQuestionAnswers = answers.stream()
                .map(SurveyQuestionAnswer::clone)
                .collect(Collectors.toList());

        SurveyQuestion question = new SurveyQuestionBuilder()
                .setQuestionId(questionId)
                .setFormulation(formulation)
                .setSelectMultiple(selectMultiple)
                .setAnswers(surveyQuestionAnswers)
                .getSurveyQuestion();

        return question;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SurveyQuestion{");
        sb.append("questionId=").append(questionId);
        sb.append(", formulation='").append(formulation).append('\'');
        sb.append(", selectMultiple=").append(selectMultiple);
        sb.append(", answers:\n").append(answers.stream().map(answer -> "\t\t" + answer.toString() + '\n').collect(Collectors.joining()));
        sb.append("\t}");
        return sb.toString();
    }
}
