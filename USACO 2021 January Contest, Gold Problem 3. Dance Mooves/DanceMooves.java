import java.io.*;
import java.util.*;

public class DanceMooves {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        int totalCows = Integer.parseInt(tokenizer.nextToken());
        long totalSwaps = Integer.parseInt(tokenizer.nextToken());
        long maxTime = Long.parseLong(tokenizer.nextToken());
        int[] cowPositions = new int[totalCows + 1];
        List<ViewRecord>[] viewRecords = new List[totalCows + 1];

        for (int cowIndex = 1; cowIndex <= totalCows; cowIndex++) {
            cowPositions[cowIndex] = cowIndex;
            viewRecords[cowIndex] = new ArrayList<>();
            viewRecords[cowIndex].add(new ViewRecord(cowIndex, 0));
        }

        for (long swapTime = 1; swapTime <= totalSwaps; swapTime++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int cowA = Integer.parseInt(tokenizer.nextToken());
            int cowB = Integer.parseInt(tokenizer.nextToken());
            int tempPositionA = cowPositions[cowA];
            int tempPositionB = cowPositions[cowB];
            cowPositions[cowA] = tempPositionB;
            cowPositions[cowB] = tempPositionA;
            viewRecords[cowPositions[cowA]].add(new ViewRecord(cowA, swapTime));
            viewRecords[cowPositions[cowB]].add(new ViewRecord(cowB, swapTime));
        }

        int[] result = new int[totalCows + 1];
        for (int startIndex = 1; startIndex <= totalCows; startIndex++) {
            if (cowPositions[startIndex] != 0) {
                List<Integer> cycleList = new ArrayList<>();
                int currentIndex = startIndex;

                while (cowPositions[currentIndex] != 0) {
                    cycleList.add(currentIndex);
                    currentIndex = cowPositions[currentIndex];
                    cowPositions[cycleList.get(cycleList.size() - 1)] = 0;
                }

                Map<Integer, List<TimeInterval>> timeIntervalsMap = new HashMap<>();
                for (int cycleIndex = 0; cycleIndex < cycleList.size(); cycleIndex++) {
                    for (ViewRecord view : viewRecords[cycleList.get(cycleIndex)]) {
                        if (maxTime >= view.timestamp) {
                            int coveredPositions = (int) Math.min(cycleList.size(), ((maxTime - view.timestamp) / totalSwaps) + 1L);
                            if (!timeIntervalsMap.containsKey(view.position)) {
                                timeIntervalsMap.put(view.position, new ArrayList<>());
                            }
                            timeIntervalsMap.get(view.position).add(new TimeInterval(cycleIndex, Math.min(cycleList.size(), cycleIndex + coveredPositions)));
                            if (cycleIndex + coveredPositions > cycleList.size()) {
                                timeIntervalsMap.get(view.position).add(new TimeInterval(0, (cycleIndex + coveredPositions) % cycleList.size()));
                            }
                        }
                    }
                }

                int[] countArray = new int[cycleList.size() + 1];
                for (List<TimeInterval> intervals : timeIntervalsMap.values()) {
                    intervals.sort(Comparator.comparingInt(interval -> interval.start));
                    int previousEnd = 0;
                    for (TimeInterval interval : intervals) {
                        countArray[Math.max(previousEnd, interval.start)]++;
                        previousEnd = Math.max(previousEnd, interval.end);
                        countArray[previousEnd]--;
                    }
                }

                for (int cycleIndex = 0; cycleIndex < cycleList.size(); cycleIndex++) {
                    result[cycleList.get(cycleIndex)] = countArray[cycleIndex];
                    countArray[cycleIndex + 1] += countArray[cycleIndex];
                }
            }
        }

        StringBuilder output = new StringBuilder();
        for (int index = 1; index <= totalCows; index++) {
            output.append(result[index]).append('\n');
        }
        System.out.print(output);
    }

    static class ViewRecord {
        final int position;
        final long timestamp;

        ViewRecord(int position, long timestamp) {
            this.position = position;
            this.timestamp = timestamp;
        }
    }

    static class TimeInterval {
        final int start;
        final int end;

        TimeInterval(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}