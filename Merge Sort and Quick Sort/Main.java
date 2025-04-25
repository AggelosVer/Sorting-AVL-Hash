import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {


    public static Map<String, Integer> sumBirthsByRegion(List<entry> dataList) {
            
            Map<String, Integer> birthsByRegion = new HashMap<>();

            for (entry entry : dataList) {
                if (entry.getBirth_Death().equals("Births")) {
                    if (birthsByRegion.containsKey(entry.getRegion())) {
                        birthsByRegion.put(entry.getRegion(), birthsByRegion.get(entry.getRegion()) + entry.getCount());
                    } else {
                        birthsByRegion.put(entry.getRegion(), entry.getCount());
                    }
                }
            } 
        return birthsByRegion;
    }

    public static List<entry> readFromFile(String filePath) {
            List<entry> dataList = new ArrayList<>();
            try {
                List<String> lines = Files.readAllLines(Paths.get("data.txt"));
                for (String line : lines.subList(1, lines.size())) { 
                    String[] parts = line.split(",", 4);
                    int period = Integer.parseInt(parts[0]);
                    String Birth_Death = parts[1];
                    String region = parts[2].replace("\"", "");
                    int count = Integer.parseInt(parts[3]);
                    dataList.add(new entry(period, Birth_Death, region, count));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return dataList;
    }

    public static List<Map.Entry<String, Integer>> mergeSortRegions(List<Map.Entry<String, Integer>> entryList) {
        if (entryList.size() <= 1) {
            return entryList;
        }

        int mid = entryList.size() / 2;

        List<Map.Entry<String, Integer>> left = new ArrayList<>(entryList.subList(0, mid));
        List<Map.Entry<String, Integer>> right = new ArrayList<>(entryList.subList(mid, entryList.size()));

        return merge(mergeSortRegions(left), mergeSortRegions(right));
    }

    private static List<Map.Entry<String, Integer>> merge(List<Map.Entry<String, Integer>> left, List<Map.Entry<String, Integer>> right) {
        List<Map.Entry<String, Integer>> merged = new ArrayList<>();
        int leftIndex = 0, rightIndex = 0;

        while (leftIndex < left.size() && rightIndex < right.size()) {
            if (left.get(leftIndex).getValue() <= right.get(rightIndex).getValue()) {
                merged.add(left.get(leftIndex++));
            } else {
                merged.add(right.get(rightIndex++));
            }
        }

        while (leftIndex < left.size()) {
            merged.add(left.get(leftIndex++));
        }

        while (rightIndex < right.size()) {
            merged.add(right.get(rightIndex++));
        }

        return merged;
    }

    public static void quickSortRegions(List<Map.Entry<String, Integer>> entryList, int low, int high) {
        if (low < high) {
            int pi = partition(entryList, low, high);

            quickSortRegions(entryList, low, pi - 1);
            quickSortRegions(entryList, pi + 1, high);
        }
    }

    private static int partition(List<Map.Entry<String, Integer>> entryList, int low, int high) {
        int pivot = entryList.get(high).getValue();
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            if (entryList.get(j).getValue() <= pivot) {
                i++;

                Map.Entry<String, Integer> temp = entryList.get(i);
                entryList.set(i, entryList.get(j));
                entryList.set(j, temp);
            }
        }

        Map.Entry<String, Integer> temp = entryList.get(i + 1);
        entryList.set(i + 1, entryList.get(high));
        entryList.set(high, temp);

        return i + 1;
    }

    public static List<Map.Entry<String, Integer>> getQuickSortedRegions(List<Map.Entry<String, Integer>> entryList) {
        List<Map.Entry<String, Integer>> sortedList = new ArrayList<>(entryList);
        quickSortRegions(sortedList, 0, sortedList.size() - 1);
        return sortedList;
    }

    public static void main(String[] args) {

        List<entry> dataList = readFromFile("data.txt");
        Map<String, Integer> birthsByRegion = sumBirthsByRegion(dataList);

        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(birthsByRegion.entrySet());

        Scanner scanner = new Scanner(System.in);
        System.out.print("Type 0 for MergeSort or 1 for QuickSort: ");
        String sort = scanner.nextLine();
        scanner.close();

        if (sort.equals("0")) {
            List<Map.Entry<String, Integer>> sortedList = mergeSortRegions(entryList);
            for (Map.Entry<String, Integer> entry : sortedList) {
                System.out.println("Region: " + entry.getKey() + ", Total Births: " + entry.getValue());
            }
        } else if (sort.equals("1")) {
            List<Map.Entry<String, Integer>> sortedList = getQuickSortedRegions(entryList);
            for (Map.Entry<String, Integer> entry : sortedList) {
                System.out.println("Region: " + entry.getKey() + ", Total Births: " + entry.getValue());
            }
        } else {
            System.out.println("Error");
        }
    }
}
    




