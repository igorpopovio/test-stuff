package trivia;

public abstract class Player {
    private String name;
    private int coins;

    public Player(String name) {
        this.name = name;
    }

    public abstract String provideAnswerFor(Question question);

    public int getCoins() {
        return coins;
    }

    public void giveCoins(int coins) {
        this.coins += coins;
    }

    @Override
    public String toString() {
        return name;
    }
}
