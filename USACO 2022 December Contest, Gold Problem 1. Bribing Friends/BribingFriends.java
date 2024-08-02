import java.util.*;

public class BribingFriends {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input n, a, b
        int n = scanner.nextInt();
        int a = scanner.nextInt();
        int b = scanner.nextInt();

        // DP arrays
        int[] dpmoney = new int[a + 1];
        int[] dpcones = new int[b + 1];

        // List to hold (p, c, x) values
        List<int[]> values = new ArrayList<>();

        // Read input (p, c, x)
        for (int i = 0; i < n; i++) {
            int p = scanner.nextInt();
            int c = scanner.nextInt();
            int x = scanner.nextInt();
            values.add(new int[]{p, c, x});
        }

        // Sort the values based on the third column (x)
        values.sort(Comparator.comparingInt(v -> v[2]));

        // Process values
        for (int[] item : values) {
            int p = item[0];
            int c = item[1];
            int x = item[2];

            // Update dpmoney for cones purchased with money
            for (int i = 0; i <= a - c; i++) {
                dpmoney[i] = Math.max(dpmoney[i], dpmoney[i + c] + p);
            }

            // Update dpmoney for cones purchased with cones
            for (int i = Math.max(0, a - c); i <= Math.min(a, a - c + (b / x)); i++) {
                int conesNeeded = (i - (a - c)) * x;
                dpmoney[i] = Math.max(dpmoney[i], dpcones[conesNeeded] + p);
            }

            // Update dpcones based on how many cones can be bought
            for (int i = 0; i <= b - x * c; i++) {
                dpcones[i] = Math.max(dpcones[i], dpcones[i + x * c] + p);
                dpmoney[a] = Math.max(dpmoney[a], dpcones[i]);
            }
        }

        // Output the result
        System.out.println(dpmoney[0]);

        scanner.close();
    }
}