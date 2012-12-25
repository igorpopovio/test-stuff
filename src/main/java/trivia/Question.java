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
    public String toString() {
        return text;
    }
}
