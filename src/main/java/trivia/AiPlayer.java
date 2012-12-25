package trivia;

import java.util.Random;

public class AiPlayer extends Player {
    private Random random;

    public AiPlayer(String name) {
        this(name, new Random());
    }

    public AiPlayer(String name, Random random) {
        super(name);
        this.random = random;
    }

    @Override
    public String provideAnswerFor(Question question) {
        if (shouldAnswerCorrectly())
            return question.getCorrectAnswer();
        else
            return "an answer that is very likely wrong";
    }

    private boolean shouldAnswerCorrectly() {
        return random.nextInt(9) != 7;
    }
}
