import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class DisjointSet {
    private int[] p, sz;

    public DisjointSet(int n) {
        p = new int[n];
        sz = new int[n];
        Arrays.fill(p, -1);
        Arrays.fill(sz, 1);
    }

    public int find(int x) {
        return p[x] < 0 ? x : (p[x] = find(p[x]));
    }

    public int getsz(int x) {
        return sz[find(x)];
    }

    public boolean merge(int x, int y) {
        x = find(x);
        y = find(y);
        if (x == y) return false;
        p[x] = y;
        sz[y] += sz[x];
        return true;
    }
}

public class StrongestFriendshipGroup {
    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        List<List<Integer>> edges = new ArrayList<>(n);
        int[] edeg = new int[n];
        for (int i = 0; i < n; i++) {
            edges.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            int a = scanner.nextInt() - 1;
            int b = scanner.nextInt() - 1;
            edeg[a]++;
            edeg[b]++;
            edges.get(a).add(b);
            edges.get(b).add(a);
        }
        int ret = 0;
        boolean[] deleted = new boolean[n];
        int[] active = new int[n];
        for (int i = 0; i < n; i++) {
            active[i] = i;
        }
        for (int mindeg = 1; mindeg * mindeg <= m; mindeg++) {
            DisjointSet dsu = new DisjointSet(n);
            for (int i : active) {
                for (int j : edges.get(i)) {
                    if (!deleted[j] && dsu.merge(i, j)) {
                        ret = Math.max(ret, dsu.getsz(i) * mindeg);
                    }
                }
            }
            List<Integer> nactive = new ArrayList<>();
            List<Integer> q = new ArrayList<>();
            for (int i : active) {
                if (edeg[i] == mindeg) {
                    q.add(i);
                }
            }
            while (!q.isEmpty()) {
                int i = q.remove(q.size() - 1);
                if (deleted[i]) continue;
                deleted[i] = true;
                for (int j : edges.get(i)) {
                    if (--edeg[j] <= mindeg) {
                        q.add(j);
                    }
                }
                edges.get(i).clear();
            }
            for (int i : active) {
                if (edeg[i] > mindeg) {
                    nactive.add(i);
                }
            }
            active = nactive.stream().mapToInt(Integer::intValue).toArray();
        }
        System.out.println(ret);
        scanner.close();
    }
}

