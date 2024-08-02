import java.io.*;
import java.util.*;

public class CustodialCleanup {

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder outputBuilder = new StringBuilder();

        int testCases = Integer.parseInt(bufferedReader.readLine());
        while (testCases-- > 0) {
            bufferedReader.readLine(); // We can ignore this line as it seems unused

            StringTokenizer st = new StringTokenizer(bufferedReader.readLine());
            int roomCount = Integer.parseInt(st.nextToken());
            int connectionCount = Integer.parseInt(st.nextToken());

            int[] roomTypes = new int[roomCount + 1];
            int[] startingKeys = new int[roomCount + 1];
            int[] targetKeys = new int[roomCount + 1];
            List<Integer>[] adjacencyList = new List[roomCount + 1];

            StringTokenizer roomTypeTokens = new StringTokenizer(bufferedReader.readLine());
            StringTokenizer startingKeyTokens = new StringTokenizer(bufferedReader.readLine());
            StringTokenizer targetKeyTokens = new StringTokenizer(bufferedReader.readLine());

            Set<Integer> directlyAccessibleRooms = new HashSet<>();

            for (int roomIndex = 1; roomIndex <= roomCount; roomIndex++) {
                roomTypes[roomIndex] = Integer.parseInt(roomTypeTokens.nextToken());
                startingKeys[roomIndex] = Integer.parseInt(startingKeyTokens.nextToken());
                targetKeys[roomIndex] = Integer.parseInt(targetKeyTokens.nextToken());
                adjacencyList[roomIndex] = new ArrayList<>();

                if (startingKeys[roomIndex] == targetKeys[roomIndex]) {
                    directlyAccessibleRooms.add(roomIndex);
                }
            }

            while (connectionCount-- > 0) {
                st = new StringTokenizer(bufferedReader.readLine());
                int roomA = Integer.parseInt(st.nextToken());
                int roomB = Integer.parseInt(st.nextToken());
                adjacencyList[roomA].add(roomB);
                adjacencyList[roomB].add(roomA);
            }

            Set<Integer> reachableFromStart = findAccessibleRooms(adjacencyList, roomTypes, startingKeys, null);
            Set<Integer> reachableFromTarget = findAccessibleRooms(adjacencyList, roomTypes, targetKeys, reachableFromStart);
            directlyAccessibleRooms.addAll(reachableFromTarget);

            boolean isAllRoomsAccessible = directlyAccessibleRooms.size() == roomCount;
            outputBuilder.append(isAllRoomsAccessible ? "YES" : "NO").append('\n');
        }
        System.out.print(outputBuilder);
    }

    static Set<Integer> findAccessibleRooms(List<Integer>[] graph, int[] colorCodes, int[] keys, Set<Integer> allowedRooms) {
        boolean[] keysAcquired = new boolean[graph.length];
        List<Integer>[] queuedRooms = new List[graph.length];

        for (int colorIndex = 1; colorIndex < graph.length; colorIndex++) {
            queuedRooms[colorIndex] = new ArrayList<>();
        }

        Stack<Integer> stack = new Stack<>();
        boolean[] visitedRooms = new boolean[graph.length];
        visitedRooms[1] = true;
        stack.push(1);
        Set<Integer> accessibleRooms = new HashSet<>();

        while (!stack.isEmpty()) {
            int currentRoom = stack.pop();
            accessibleRooms.add(currentRoom);

            if (!keysAcquired[keys[currentRoom]]) {
                keysAcquired[keys[currentRoom]] = true;
                stack.addAll(queuedRooms[keys[currentRoom]]);
            }

            for (int adjacentRoom : graph[currentRoom]) {
                if (!visitedRooms[adjacentRoom] && (allowedRooms == null || allowedRooms.contains(adjacentRoom))) {
                    visitedRooms[adjacentRoom] = true;
                    if (keysAcquired[colorCodes[adjacentRoom]] || (allowedRooms != null && keys[adjacentRoom] == colorCodes[adjacentRoom])) {
                        stack.push(adjacentRoom);
                    } else {
                        queuedRooms[colorCodes[adjacentRoom]].add(adjacentRoom);
                    }
                }
            }
        }

        return accessibleRooms;
    }
}