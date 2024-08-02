import java.io.*;
import java.util.*;

public class Timeline {

    private static final int MX = 100005;
    private static int N, M, C;
    private static int[] S = new int[MX];
    private static int[] in = new int[MX];
    private static boolean[] vis = new boolean[MX];
    private static List<int[]>[] adj = new ArrayList[MX];
    private static Queue<Integer> queue = new LinkedList<>();

    static {
        for (int i = 0; i < MX; i++) {
            adj[i] = new ArrayList<>();
        }
    }

    public static void main(String[] args) throws IOException {
        setIO("timeline");
        BufferedReader br = new BufferedReader(new FileReader("timeline.in"));
        PrintWriter pw = new PrintWriter(new FileWriter("timeline.out"));

        String[] firstLine = br.readLine().split(" ");
        N = Integer.parseInt(firstLine[0]);
        M = Integer.parseInt(firstLine[1]);
        C = Integer.parseInt(firstLine[2]);

        String[] SInput = br.readLine().split(" ");
        for (int i = 1; i <= N; i++) {
            S[i] = Integer.parseInt(SInput[i - 1]);
        }

        for (int i = 0; i < C; i++) {
            String[] edgeInfo = br.readLine().split(" ");
            int a = Integer.parseInt(edgeInfo[0]);
            int b = Integer.parseInt(edgeInfo[1]);
            int x = Integer.parseInt(edgeInfo[2]);
            adj[a].add(new int[]{b, x});
            in[b]++;
        }

        for (int i = 1; i <= N; i++) {
            if (in[i] == 0) {
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            int x = queue.poll(); // process x in order of topological sort
            vis[x] = true;
            assert S[x] <= M;

            for (int[] t : adj[x]) {
                S[t[0]] = Math.max(S[t[0]], S[x] + t[1]);
                if (--in[t[0]] == 0) {
                    queue.offer(t[0]);
                }
            }
        }

        for (int i = 1; i <= N; i++) {
            assert vis[i];
            pw.println(S[i]);
        }

        br.close();
        pw.close();
    }

    private static void setIO(String s) {
        // This function is no longer needed in Java as we handle file reading directly
    }
}