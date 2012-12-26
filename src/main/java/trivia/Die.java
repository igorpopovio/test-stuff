package trivia;

import java.util.Random;

public class Die {
    private Random random;

    public Die(Random random) {
        this.random = random;
    }

    public int roll() {
        return random.nextInt(5) + 1;
    }
}
