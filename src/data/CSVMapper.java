package data;

import java.io.*;
import java.util.*;
import java.util.function.Function;

public class CSVMapper {

    public static <T> List<T> load(String file, Function<String[], T> mapper) {
        List<T> results = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/data/" + file))) {
            String line;
            while ((line = br.readLine()) != null) {
                results.add(mapper.apply(line.split(",")));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return results;
    }

    public static <T> void save(String file, List<T> list, Function<T, String> serializer) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/data/" + file))) {
            for (T obj : list) {
                bw.write(serializer.apply(obj));
                bw.newLine();
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}
