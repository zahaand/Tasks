import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class GuessWordByLettersTwoPlayers {
    private static final Path INPUT = Path.of("/Users/zahaand/Desktop/input.txt");
    private static final Random RANDOM = new Random();
    private static int pointsPlayer1 = 0;
    private static int pointsPlayer2 = 0;
    private static boolean isGuessed = false;

    public static void main(String[] args) throws IOException {
        String randomWord = getRandomWordFromFile();
        System.out.println("Слово загадано: " + randomWord);

        char[] hiddenWord = getHiddenWord(randomWord);
        Stream.of(hiddenWord).forEach(System.out::print);
        System.out.println();

        try (Scanner scanner = new Scanner(System.in)) {
            do {
                guessRandomWord(scanner, randomWord, hiddenWord);
            } while (!isGuessed);
        }

        System.out.println("Игра закончена!");
        System.out.println("Баллов у Игрока 1: " + pointsPlayer1);
        System.out.println("Баллов у Игрока 2: " + pointsPlayer2);
        System.out.println("Побеждает " + getWinner());
    }

    private static String getWinner() {
        return pointsPlayer1 > pointsPlayer2 ? "Игрок 1" : "Игрок 2";
    }

    private static void guessRandomWord(Scanner scanner, String randomWord, char[] hiddenWord) {
        System.out.println("Игрок 1 угадайте букву или слово целиком: ");
        String answerPlayer1 = scanner.next();
        pointsPlayer1 += getResult(answerPlayer1, randomWord, hiddenWord);

        if (isGuessed) return;

        System.out.println("Игрок 2 угадайте букву или слово целиком: ");
        String answerPlayer2 = scanner.next();
        pointsPlayer2 += getResult(answerPlayer2, randomWord, hiddenWord);
    }

    private static int getResult(String answer, String randomWord, char[] hiddenWord) {
        int points = 0;

        if (answer.isEmpty()) {
            System.out.println("Ответ не должен быть пустым");
        } else {
            return checkAnswer(answer, randomWord, hiddenWord);
        }

        return points;
    }

    private static int checkAnswer(String answer, String randomWord, char[] hiddenWord) {
        char[] answerLetters = answer.toCharArray();
        char[] wordLetters = randomWord.toCharArray();
        int totalPoints = 0;

        /*
         Если угаданная буква равна букве загаданного слова и если она еще не была угадана:
         Добавляем её вместо символа # и считаем за нее очки
         */
        for (char answerLetter : answerLetters) {
            for (int j = 0; j < wordLetters.length; j++) {
                if (answerLetter == wordLetters[j] && hiddenWord[j] == '#') {
                    hiddenWord[j] = answerLetter;
                    totalPoints += getPoints(answerLetter);
                }
            }
        }
        isGuessed = Arrays.equals(wordLetters, hiddenWord);

        if (!isGuessed) {
            Stream.of(hiddenWord).forEach(System.out::print);
            System.out.println();
        }

        return totalPoints;
    }

    private static int getPoints(char answer) {
        return switch (answer) {
            case 'а', 'о', 'и', 'е', 'ё', 'э', 'ы', 'у', 'ю', 'я' -> 1;
            default -> 2;
        };
    }

    private static char[] getHiddenWord(String randomWord) {
        char[] hiddenWord = new char[randomWord.length()];
        Arrays.fill(hiddenWord, '#');
        return hiddenWord;
    }

    private static String getRandomWordFromFile() throws IOException {
        List<String> allLines = Files.readAllLines(INPUT);
        return allLines.get(GuessWordByLettersTwoPlayers.RANDOM.nextInt(allLines.size()));
    }
}
