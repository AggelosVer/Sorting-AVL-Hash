import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static Map<String, Integer> sumDeathsByRegion(List<Entry> dataList) {
        Map<String, Integer> deathsByRegion = new HashMap<>();
        for (Entry entry : dataList) {
            if (entry.getBirthDeath().equals("Deaths")) {
                deathsByRegion.put(entry.getRegion(),
                        deathsByRegion.getOrDefault(entry.getRegion(), 0) + entry.getCount());
            }
        }
        return deathsByRegion;
    }

    public static List<Entry> readFromFile(String filePath) {
        List<Entry> dataList = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines.subList(1, lines.size())) {
                String[] parts = line.split(",", 4);
                int period = Integer.parseInt(parts[0]);
                String birthDeath = parts[1];
                String region = parts[2].replace("\"", "");
                int count = Integer.parseInt(parts[3]);
                dataList.add(new Entry(period, birthDeath, region, count));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    public static List<Map.Entry<String, Integer>> heapSortRegions(List<Map.Entry<String, Integer>> entryList) {
        int n = entryList.size();
    
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(entryList, n, i);
        }
    
        for (int i = n - 1; i >= 0; i--) {
            Map.Entry<String, Integer> temp = entryList.get(0);
            entryList.set(0, entryList.get(i));
            entryList.set(i, temp);
    
            heapify(entryList, i, 0);
        }
    
        return entryList;
    }
    
    private static void heapify(List<Map.Entry<String, Integer>> entryList, int n, int i) {
        int largest = i; 
        int left = 2 * i + 1; 
        int right = 2 * i + 2; 
    
        if (left < n && entryList.get(left).getValue() > entryList.get(largest).getValue()) {
            largest = left;
        }
    
        if (right < n && entryList.get(right).getValue() > entryList.get(largest).getValue()) {
            largest = right;
        }
    
        if (largest != i) {
            Map.Entry<String, Integer> swap = entryList.get(i);
            entryList.set(i, entryList.get(largest));
            entryList.set(largest, swap);
    
            heapify(entryList, n, largest);
        }
    }
    
    public static List<Map.Entry<String, Integer>> countingSortRegions(List<Map.Entry<String, Integer>> entryList) {
        int max = findMax(entryList);
        int[] count = new int[max + 1];
        List<Map.Entry<String, Integer>> output = new ArrayList<>(entryList.size());
        for (int i = 0; i < entryList.size(); i++) {
            output.add(null); 
        }
        for (int i = 0; i <= max; i++) {
            count[i] = 0;
        }
        for (Map.Entry<String, Integer> entry : entryList) {
            count[entry.getValue()]++;
        }

        for (int i = 1; i <= max; i++) {
            count[i] += count[i - 1];
        }

        for (int i = entryList.size() - 1; i >= 0; i--) {
            Map.Entry<String, Integer> entry = entryList.get(i);
            output.set(count[entry.getValue()] - 1, entry);
            count[entry.getValue()]--;
        }

        return output;
    }

    private static int findMax(List<Map.Entry<String, Integer>> entryList) {
        int max = entryList.get(0).getValue();
        for (Map.Entry<String, Integer> entry : entryList) {
            if (entry.getValue() > max) {
                max = entry.getValue();
            }
        }
        return max;
    }

    public static void main(String[] args) {
        List<Entry> dataList = readFromFile("data.txt");
        Map<String, Integer> deathsByRegion = sumDeathsByRegion(dataList);
    
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(deathsByRegion.entrySet());
    
        Scanner scanner = new Scanner(System.in);
        System.out.print("Type 0 for HeapSort or 1 for CountingSort: ");
        String sort = scanner.nextLine();
        scanner.close();
    
        if (sort.equals("0")) {
            List<Map.Entry<String, Integer>> sortedList = heapSortRegions(entryList);
            for (Map.Entry<String, Integer> entry : sortedList) {
                System.out.println("Region: " + entry.getKey() + ", Total Deaths: " + entry.getValue());
            }
        } else if (sort.equals("1")) {
            List<Map.Entry<String, Integer>> sortedList = countingSortRegions(entryList);
            for (Map.Entry<String, Integer> entry : sortedList) {
                System.out.println("Region: " + entry.getKey() + ", Total Deaths: " + entry.getValue());
            }
        } else {
            System.out.println("Error");
        }
    }    
    
}