import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class FindAndReplace {
    public static final long MAXIMUM_VALUE = 1000000000000000000L;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokenParser = new StringTokenizer(reader.readLine());
        long lowerBound = Long.parseLong(tokenParser.nextToken());
        long upperBound = Long.parseLong(tokenParser.nextToken());
        int numberOfOperations = Integer.parseInt(tokenParser.nextToken());
        List<Operation> operationList = new ArrayList<>();
        for (; numberOfOperations > 0; numberOfOperations--) {
            tokenParser = new StringTokenizer(reader.readLine());
            char sourceChar = tokenParser.nextToken().charAt(0);
            char[] targetChars = tokenParser.nextToken().toCharArray();
            operationList.add(new Operation(sourceChar, targetChars));
        }

        BigString[] currentStrings = new BigString[26];
        for (char character = 'a'; character <= 'z'; character++) {
            currentStrings[character - 'a'] = new BigString(true, character, null, 1);
        }
        Collections.reverse(operationList);
        for (Operation operation : operationList) {
            if (operation.targetChars.length == 1) {
                currentStrings[operation.sourceChar - 'a'] = currentStrings[operation.targetChars[0] - 'a'];
            } else {
                BigString[] segmentedStrings = new BigString[operation.targetChars.length];
                long totalLength = 0;
                for (int index = 0; index < segmentedStrings.length; index++) {
                    segmentedStrings[index] = currentStrings[operation.targetChars[index] - 'a'];
                    totalLength += segmentedStrings[index].length;
                    totalLength = Math.min(totalLength, MAXIMUM_VALUE);
                }
                currentStrings[operation.sourceChar - 'a'] = new BigString(false, '\0', segmentedStrings, totalLength);
            }
        }

        StringBuilder resultBuilder = new StringBuilder();
        currentStrings[0].append(lowerBound, upperBound, resultBuilder);
        System.out.println(resultBuilder);
    }

    static class Operation {
        final char sourceChar;
        final char[] targetChars;

        Operation(char sourceChar, char[] targetChars) {
            this.sourceChar = sourceChar;
            this.targetChars = targetChars;
        }
    }

    static class BigString {
        final boolean isSingleCharacter;
        final char character;
        final BigString[] segmentedElements;
        final long length;

        BigString(boolean isSingleCharacter, char character, BigString[] segmentedElements, long length) {
            this.isSingleCharacter = isSingleCharacter;
            this.character = character;
            this.segmentedElements = segmentedElements;
            this.length = length;
        }

        void append(long start, long end, StringBuilder builder) {
            start = Math.max(start, 1);
            end = Math.min(end, length);
            if (start <= end) {
                if (isSingleCharacter) {
                    builder.append(character);
                } else {
                    long currentLength = 0;
                    for (BigString segment : segmentedElements) {
                        segment.append(start - currentLength, end - currentLength, builder);
                        currentLength += segment.length;
                        currentLength = Math.min(currentLength, MAXIMUM_VALUE);
                    }
                }
            }
        }
    }
}