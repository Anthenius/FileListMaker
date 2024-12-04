
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String FILE_EXTENSION = ".txt";
    private static ArrayList<String> list = new ArrayList<>();
    private static boolean needsToBeSaved = false;

    public static void main(String[] args) {
        String choice;
        do {
            displayMenu();
            choice = scanner.nextLine().toUpperCase();
            try {
                switch (choice) {
                    case "A": addItem(); break;
                    case "D": deleteItem(); break;
                    case "I": insertItem(); break;
                    case "M": moveItem(); break;
                    case "V": viewList(); break;
                    case "C": clearList(); break;
                    case "O": openFile(); break;
                    case "S": saveFile(); break;
                    case "Q": quitProgram(); break;
                    default: System.out.println("Invalid choice. Try again.");
                }
            } catch (IOException e) {
                System.out.println("File error: " + e.getMessage());
            }
        } while (!choice.equals("Q"));
    }

    private static void displayMenu() {
        System.out.println("\nMenu:");
        System.out.println("A - Add an item to the list");
        System.out.println("D - Delete an item from the list");
        System.out.println("I - Insert an item into the list");
        System.out.println("M - Move an item");
        System.out.println("V - View the list");
        System.out.println("C - Clear the list");
        System.out.println("O - Open a list file");
        System.out.println("S - Save the current list");
        System.out.println("Q - Quit");
        System.out.print("Choose an option: ");
    }

    private static void addItem() {
        System.out.print("Enter the item to add: ");
        list.add(scanner.nextLine());
        needsToBeSaved = true;
    }

    private static void deleteItem() {
        viewList();
        System.out.print("Enter the index of the item to delete: ");
        int index = Integer.parseInt(scanner.nextLine());
        if (index >= 0 && index < list.size()) {
            list.remove(index);
            needsToBeSaved = true;
        } else {
            System.out.println("Invalid index.");
        }
    }

    private static void insertItem() {
        viewList();
        System.out.print("Enter the index to insert the item at: ");
        int index = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter the item to insert: ");
        String item = scanner.nextLine();
        if (index >= 0 && index <= list.size()) {
            list.add(index, item);
            needsToBeSaved = true;
        } else {
            System.out.println("Invalid index.");
        }
    }

    private static void moveItem() {
        viewList();
        System.out.print("Enter the index of the item to move: ");
        int fromIndex = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter the new index for the item: ");
        int toIndex = Integer.parseInt(scanner.nextLine());
        if (fromIndex >= 0 && fromIndex < list.size() && toIndex >= 0 && toIndex <= list.size()) {
            String item = list.remove(fromIndex);
            list.add(toIndex, item);
            needsToBeSaved = true;
        } else {
            System.out.println("Invalid index.");
        }
    }

    private static void viewList() {
        if (list.isEmpty()) {
            System.out.println("The list is empty.");
        } else {
            for (int i = 0; i < list.size(); i++) {
                System.out.println(i + ": " + list.get(i));
            }
        }
    }

    private static void clearList() {
        System.out.print("Are you sure you want to clear the list? (Y/N): ");
        if (scanner.nextLine().equalsIgnoreCase("Y")) {
            list.clear();
            needsToBeSaved = true;
        }
    }

    private static void openFile() throws IOException {
        if (needsToBeSaved) {
            promptToSave();
        }
        System.out.print("Enter the filename to open: ");
        String filename = scanner.nextLine();
        list = loadFile(filename);
        System.out.println("File loaded successfully.");
        needsToBeSaved = false;
    }

    private static void saveFile() throws IOException {
        System.out.print("Enter the filename to save as: ");
        String filename = scanner.nextLine();
        writeFile(filename);
        System.out.println("File saved successfully.");
        needsToBeSaved = false;
    }

    private static void quitProgram() throws IOException {
        if (needsToBeSaved) {
            promptToSave();
        }
        System.out.println("Exiting program. Goodbye!");
    }

    private static void promptToSave() throws IOException {
        System.out.print("You have unsaved changes. Save now? (Y/N): ");
        if (scanner.nextLine().equalsIgnoreCase("Y")) {
            saveFile();
        }
    }

    private static ArrayList<String> loadFile(String filename) throws IOException {
        ArrayList<String> loadedList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename + FILE_EXTENSION))) {
            String line;
            while ((line = reader.readLine()) != null) {
                loadedList.add(line);
            }
        }
        return loadedList;
    }

    private static void writeFile(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename + FILE_EXTENSION))) {
            for (String item : list) {
                writer.write(item);
                writer.newLine();
            }
        }
    }
}


