package view;
import data.*;

public class Main {
    public static void main(String[] args) {
        DataManager data = new DataManager();
        data.loadAll();

        System.out.println(data.getStudent("S001").getName());
        System.out.println(data.getRoom("KBG13").getCapacity());
        System.out.println(data.scheduledClasses.size());

    }
}
