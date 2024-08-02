import java.io.*;
import java.util.*;

public class Delegation {

    private static final int MOD = (int) 1e9 + 7;
    private static final int MX = 100005;

    private static int N;
    private static int[] sub = new int[MX];
    private static List<Integer>[] adj = new ArrayList[MX];
    private static List<Integer>[] num = new ArrayList[MX];
    private static int[] cur = new int[MX];

    static {
        for (int i = 0; i < MX; i++) {
            adj[i] = new ArrayList<>();
            num[i] = new ArrayList<>();
        }
    }

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("deleg.in"));
        PrintWriter pw = new PrintWriter(new FileWriter("deleg.out"));

        N = Integer.parseInt(br.readLine());
        for (int i = 1; i < N; i++) {
            String[] edge = br.readLine().split(" ");
            int a = Integer.parseInt(edge[0]);
            int b = Integer.parseInt(edge[1]);
            adj[a].add(b);
            adj[b].add(a);
        }

        dfs(1, 0);

        for (int i = 1; i < N; i++) {
            if (ok(i)) {
                pw.print(1);
            } else {
                pw.print(0);
            }
        }

        pw.println();
        br.close();
        pw.close();
    }

    private static void dfs(int a, int b) {
        sub[a] = 1;
        for (int t : adj[a]) {
            if (t != b) {
                dfs(t, a);
                sub[a] += sub[t];
                num[a].add(sub[t]);
            }
        }
        if (sub[a] != N) {
            num[a].add(N - sub[a]);
        }
    }

    private static boolean ok(int K) {
        if ((N - 1) % K != 0) return false;
        for (int i = 0; i < K; i++) {
            cur[i] = 0;
        }
        for (int i = 1; i <= N; i++) {
            int cnt = 0;
            for (int t : num[i]) {
                int z = t % K;
                if (z == 0) continue;
                if (cur[K - z] > 0) {
                    cur[K - z]--;
                    cnt--;
                } else {
                    cur[z]++;
                    cnt++;
                }
            }
            if (cnt != 0) return false; // paths don't pair up
        }
        return true;
    }

}