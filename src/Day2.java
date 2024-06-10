import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2 {
    public static final int red = 12;
    public static final int green = 13;
    public static final int blue = 14;
    public static Pattern numberPattern = Pattern.compile("Game \\d+");
    public static Pattern redPattern = Pattern.compile("\\d+ red");
    public static Pattern greenPattern = Pattern.compile("\\d+ green");
    public static Pattern bluePattern = Pattern.compile("\\d+ blue");

    public static void main(String[] args) {
        int sum = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            String line = reader.readLine();

            while (line != null) {
                sum += gamePower(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(sum);
    }

    public static int gamePower(String line) {
        int redCubes = findMatch(line, redPattern);
        int greenCubes = findMatch(line, greenPattern);
        int blueCubes = findMatch(line, bluePattern);

        return redCubes * greenCubes * blueCubes;
    }

    public static int isGamePossible(String line) {
        int gameNumber = findMatch(line, numberPattern);

        int redCubes = findMatch(line, redPattern);
        int greenCubes = findMatch(line, greenPattern);
        int blueCubes = findMatch(line, bluePattern);

        return (redCubes <= red && greenCubes <= green && blueCubes <= blue) ? gameNumber : 0;
    }

    public static int findMatch(String input, Pattern pattern) {
        Matcher matcher = pattern.matcher(input);
        int results = matcher.results()
                .map(MatchResult::group)
                .map(elem -> extractNumber(elem)).max(Integer::compare).orElse(0);

        return results;
    }

    public static int extractNumber(String input) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return Integer.valueOf(matcher.group());
        }
        return 0;
    }
}
