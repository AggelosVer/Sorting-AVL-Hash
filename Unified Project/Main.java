import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
}
