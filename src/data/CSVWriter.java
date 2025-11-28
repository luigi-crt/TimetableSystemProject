
package data;

import java.io.*;
import java.util.*;

/**
 * A generic CSV writer that writes data to CSV files.
 */
public class CSVWriter implements AutoCloseable {
    private PrintWriter writer;

    /**
     * Constructor opens the file for writing (overwrites existing content).
     * @param filename Path to the CSV file.
     * @throws FileNotFoundException if the file cannot be created.
     */
    public CSVWriter(String filename) throws FileNotFoundException {
        writer = new PrintWriter(filename);
    }

    /**
     * Writes a single line to the CSV file.
     * @param line The line to write (already formatted as CSV).
     */
    public void writeRecord(String line) {
        writer.println(line);
    }

    /**
     * Writes a list of raw lines to the CSV file.
     * @param lines List of strings, each representing one row.
     */
    public void writeAll(List<String> lines) {
        for (String line : lines) {
            writer.println(line);
        }
    }

    /**
     * Writes a list of rows where each row is an array of columns.
     * @param rows List of String[] arrays representing CSV rows.
     */
    public void writeMatrix(List<String[]> rows) {
        for (String[] row : rows) {
            writer.println(String.join(",", row));
        }
    }

    /**
     * Closes the PrintWriter resource.
     */
    @Override
    public void close() {
        if (writer != null) {
            writer.close();
        }
    }
}
