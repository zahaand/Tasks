import java.util.Arrays;
import java.util.Scanner;

public class GuessWordByLetters {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String[] hiddenWordChars = getHiddenWordChars(scanner);

            int wordLength = hiddenWordChars.length;
            String[] guessedWordChars = getGuessedWordChars(wordLength);

            String guessedWord = guessLetter(scanner, hiddenWordChars, wordLength, guessedWordChars);
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

    private static String[] getGuessedWordChars(int wordLength) {
        String[] guessedWordChars = new String[wordLength];
        Arrays.fill(guessedWordChars, "#");
        System.out.println(String.join("", guessedWordChars));
        return guessedWordChars;
    }

    private static String  guessLetter(Scanner scanner, String[] hiddenWordChars, int wordLength, String[] guessedWordChars) {
        boolean isGuessed = false;
        while (!isGuessed) {
            System.out.println("Угадайте букву:");
            String guessedLetter = scanner.next();
            for (int i = 0; i < wordLength; i++) {
                if (hiddenWordChars[i].equals(guessedLetter)) {
                    guessedWordChars[i] = guessedLetter;
                }
            }
            System.out.println(String.join("", guessedWordChars));
            if (Arrays.equals(hiddenWordChars, guessedWordChars)) {
                isGuessed = true;
            }
        }
        return String.join("" ,guessedWordChars);
    }
}
