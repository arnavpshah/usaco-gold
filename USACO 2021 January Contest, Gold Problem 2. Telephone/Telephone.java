import java.io.*;
import java.util.*;

public class Telephone {

    public static void main(String[] arguments) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer token = new StringTokenizer(reader.readLine());
        int totalEntries = Integer.parseInt(token.nextToken());
        int totalBreeds = Integer.parseInt(token.nextToken());

        int[] breedValues = new int[totalEntries + 1];
        token = new StringTokenizer(reader.readLine());
        for (int index = 1; index <= totalEntries; index++) {
            breedValues[index] = Integer.parseInt(token.nextToken());
        }

        boolean[][] adjacencyMatrix = new boolean[totalBreeds + 1][totalBreeds + 1];
        for (int breedIndex = 1; breedIndex <= totalBreeds; breedIndex++) {
            String inputLine = " " + reader.readLine();
            for (int connectionIndex = 1; connectionIndex <= totalBreeds; connectionIndex++) {
                adjacencyMatrix[breedIndex][connectionIndex] = inputLine.charAt(connectionIndex) == '1';
            }
            adjacencyMatrix[breedIndex][0] = adjacencyMatrix[breedIndex][breedValues[totalEntries]];
        }

        breedValues[totalEntries] = 0;
        int[][] distanceArray = new int[totalBreeds + 1][totalEntries + 1];
        for (int breedIndex = 0; breedIndex <= totalBreeds; breedIndex++) {
            Arrays.fill(distanceArray[breedIndex], -1);
        }

        distanceArray[breedValues[1]][1] = 0;
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(breedValues[1]);
        queue.add(1);

        while (!queue.isEmpty()) {
            int currentBreed = queue.remove();
            int currentPosition = queue.remove();

            if (currentPosition > 1 && distanceArray[currentBreed][currentPosition - 1] == -1) {
                distanceArray[currentBreed][currentPosition - 1] = distanceArray[currentBreed][currentPosition] + 1;
                queue.add(currentBreed);
                queue.add(currentPosition - 1);
            }

            if (currentPosition < totalEntries && distanceArray[currentBreed][currentPosition + 1] == -1) {
                distanceArray[currentBreed][currentPosition + 1] = distanceArray[currentBreed][currentPosition] + 1;
                queue.add(currentBreed);
                queue.add(currentPosition + 1);
            }

            if (adjacencyMatrix[currentBreed][breedValues[currentPosition]] && distanceArray[breedValues[currentPosition]][currentPosition] == -1) {
                distanceArray[breedValues[currentPosition]][currentPosition] = distanceArray[currentBreed][currentPosition];
                queue.addFirst(currentPosition);
                queue.addFirst(breedValues[currentPosition]);
            }
        }
        System.out.println(distanceArray[0][totalEntries]);
    }
}