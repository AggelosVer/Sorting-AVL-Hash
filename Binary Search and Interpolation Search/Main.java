import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

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

    public static Map<String, Integer> sumBirthsByRegion(List<Entry> dataList) {
        Map<String, Integer> birthsByRegion = new HashMap<>();
        for (Entry entry : dataList) {
            if (entry.getBirthDeath().equals("Births")) {
                birthsByRegion.put(entry.getRegion(),
                        birthsByRegion.getOrDefault(entry.getRegion(), 0) + entry.getCount());
            }
        }
        return birthsByRegion;
    }

    public static List<Map.Entry<String, Integer>> mergeSortRegionsByBirths(List<Map.Entry<String, Integer>> entryList) {
        if (entryList.size() <= 1) {
            return entryList;
        }

        int mid = entryList.size() / 2;
        List<Map.Entry<String, Integer>> left = new ArrayList<>(entryList.subList(0, mid));
        List<Map.Entry<String, Integer>> right = new ArrayList<>(entryList.subList(mid, entryList.size()));

        return merge(mergeSortRegionsByBirths(left), mergeSortRegionsByBirths(right));
    }

    private static List<Map.Entry<String, Integer>> merge(List<Map.Entry<String, Integer>> left, List<Map.Entry<String, Integer>> right) {
        List<Map.Entry<String, Integer>> result = new ArrayList<>();
        int leftIndex = 0, rightIndex = 0;

        while (leftIndex < left.size() && rightIndex < right.size()) {
            if (left.get(leftIndex).getValue() <= right.get(rightIndex).getValue()) {
                result.add(left.get(leftIndex));
                leftIndex++;
            } else {
                result.add(right.get(rightIndex));
                rightIndex++;
            }
        }

        while (leftIndex < left.size()) {
            result.add(left.get(leftIndex));
            leftIndex++;
        }

        while (rightIndex < right.size()) {
            result.add(right.get(rightIndex));
            rightIndex++;
        }

        return result;
    }

    public static List<Map.Entry<String, Integer>> interpolationSearchRegions(List<Map.Entry<String, Integer>> entryList, int b1, int b2) {
        List<Map.Entry<String, Integer>> result = new ArrayList<>();
        int low = 0;
        int high = entryList.size() - 1;

        while (low <= high && b1 <= b2 && b1 <= entryList.get(high).getValue() && b2 >= entryList.get(low).getValue()) {
            if (entryList.get(high).getValue() == entryList.get(low).getValue()) {
                if (entryList.get(low).getValue() >= b1 && entryList.get(low).getValue() <= b2) {
                    for (int i = low; i <= high; i++) {
                        result.add(entryList.get(i));
                    }
                }
                break;
            }

            int pos = low + (int) (((double) (high - low) / (entryList.get(high).getValue() - entryList.get(low).getValue())) * (b1 - entryList.get(low).getValue()));

            if (pos < low || pos > high) {
                break;
            }

            int posValue = entryList.get(pos).getValue();

            if (posValue >= b1 && posValue <= b2) {
                result.add(entryList.get(pos));

                int temp = pos - 1;
                while (temp >= low && entryList.get(temp).getValue() >= b1 && entryList.get(temp).getValue() <= b2) {
                    result.add(entryList.get(temp));
                    temp--;
                }

                temp = pos + 1;
                while (temp <= high && entryList.get(temp).getValue() >= b1 && entryList.get(temp).getValue() <= b2) {
                    result.add(entryList.get(temp));
                    temp++;
                }

                break;
            } else if (posValue < b1) {
                low = pos + 1;
            } else {
                high = pos - 1;
            }
        }

        result.removeIf(entry -> entry.getValue() < b1 || entry.getValue() > b2);
        return result;
    }

    public static List<Map.Entry<String, Integer>> binarySearchRegions(List<Map.Entry<String, Integer>> entryList, int b1, int b2) {
        List<Map.Entry<String, Integer>> result = new ArrayList<>();
        int left = 0, right = entryList.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int midValue = entryList.get(mid).getValue();

            if (midValue >= b1 && midValue <= b2) {
                result.add(entryList.get(mid));
                int temp = mid - 1;
                while (temp >= 0 && entryList.get(temp).getValue() >= b1 && entryList.get(temp).getValue() <= b2) {
                    result.add(entryList.get(temp--));
                }
                temp = mid + 1;
                while (temp < entryList.size() && entryList.get(temp).getValue() >= b1 && entryList.get(temp).getValue() <= b2) {
                    result.add(entryList.get(temp++));
                }
                break;
            } else if (midValue < b1) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        List<Entry> dataList = readFromFile("data.txt");

        Map<String, Integer> birthsByRegion = sumBirthsByRegion(dataList);

        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(birthsByRegion.entrySet());

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the lower bound (b1): ");
        int b1 = scanner.nextInt();
        System.out.print("Enter the upper bound (b2): ");
        int b2 = scanner.nextInt();
        System.out.print("Type 0 for Binary Search or 1 for Interpolation Search: ");
        String searchType = scanner.next();
        scanner.close();

        List<Map.Entry<String, Integer>> sortedList = mergeSortRegionsByBirths(entryList);

        List<Map.Entry<String, Integer>> resultList;
        if (searchType.equals("0")) {
            resultList = binarySearchRegions(sortedList, b1, b2);
        } else if (searchType.equals("1")) {
            resultList = interpolationSearchRegions(sortedList, b1, b2);
        } else {
            System.out.println("Error");
            return;
        }

        System.out.println("\nRegions with births in the given range:");
        for (Map.Entry<String, Integer> entry : resultList) {
            System.out.println("Region: " + entry.getKey() + ", Total Births: " + entry.getValue());
        }
    }
}
