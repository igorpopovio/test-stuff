package trivia;

import java.util.Scanner;

import static trivia.Logger.log;

public class HumanPlayer extends Player {
    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public String provideAnswerFor(Question question) {
        log("Please provide an answer for: \"%s\"", question.getText());
        return new Scanner(System.in).nextLine();
    }
}
