package trivia;

public class Logger {
    public static void log(String format, Object... args) {
        System.out.println(String.format(format, args));
    }
}
