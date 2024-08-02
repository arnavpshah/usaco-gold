import java.io.*;
import java.util.*;

public class LightsOff {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokenParser = new StringTokenizer(reader.readLine());
        int testCases = Integer.parseInt(tokenParser.nextToken());
        int numLights = Integer.parseInt(tokenParser.nextToken());

        int[][] transformationPads = new int[3 * numLights + 1][numLights];
        for (int step = 1; step <= 3 * numLights; step++) {
            for (int lightIndex = 0; lightIndex < numLights; lightIndex++) {
                transformationPads[step][lightIndex] = (1 << lightIndex) ^ transformationPads[step - 1][(lightIndex + 1) % numLights];
            }
        }

        boolean[][] dpTable = new boolean[3 * numLights + 1][1 << numLights];
        dpTable[0][0] = true;
        for (int round = 1; round <= 3 * numLights; round++) {
            for (int mask = 0; mask < (1 << numLights); mask++) {
                for (int lightIndex = 0; lightIndex < numLights; lightIndex++) {
                    if (dpTable[round - 1][mask ^ transformationPads[round][lightIndex]]) {
                        dpTable[round][mask] = true;
                        break;
                    }
                }
            }
        }

        StringBuilder outputBuilder = new StringBuilder();
        for (; testCases > 0; testCases--) {
            tokenParser = new StringTokenizer(reader.readLine());
            int initialState = stringToMask(tokenParser.nextToken());
            int togglePattern = stringToMask(tokenParser.nextToken());

            int currentPad = 0;
            int result = -1;
            for (int round = 0; round <= 3 * numLights; round++) {
                int maskedState = initialState ^ currentPad;
                if (dpTable[round][maskedState]) {
                    result = round;
                    break;
                }
                currentPad <<= 1;
                currentPad ^= togglePattern;
                if (currentPad >= 1 << numLights) {
                    currentPad ^= 1 << numLights;
                    currentPad ^= 1;
                }
            }

            outputBuilder.append(result).append('\n');
        }
        System.out.print(outputBuilder);
    }

    public static int stringToMask(String binaryString) {
        char[] reversedCharArray = new char[binaryString.length()];
        for (int index = 0; index < binaryString.length(); index++) {
            reversedCharArray[index] = binaryString.charAt(binaryString.length() - 1 - index);
        }
        return Integer.parseInt(new String(reversedCharArray), 2);
    }
}