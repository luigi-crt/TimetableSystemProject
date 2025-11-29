package view;

import data.DataManager;
import view.CLI;

public class Main {
    public static void main(String[] args) {
        DataManager dataManager = new DataManager();
        CLI cli = new CLI(dataManager);
        cli.start();
    }
}
