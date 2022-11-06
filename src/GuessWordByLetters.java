import java.util.Arrays;
import java.util.Scanner;

public class GuessWordByLetters {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String[] hiddenWordChars = getHiddenWordChars(scanner);
            String[] guessedWordChars = getGuessedWordChars(hiddenWordChars);

            String guessedWord = guessLetters(scanner, hiddenWordChars, guessedWordChars);
            System.out.println("Поздравляю! Вы угадали слово: " + guessedWord);
        }
    }

    private static String[] getHiddenWordChars(Scanner scanner) {
        System.out.println("Загадайте слово:");
        String hiddenWord = scanner.next();
        String[] hiddenWordChars;
        hiddenWordChars = hiddenWord.split("");
        return hiddenWordChars;
    }

    private static String[] getGuessedWordChars(String[] hiddenWordChars) {
        String[] guessedWordChars = new String[hiddenWordChars.length];
        Arrays.fill(guessedWordChars, "#");
        System.out.println(String.join("", guessedWordChars));
        return guessedWordChars;
    }

    private static String guessLetters(Scanner scanner, String[] hiddenWordChars, String[] guessedWordChars) {
        do {
            System.out.println("Угадайте букву:");
            String guessedLetter = scanner.next();
            for (int i = 0; i < hiddenWordChars.length; i++) {
                if (hiddenWordChars[i].equals(guessedLetter)) {
                    guessedWordChars[i] = guessedLetter;
                }
            }
            System.out.println(String.join("", guessedWordChars));
        } while (!Arrays.equals(hiddenWordChars, guessedWordChars));
        return String.join("" ,guessedWordChars);
    }
}
