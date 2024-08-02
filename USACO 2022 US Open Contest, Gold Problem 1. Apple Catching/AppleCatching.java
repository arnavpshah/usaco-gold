import java.io.*;
import java.util.*;

public class AppleCatching {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int count = Integer.parseInt(reader.readLine());
        List<Item> livestock = new ArrayList<>();
        List<Item> fruits = new ArrayList<>();

        for (int index = 1; index <= count; index++) {
            StringTokenizer parser = new StringTokenizer(reader.readLine());
            int type = Integer.parseInt(parser.nextToken());
            int duration = Integer.parseInt(parser.nextToken());
            int position = Integer.parseInt(parser.nextToken());
            int volume = Integer.parseInt(parser.nextToken());

            int adjustedY = duration - position;
            int adjustedX = duration + position;

            (type == 1 ? livestock : fruits).add(new Item(volume, adjustedY, adjustedX));
        }

        Collections.sort(livestock, Comparator.comparingInt(animal -> animal.adjustedY));
        Collections.sort(fruits, Comparator.comparingInt(fruit -> fruit.adjustedY));

        TreeSet<Item> activeLivestock = new TreeSet<>((first, second) -> {
            if (first.adjustedX != second.adjustedX) {
                return first.adjustedX - second.adjustedX;
            } else {
                return first.adjustedY - second.adjustedY;
            }
        });

        int livestockIndex = 0;
        int totalCaught = 0;

        for (Item fruit : fruits) {
            while (livestockIndex < livestock.size() && livestock.get(livestockIndex).adjustedY <= fruit.adjustedY) {
                activeLivestock.add(livestock.get(livestockIndex));
                livestockIndex++;
            }

            int remainingVolume = fruit.volume;
            while (remainingVolume > 0 && !activeLivestock.isEmpty() && activeLivestock.first().adjustedX <= fruit.adjustedX) {
                Item currentLivestock = activeLivestock.floor(new Item(0, 1000000000, fruit.adjustedX));
                activeLivestock.remove(currentLivestock);

                int harvested = Math.min(remainingVolume, currentLivestock.volume);
                totalCaught += harvested;
                remainingVolume -= harvested;

                if (harvested < currentLivestock.volume) {
                    activeLivestock.add(new Item(currentLivestock.volume - harvested, currentLivestock.adjustedY, currentLivestock.adjustedX));
                }
            }
        }

        System.out.println(totalCaught);
    }

    static class Item {
        final int volume;
        final int adjustedY;
        final int adjustedX;

        Item(int volume, int adjustedY, int adjustedX) {
            this.volume = volume;
            this.adjustedY = adjustedY;
            this.adjustedX = adjustedX;
        }
    }
}