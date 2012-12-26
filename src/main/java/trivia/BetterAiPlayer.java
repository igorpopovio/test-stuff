package trivia;

import java.util.Random;

public class BetterAiPlayer extends Player {
    private final Random random;
    private final int winningChance;

    public BetterAiPlayer(
            String name,
            Random random,
            int winningChance) {
        super(name);
        this.random = random;
        this.winningChance = winningChance;
    }

    @Override
    public String provideAnswerFor(Question question) {
        if (shouldProvideCorrectAnswer())
            return question.getCorrectAnswer();
        return "a wrong answer";
    }

    private boolean shouldProvideCorrectAnswer() {
        return random.nextInt(100 / winningChance) == 0;
    }
}
