package epam.zlatamigas.surveyplatform.model.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SurveyQuestion {

    private int questionId;
    private String formulation;
    private boolean selectMultiple;
    private List<SurveyQuestionAnswer> answers;

    public SurveyQuestion() {
        questionId = 0;
        selectMultiple = false;
        answers = new ArrayList<>();
    }

    public SurveyQuestion(String formulation, boolean selectMultiple) {
        this(0, formulation, selectMultiple, new ArrayList<>());
    }

    public SurveyQuestion(String formulation, boolean selectMultiple, List<SurveyQuestionAnswer> answers) {
        this(0, formulation, selectMultiple, answers);
    }

    public SurveyQuestion(int questionId, String formulation, boolean selectMultiple, List<SurveyQuestionAnswer> answers) {
        this.questionId = questionId;
        this.formulation = formulation;
        this.selectMultiple = selectMultiple;
        this.answers = answers;
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
