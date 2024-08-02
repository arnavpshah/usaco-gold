import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class StoneGame {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int numberOfPiles = Integer.parseInt(reader.readLine());
        Integer[] stonePiles = new Integer[numberOfPiles + 1];
        StringTokenizer token = new StringTokenizer(reader.readLine());
        for (int i = 0; i < numberOfPiles; i++) {
            stonePiles[i] = Integer.parseInt(token.nextToken());
        }
        stonePiles[numberOfPiles] = 0;
        Arrays.sort(stonePiles);
        int[] differenceCount = new int[1000001];
        int[] sumIndex = new int[1000001];
        for (int i = numberOfPiles; i >= 1; i -= 2) {
            differenceCount[stonePiles[i]]++;
            differenceCount[stonePiles[i - 1]]--;
            sumIndex[stonePiles[i]] += i;
            sumIndex[stonePiles[i - 1]] -= i;
        }
        long totalAnswer = 0;
        for (int j = 1000000; j > 0; j--) {
            differenceCount[j - 1] += differenceCount[j];
            sumIndex[j - 1] += sumIndex[j];
            int totalDiff = 0;
            int totalIndex = 0;
            for (int m = j; m <= 1000000; m += j) {
                totalDiff += differenceCount[m];
                totalIndex += sumIndex[m];
            }
            if (totalDiff == 1) {
                int high = numberOfPiles;
                int low = totalIndex;
                while (high > low) {
                    int midPoint = (high + low + 1) / 2;
                    if (stonePiles[midPoint] / j == stonePiles[totalIndex] / j) {
                        low = midPoint;
                    } else {
                        high = midPoint - 1;
                    }
                }
                totalAnswer += high - totalIndex + 1;
            }
        }
        System.out.println(totalAnswer);
    }
}