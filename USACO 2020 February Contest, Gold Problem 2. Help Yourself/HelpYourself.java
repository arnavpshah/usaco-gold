import java.io.*;
import java.util.*;

public class HelpYourself {

    private static final int MOD = (int) 1e9 + 7;
    private static int N;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("help.in"));
        PrintWriter pw = new PrintWriter(new FileWriter("help.out"));

        N = Integer.parseInt(br.readLine());
        List<Pair> v = new ArrayList<>(N);

        StringTokenizer st;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int first = Integer.parseInt(st.nextToken());
            int second = Integer.parseInt(st.nextToken());
            v.add(new Pair(first, second));
        }

        int[] over = new int[2 * N + 1];
        int[] po2 = new int[N];
        po2[0] = 1;
        for (int i = 1; i < N; i++) {
            po2[i] = 2 * po2[i - 1] % MOD;
        }

        for (Pair t : v) {
            over[t.first]++;
            over[t.second]--;
        }

        for (int i = 1; i <= 2 * N; i++) {
            over[i] += over[i - 1];
        }

        int ans = 0;
        for (Pair t : v) {
            ans = (ans + po2[N - 1 - over[t.first - 1]]) % MOD;
        }

        pw.println(ans);
        br.close();
        pw.close();
    }


    // Helper class to represent pairs of integers
    private static class Pair {
        int first;
        int second;

        Pair(int f, int s) {
            this.first = f;
            this.second = s;
        }
    }
}