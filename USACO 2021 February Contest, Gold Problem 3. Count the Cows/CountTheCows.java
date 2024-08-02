import java.io.*;
import java.util.*;

public class CountTheCows {

    public static void main(String[] args) throws IOException {
        long[] powerOfThree = new long[39];
        powerOfThree[0] = 1;

        for (int exponent = 1; exponent <= 38; exponent++) {
            powerOfThree[exponent] = 3L * powerOfThree[exponent - 1];
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder resultBuilder = new StringBuilder();
        int testCases = Integer.parseInt(reader.readLine());

        for (int caseIndex = 1; caseIndex <= testCases; caseIndex++) {
            StringTokenizer token = new StringTokenizer(reader.readLine());
            long divisor = Long.parseLong(token.nextToken());
            long coordinateX = Long.parseLong(token.nextToken());
            long coordinateY = Long.parseLong(token.nextToken());

            long[][][][][] dpTable = new long[3][2][3][2][40];

            for (int stateX = 0; stateX < 2; stateX++) {
                for (int stateY = 0; stateY < 2; stateY++) {
                    dpTable[stateX][0][stateY][0][0] = 1;
                }
            }

            for (int exponent = 0; exponent <= 38; exponent++) {
                int limit = (int) ((divisor / powerOfThree[exponent]) % 3L);
                int digitX = (int) ((coordinateX / powerOfThree[exponent]) % 3L);
                int digitY = (int) ((coordinateY / powerOfThree[exponent]) % 3L);

                for (int carryX = 0; carryX < 2; carryX++) {
                    for (int currentDigit = 0; currentDigit < 3; currentDigit++) {
                        for (int carryY = 0; carryY < 2; carryY++) {
                            int nextCarryX = (digitX + currentDigit + carryX) / 3;
                            int newDigitX = (digitX + currentDigit + carryX) % 3;
                            int nextCarryY = (digitY + currentDigit + carryY) / 3;
                            int newDigitY = (digitY + currentDigit + carryY) % 3;

                            int compareResult;
                            if (currentDigit < limit) {
                                compareResult = 0;
                            } else if (currentDigit == limit) {
                                compareResult = 1;
                            } else {
                                compareResult = 2;
                            }

                            if (newDigitX % 2 == newDigitY % 2) {
                                for (int stateX = 0; stateX < 2; stateX++) {
                                    for (int stateY = 0; stateY < 2; stateY++) {
                                        dpTable[stateX][nextCarryX][stateY][nextCarryY][exponent + 1] +=
                                                dpTable[stateX == 1 ? compareResult : 0][carryX][stateY == 1 ? compareResult : 0][carryY][exponent];
                                    }
                                }
                            }
                        }
                    }
                }
            }
            resultBuilder.append(dpTable[1][0][1][0][39]).append('\n');
        }
        System.out.print(resultBuilder);
    }
}