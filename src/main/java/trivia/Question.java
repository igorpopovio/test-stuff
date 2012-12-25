package trivia;

public class Question {
    private String category;
    private String text;
    private String correctAnswer;

    public Question(String category, String text, String correctAnswer) {
        this.category = category;
        this.text = text;
        this.correctAnswer = correctAnswer;
    }

    public String getCategory() {
        return category;
    }

    public String getText() {
        return text;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public boolean isCorrectAnswer(String answer) {
        return correctAnswer.equals(answer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        if (!category.equals(question.category)) return false;
        if (!text.equals(question.text)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = category.hashCode();
        result = 31 * result + text.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return text;
    }
}
