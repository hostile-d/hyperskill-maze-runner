package maze;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.Callable;

public class Menu {
    private LinkedHashMap<Integer, Command> menuItems = new LinkedHashMap<>();
    private Scanner scanner = new Scanner(System.in);

    private ArrayList<String> maze;
    private FileManager fileManager;

    public Menu() {
        menuItems.put(1, new Command("Generate a new maze\n", this::getMaze));
        menuItems.put(2, new Command("Load a maze\n", this::load));
        menuItems.put(3, new Command("Save the maze\n", this::save));
        menuItems.put(4, new Command("Display the maze\n", this::display));
        menuItems.put(0, new Command("Exit\n", this::exit));
        startNavigation();
    }

    public void startNavigation() {
        printMenu();
        var nextChoice = scanner.nextLine();
        try {
            handleMenuChange(Integer.parseInt(nextChoice));
        } catch (NumberFormatException e) {
            System.out.printf("Incorrect input, type a number from 0 to %s to continue\n", menuItems.size());
            startNavigation();
        }
    }

    private void handleMenuChange(Integer selectedOption) {
        callAction(menuItems.get(selectedOption));
        startNavigation();
    }

    private void callAction(Command menuItem) {
        try {
            menuItem.callAction();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void printMenu() {
        System.out.println("=== Menu ===");
        for (Map.Entry<Integer, Command> entry: menuItems.entrySet()) {
            System.out.printf("%d. %s", entry.getKey(), entry.getValue().getName());
        }
        System.out.println();
    }

    private Callable getMaze() {
        System.out.println("Enter the size of a new maze");
        String[] input = scanner.nextLine().split(" ");
        Integer height = Integer.parseInt(input[0]);
        Optional<Integer> width = Optional.empty();
        if (input.length > 1) {
            width = Optional.of(Integer.parseInt(input[1]));
        }
        Maze maze = MazeFabrica.getMaze(height, width);
        maze.makePrintable();
        this.maze = maze.getPrintedMaze();
        printMaze();
        return null;
    }

    private void printMaze() {
        for (String line: maze) {
            System.out.println(line);
        }
    }

    private Callable load() {
        String fileName = scanner.nextLine();
        fileManager = new FileManager(fileName);
        try {
            maze = fileManager.load();
        } catch (FileNotFoundException e) {
            System.out.printf("The file %s does not exist\n", fileName);
        }
        return null;
    }

    private Callable save() {
        String fileName = scanner.nextLine();
        fileManager = new FileManager(fileName);
        fileManager.save(maze);
        return null;
    }

    private Callable display() {
        printMaze();
        return null;
    }

    private Callable exit() {
        System.exit(0);
        System.out.println("Bye!");
        return null;
    }
}
