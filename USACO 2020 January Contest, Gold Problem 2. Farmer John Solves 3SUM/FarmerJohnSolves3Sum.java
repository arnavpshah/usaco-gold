import java.io.*;
import java.util.*;

public class FarmerJohnSolves3Sum {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("threesum.in"));
        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("threesum.out")));

        String[] inputLine = reader.readLine().split(" ");
        int numberOfElements = Integer.parseInt(inputLine[0]);
        int queryCount = Integer.parseInt(inputLine[1]);

        inputLine = reader.readLine().split(" ");
        int[] elements = new int[numberOfElements];
        long[][] results = new long[numberOfElements][numberOfElements];

        for (int index = 0; index < numberOfElements; ++index) {
            elements[index] = Integer.parseInt(inputLine[index]);
        }

        int[] counts = new int[2000001];

        for (int i = numberOfElements - 1; i >= 0; --i) {
            for (int j = i + 1; j < numberOfElements; ++j) {
                int targetIndex = 1000000 - elements[i] - elements[j];
                if (targetIndex >= 0 && targetIndex <= 2000000) {
                    results[i][j] = counts[targetIndex];
                }
                counts[1000000 + elements[j]]++;
            }
            for (int j = i + 1; j < numberOfElements; ++j) {
                counts[1000000 + elements[j]]--;
            }
        }

        for (int i = numberOfElements - 1; i >= 0; --i) {
            for (int j = i + 1; j < numberOfElements; ++j) {
                results[i][j] += results[i + 1][j] + results[i][j - 1] - results[i + 1][j - 1];
            }
        }

        for (int i = 0; i < queryCount; ++i) {
            inputLine = reader.readLine().split(" ");
            int queryStart = Integer.parseInt(inputLine[0]);
            int queryEnd = Integer.parseInt(inputLine[1]);
            writer.println(results[queryStart - 1][queryEnd - 1]);
        }

        writer.close();
    }
}