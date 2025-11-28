
package data;

import java.io.*;
import java.util.*;

/**
 * A generic CSV reader that reads CSV files and provides data as lists or arrays.
 */
public class CSVReader implements AutoCloseable {
    private Scanner input;

    /**
     * Constructor to initialise the CSVReader with the filename.
     * @param filename Path to the CSV file.
     * @throws FileNotFoundException if the file does not exist.
     */
    public CSVReader(String filename) throws FileNotFoundException {
        input = new Scanner(new File(filename));
    }

    /**
     * Reads the next line from the CSV and splits it by ','.
     * @return An array of Strings representing the columns, or empty array if no more lines.
     */
    public String[] getValues() {
        if (input.hasNextLine()) {
            String line = input.nextLine();
            return line.split(",");
        } else {
            return new String[0];
        }
    }

    /**
     * Reads the entire CSV file into a list of raw lines.
     * @return An ArrayList of Strings, each representing one line.
     */
    public ArrayList<String> readToList() {
        ArrayList<String> allData = new ArrayList<>();
        while (input.hasNextLine()) {
            allData.add(input.nextLine());
        }
        return allData;
    }

    /**
     * Reads the entire CSV file into a list of String arrays (split by comma).
     * @return An ArrayList of String[] where each array represents one row.
     */
    public ArrayList<String[]> readToMatrix() {
        ArrayList<String[]> allRows = new ArrayList<>();
        while (input.hasNextLine()) {
            String[] tokens = input.nextLine().split(",");
            allRows.add(tokens);
        }
        return allRows;
    }

    /**
     * Closes the Scanner resource.
     */
    @Override
    public void close() {
        if (input != null) {
            input.close();
        }
    }
}
