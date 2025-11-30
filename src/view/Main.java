package view;

import data.DataManager;
import view.CLI;

/**
 * Entry point for the System Application
 *
 * Initialises the DataManager and the command line interface(CLI)
 * for user interaction.
 */
public class Main {
    /**
     * Method starts the application
     * @param args
     */
    public static void main(String[] args) {
        DataManager dataManager = new DataManager();
        CLI cli = new CLI(dataManager);
        cli.start();
    }
}
