import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainFileStringsMatcher {
    static final Path INPUT = Path.of("/Users/zahaand/Desktop/input.txt");
    static final Path OUTPUT = Path.of("/Users/zahaand/Desktop/output.txt");

    public static void main(String[] args) {
        try {
            List<String> allStringsFromFile = Files.readAllLines(INPUT, StandardCharsets.UTF_8)
                    .stream().filter(s -> !Objects.equals(s, "")).collect(Collectors.toList());
            Files.write(OUTPUT, getMatchedList(allStringsFromFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> getMatchedList(List<String> allStringsFromFile) {
        int firstListSize = Integer.parseInt(allStringsFromFile.get(0));

        List<String> firstValuesList = allStringsFromFile.stream().skip(1).limit(firstListSize).toList();
        List<String> secondValuesList = allStringsFromFile.stream().skip(1 + firstListSize + 1).toList();
        List<String> matchedList = new ArrayList<>();

        List<String> allValuesToRemoveList = new ArrayList<>(Stream.concat(firstValuesList.stream(), secondValuesList.stream()).toList());

        if (firstValuesList.size() == 1 && secondValuesList.size() == 1) {
            matchedList.add(firstValuesList.get(0) + ":" + secondValuesList.get(0));
            return matchedList;
        }

        for (String firstListString : firstValuesList) {
            boolean isMatchFound = false;
            for (String string : firstListString.split(" ")) {
                if (!isMatchFound) {
                    for (String secondListString : secondValuesList) {
                        if (secondListString.contains(string)) {
                            matchedList.add(firstListString + ":" + secondListString);
                            allValuesToRemoveList.remove(firstListString);
                            allValuesToRemoveList.remove(secondListString);
                            isMatchFound = true;
                            break;
                        }
                    }
                }
            }

            if (!isMatchFound) {
                matchedList.add(firstListString + ":?");
                allValuesToRemoveList.remove(firstListString);
            }
        }

        for (String string : allValuesToRemoveList) {
            matchedList.add(string + ":?");
        }

        return matchedList;
    }
}

