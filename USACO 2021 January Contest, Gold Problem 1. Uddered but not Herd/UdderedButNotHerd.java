import java.io.*;
import java.util.*;

public class UdderedButNotHerd {

    public static void main(String[] input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String inputString = reader.readLine();

        Map<Character, Integer> characterIndex = new HashMap<>();

        for (char currentChar : inputString.toCharArray()) {
            if (!characterIndex.containsKey(currentChar)) {
                characterIndex.put(currentChar, characterIndex.size());
            }
        }

        int totalUniqueChars = characterIndex.size();
        int[][] connectivityMatrix = new int[totalUniqueChars][totalUniqueChars];

        for (int position = 1; position < inputString.length(); position++) {
            connectivityMatrix[characterIndex.get(inputString.charAt(position - 1))][characterIndex.get(inputString.charAt(position))]++;
        }

        int[][] cumulativeSums = new int[totalUniqueChars][1 << totalUniqueChars];
        int[] dynamicProgrammingArray = new int[1 << totalUniqueChars];
        dynamicProgrammingArray[0] = 1;

        for (int bitmask = 1; bitmask < (1 << totalUniqueChars); bitmask++) {
            dynamicProgrammingArray[bitmask] = inputString.length();
            int firstSetBit = 0;

            while ((bitmask & (1 << firstSetBit)) == 0) {
                firstSetBit++;
            }

            for (int charIdx = 0; charIdx < totalUniqueChars; charIdx++) {
                cumulativeSums[charIdx][bitmask] = cumulativeSums[charIdx][bitmask - (1 << firstSetBit)] + connectivityMatrix[charIdx][firstSetBit];

                if ((bitmask & (1 << charIdx)) != 0) {
                    dynamicProgrammingArray[bitmask] = Math.min(dynamicProgrammingArray[bitmask], dynamicProgrammingArray[bitmask - (1 << charIdx)] + cumulativeSums[charIdx][bitmask]);
                }
            }
        }

        System.out.println(dynamicProgrammingArray[(1 << totalUniqueChars) - 1]);
    }
}