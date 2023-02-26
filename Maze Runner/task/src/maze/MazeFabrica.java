package maze;

public class MazeFabrica {
    public static Maze getMaze(int height, int width) {
        return new Maze(height, width);
    }
}
