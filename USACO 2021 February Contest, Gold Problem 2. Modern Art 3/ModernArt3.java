import java.util.Scanner;

public class ModernArt3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int N = scanner.nextInt();
        int[][] dp = new int[305][305];
        int[] a = new int[N];

        for (int i = 0; i < N; i++) {
            a[i] = scanner.nextInt();
        }

        for (int i = N - 1; i >= 0; i--) {
            for (int j = i + 1; j < N; j++) {
                if (a[i] == a[j]) { // draw segment from i to j
                    dp[i][j] = Math.max(dp[i][j], 1 + dp[i + 1][j - 1]);
                }
                for (int k = i + 1; k < j; k++) { // split at k
                    dp[i][j] = Math.max(dp[i][j], dp[i][k] + dp[k][j]);
                }
            }
        }

        System.out.println(N - dp[0][N - 1]);
        scanner.close();
    }
}