import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

public class Day3 {
    public static final Set<Character> digits = Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
    public static String[] lines;

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            lines = reader.lines().toArray(String[]::new);
            int sum = sumGearRatios();

            System.out.println(sum);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int sumGearRatios() {
        int sum = 0;
        for (int i = 0; i < lines.length; i++) {
            String curLine = lines[i];
            for (int j = 0; j < curLine.length(); j++) {
                char curChar = curLine.charAt(j);
                if (curChar != '*') {
                    continue;
                }
                sum += gearPartNumbers(i, j);
            }
        }
        return sum;
    }

    public static int gearPartNumbers(int lineIndex, int gearIndex) {
        String curLine = lines[lineIndex];
        int[] gears = new int[2];
        int numbersFound = 0;

        if (gearIndex > 0 && digits.contains(curLine.charAt(gearIndex - 1))) {
            int i = gearIndex - 2;
            while (i >= 0 && digits.contains(curLine.charAt(i))) {
                i--;
            }
            String num = i < 0 ? curLine.substring(0, gearIndex) : curLine.substring(i + 1, gearIndex);
            if (numbersFound > 1)
                return 0;
            gears[numbersFound] = Integer.valueOf(num);
            numbersFound++;
        }

        if (gearIndex < curLine.length() - 1 && digits.contains(curLine.charAt(gearIndex + 1))) {
            int i = gearIndex + 1;
            while (i < curLine.length() && digits.contains(curLine.charAt(i))) {
                i++;
            }
            String num = i == curLine.length() ? curLine.substring(gearIndex + 1) : curLine.substring(gearIndex + 1, i);
            if (numbersFound > 1)
                return 0;
            gears[numbersFound] = Integer.valueOf(num);
            numbersFound++;
        }

        if (lineIndex > 0) {
            int[] above = numbersBetween(lines[lineIndex - 1], gearIndex - 1, gearIndex + 1);
            if (above != null) {
                for (int i : above) {
                    if (i == 0)
                        continue;
                    if (numbersFound > 1)
                        return 0;
                    gears[numbersFound] = i;
                    numbersFound++;
                }
            }
        }

        if (lineIndex < lines.length - 1) {
            int[] below = numbersBetween(lines[lineIndex + 1], gearIndex - 1, gearIndex + 1);
            if (below != null) {
                for (int i : below) {
                    if (i == 0)
                        continue;
                    if (numbersFound > 1)
                        return 0;
                    gears[numbersFound] = i;
                    numbersFound++;
                }
            }
        }
        return gears[0] * gears[1];
    }

    public static int[] numbersBetween(String line, int begin, int end) {
        int numbersFound = 0;
        int[] ret = new int[2];

        begin = begin < 0 ? 0 : begin;
        for (int i = begin; i <= end && i < line.length(); i++) {
            if (digits.contains(line.charAt(i))) {
                int beginNum = i;
                while (beginNum >= 0 && digits.contains(line.charAt(beginNum))) {
                    beginNum--;
                }
                int endNum = i;
                while (endNum < line.length() && digits.contains(line.charAt(endNum))) {
                    endNum++;
                }
                String num;
                if (beginNum < 0) {
                    // since there won't be numbers stretching all the way from
                    // the left to the right, at most one of beginNum and endNum
                    // will be out of bounds but check anyways
                    num = endNum >= line.length() ? line.substring(0) : line.substring(0, endNum);
                } else if (endNum >= line.length()) {
                    num = beginNum < 0 ? line.substring(0) : line.substring(beginNum + 1);
                } else {
                    num = line.substring(beginNum + 1, endNum);
                }

                ret[numbersFound] = Integer.valueOf(num);
                numbersFound++;
                i = endNum;
            }
        }
        return numbersFound == 0 ? null : ret;
    }

    public static int sumPartNumbers() {
        int sum = 0;
        for (int i = 0; i < lines.length; i++) {
            String curLine = lines[i];
            for (int j = 0; j < curLine.length(); j++) {
                char curChar = curLine.charAt(j);
                if (!digits.contains(curChar)) {
                    continue;
                }

                int begin = j;
                int end = j + 1;
                while (end < curLine.length() && digits.contains(curLine.charAt(end))) {
                    end++;
                }

                String num;
                if (end == curLine.length()) {
                    num = curLine.substring(begin);
                    if (!isConnected(i, begin, end)) {
                        break;
                    }
                } else {
                    num = curLine.substring(begin, end);
                    if (!isConnected(i, begin, end - 1)) {
                        j = end;
                        continue;
                    }
                }

                sum += Integer.valueOf(num);
                j = end;
            }
        }
        return sum;
    }

    public static boolean isConnected(int lineIndex, int begin, int end) {
        String curLine = lines[lineIndex];
        if (begin > 0 && curLine.charAt(begin - 1) != '.') {
            return true;
        }
        if (end < curLine.length() && curLine.charAt(end + 1) != '.') {
            return true;
        }
        if (lineIndex > 0 && symbolBetween(lines[lineIndex - 1], begin - 1, end + 1)) {
            return true;
        }
        if (lineIndex < lines.length - 1 && symbolBetween(lines[lineIndex + 1], begin - 1, end + 1)) {
            return true;
        }

        return false;
    }

    public static boolean symbolBetween(String line, int begin, int end) {
        begin = begin < 0 ? 0 : begin;
        for (int i = begin; i <= end && i < line.length(); i++) {
            if (line.charAt(i) != '.') {
                return true;
            }
        }
        return false;
    }
}
