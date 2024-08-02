import java.io.*;
import java.util.*;

public class BalancingATree {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        int testCaseCount = Integer.parseInt(tokenizer.nextToken());
        boolean constructionRequired = tokenizer.nextToken().equals("1");

        while (testCaseCount > 0) {
            --testCaseCount;
            tokenizer = new StringTokenizer(reader.readLine());
            int nodeCount = Integer.parseInt(tokenizer.nextToken());
            int[] parentNodes = new int[nodeCount + 1];
            tokenizer = new StringTokenizer(reader.readLine());
            for (int i = 2; i <= nodeCount; i++) {
                parentNodes[i] = Integer.parseInt(tokenizer.nextToken());
            }
            int[] leftLimits = new int[nodeCount + 1];
            int[] rightLimits = new int[nodeCount + 1];
            int minimumRight = Integer.MAX_VALUE;
            int maximumLeft = 0;

            for (int i = 1; i <= nodeCount; i++) {
                tokenizer = new StringTokenizer(reader.readLine());
                leftLimits[i] = Integer.parseInt(tokenizer.nextToken());
                rightLimits[i] = Integer.parseInt(tokenizer.nextToken());
                minimumRight = Math.min(minimumRight, rightLimits[i]);
                maximumLeft = Math.max(maximumLeft, leftLimits[i]);
            }

            int maxDifference = 0;
            StringJoiner resultJoiner = new StringJoiner(" ");
            int[] minChoices = new int[nodeCount + 1];
            int[] maxChoices = new int[nodeCount + 1];

            for (int i = 1; i <= nodeCount; i++) {
                int selectedValue = Math.min(rightLimits[i], Math.max(leftLimits[i], (minimumRight + maximumLeft) / 2));
                minChoices[i] = i == 1 ? selectedValue : Math.min(minChoices[parentNodes[i]], selectedValue);
                maxChoices[i] = i == 1 ? selectedValue : Math.max(maxChoices[parentNodes[i]], selectedValue);
                maxDifference = Math.max(maxDifference, maxChoices[i] - minChoices[i]);
                resultJoiner.add(String.valueOf(selectedValue));
            }

            System.out.println(maxDifference);
            if (constructionRequired) {
                System.out.println(resultJoiner);
            }
        }
    }
}