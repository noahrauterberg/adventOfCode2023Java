import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day1 {
    public static void main(String[] args) {
        int sum = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            String line = reader.readLine();

            while (line != null) {
                sum += intFromLine(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(sum);
    }

    public static int intFromLine(String line) {
        char[] digits = new char[2];

        breakLabel1: for (int i = 0; i < line.length(); i++) {
            char cur = line.charAt(i);
            if (Character.isDigit(cur)) {
                digits[0] = cur;
                break;
            }
            for (int j = i + 3; j <= i + 5; j++) {
                char digit = digitSpelledOut(line, i, j);
                if (digit != '0') {
                    digits[0] = digit;
                    break breakLabel1;
                }
            }
        }

        breakLabel2: for (int i = line.length() - 1; i >= 0; i--) {
            char cur = line.charAt(i);
            if (Character.isDigit(cur)) {
                digits[1] = cur;
                break;
            }
            for (int j = i - 2; j >= i - 5; j--) {
                char digit = digitSpelledOut(line, j, i + 1);
                if (digit != '0') {
                    digits[1] = digit;
                    break breakLabel2;
                }
            }
        }

        String number = new String(digits);
        return Integer.valueOf(number);
    }

    public static char digitSpelledOut(String line, int start, int end) {
        if (start < 0 || end > line.length()) {
            return '0';
        }

        String subString = line.substring(start, end);
        switch (subString) {
            case "one":
                return '1';
            case "two":
                return '2';
            case "three":
                return '3';
            case "four":
                return '4';
            case "five":
                return '5';
            case "six":
                return '6';
            case "seven":
                return '7';
            case "eight":
                return '8';
            case "nine":
                return '9';
            default:
                return '0';
        }
    }
}