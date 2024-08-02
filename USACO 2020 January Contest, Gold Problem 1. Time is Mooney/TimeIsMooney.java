import java.util.*;
import java.io.*;

public class TimeIsMooney {
    static final int MAXN = 1005;
    static final int MAXT = 1005;

    static long n, m, c;
    static long[] value = new long[MAXN];
    static long[][] dp = new long[2][MAXN];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("time.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("time.out")));

        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Long.parseLong(st.nextToken());
        m = Long.parseLong(st.nextToken());
        c = Long.parseLong(st.nextToken());

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            value[i] = Long.parseLong(st.nextToken());
        }

        List<int[]> edges = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            edges.add(new int[] {a, b});
        }

        long max_profit = 0;
        for (int i = 0; i < 2; i++) {
            Arrays.fill(dp[i], -1);
        }
        dp[0][1] = 0;

        for (int t = 1; t < MAXT; t++) {
            int p = t % 2;
            Arrays.fill(dp[p], -1);
            for (int[] e : edges) {
                int a = e[0];
                int b = e[1];
                if (dp[1 - p][a] >= 0) {
                    dp[p][b] = Math.max(dp[p][b], dp[1 - p][a] + value[b]);
                }
            }
            max_profit = Math.max(max_profit, dp[p][1] - c * t * t);
        }

        pw.println(max_profit);
        pw.close();
    }
}