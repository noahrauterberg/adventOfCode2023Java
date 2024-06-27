import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class Day4 {
	public static final HashMap<Integer, Integer> totalCards = new HashMap<>();

	public static void main(String[] args) {
		int sum = 0;
		int curCard = 1;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(args[0]));
			String line = reader.readLine();
			while (line != null) {

				int matches = points(line);

				int numCurCard = 1;
				if (totalCards.containsKey(curCard)) {
					numCurCard += totalCards.get(curCard);
				}

				for (int i = 1; i < matches + 1; i++) {
					if (totalCards.containsKey(curCard + i)) {
						int curSum = totalCards.get(curCard + i);
						totalCards.replace(curCard + i, curSum + numCurCard);
					} else {
						totalCards.put(curCard + i, numCurCard);
					}
				}
				line = reader.readLine();
				curCard++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 1; i < curCard; i++) {
			if (totalCards.containsKey(i)) {
				sum += totalCards.get(i);
			}
			sum++;
		}
		System.out.println(sum);
	}

	public static int points(String line) {
		// initialize with -1 for 4.1
		int matches = 0;
		String[] arr = line.split("Card\\s+\\d+:\\s+");
		String[] cards = arr[1].split("\\|\s+");

		String[] winningStr = cards[0].split("\\s+");
		HashSet<String> winning = new HashSet<>();

		for (String cur : winningStr) {
			winning.add(cur);
		}

		String[] cardStr = cards[1].split(" ");
		for (String cur : cardStr) {
			if (winning.contains(cur)) {
				matches++;
			}
		}

		return matches;

		/*
		 * Solution for 4.1:
		 */
		// if (matches == -1)
		// return 0;
		// return (int) Math.pow(2, matches);
	}
}
