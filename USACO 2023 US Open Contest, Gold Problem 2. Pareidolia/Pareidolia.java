import java.util.*;
import java.io.*;


public class Pareidolia {

    static final String BESSIE = "bessie";
    static final int INF = 300_000_000;

    static class Pair {
        int first;
        int second;

        Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }

    static Pair opt(Pair a, Pair b) {
        if (a.first > b.first) {
            return a;
        }
        if (b.first > a.first) {
            return b;
        }
        return (a.second < b.second) ? a : b;
    }

    static Pair add(Pair a, Pair b) {
        return new Pair(a.first + b.first, a.second + b.second);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read the input string
        String s = scanner.nextLine();
        String[] input = scanner.nextLine().split(" ");
        int[] c = new int[input.length];

        // Parse integers from the input
        for (int i = 0; i < input.length; i++) {
            c[i] = Integer.parseInt(input[i]);
        }

        // Initialize the dp array
        Pair[][] dp = new Pair[s.length() + 1][7];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                dp[i][j] = new Pair(-1, INF);
            }
        }
        dp[0][0] = new Pair(0, 0);

        // Fill the dp array
        for (int k = 1; k <= s.length(); k++) {
            for (int j = 1; j < 7; j++) {
                if (s.charAt(k - 1) == BESSIE.charAt(j - 1)) {
                    dp[k][j] = opt(add(dp[k - 1][j], new Pair(0, c[k - 1])), dp[k - 1][j - 1]);
                } else {
                    dp[k][j] = add(dp[k - 1][j], new Pair(0, c[k - 1]));
                }
            }
            dp[k][0] = opt(dp[k - 1][0], add(dp[k][6], new Pair(1, 0)));
        }

        Pair result = dp[s.length()][0];
        System.out.println(result.first);
        System.out.println(result.second);

        scanner.close();
    }
}