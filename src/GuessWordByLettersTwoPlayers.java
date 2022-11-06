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
        pointsPlayer1 += getResult(scanner, randomWord, hiddenWord);

        if (isGuessed) return;

        System.out.println("Игрок 2 угадайте букву или слово целиком: ");
        pointsPlayer2 += getResult(scanner, randomWord, hiddenWord);
    }

    private static int getResult(Scanner scanner, String randomWord, char[] hiddenWord) {
        String answer = scanner.next();
        return checkAnswer(answer, randomWord, hiddenWord);
    }

    private static int checkAnswer(String answer, String randomWord, char[] hiddenWord) {
        char[] answerLetters = answer.toCharArray();
        char[] randomWordLetters = randomWord.toCharArray();
        int totalPoints = 0;

        /*
        Если в ответе не 1 буква и его длина не равна длине загаданного слова:
        Тогда ответ точно неверный
         */
        if (answerLetters.length != 1 && answerLetters.length != randomWordLetters.length) {
            return 0;
        }

        /*
         Если угаданная буква равна букве загаданного слова и если она еще не была угадана:
         Добавляем её вместо символа # и считаем за нее очки
         */
        for (char answerLetter : answerLetters) {
            for (int j = 0; j < randomWordLetters.length; j++) {
                if (answerLetter == randomWordLetters[j] && hiddenWord[j] == '#') {
                    hiddenWord[j] = answerLetter;
                    totalPoints += getPoints(answerLetter);
                }
            }
        }
        isGuessed = Arrays.equals(randomWordLetters, hiddenWord);

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
        return allLines.get(RANDOM.nextInt(allLines.size()));
    }
}