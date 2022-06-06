package epam.zlatamigas.surveyplatform.model.entity;

public class SurveyQuestionAnswer {
    private int questionAnswerId;
    private String answer;
    private int selectedCount;

    public SurveyQuestionAnswer() {
        questionAnswerId = 0;
        selectedCount = 0;
    }

    public SurveyQuestionAnswer(String answer) {
        this();
        this.answer = answer;
    }

    public SurveyQuestionAnswer(String answer, int selectedCount) {
        this(0, answer, selectedCount);
    }

    public SurveyQuestionAnswer(int questionAnswerId, String answer, int selectedCount) {
        this.questionAnswerId = questionAnswerId;
        this.answer = answer;
        this.selectedCount = selectedCount;
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
    public String toString() {
        final StringBuilder sb = new StringBuilder("QuestionAnswer{");
        sb.append("questionAnswerId=").append(questionAnswerId);
        sb.append(", answer='").append(answer).append('\'');
        sb.append(", selectedCount=").append(selectedCount);
        sb.append('}');
        return sb.toString();
    }
}
