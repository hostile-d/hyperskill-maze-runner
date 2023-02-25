package maze;

public class Main {
    public static void main(String[] args) {
        Maze maze = MazeFabrica.getMaze(10, 10);
        maze.print();
    }
}
