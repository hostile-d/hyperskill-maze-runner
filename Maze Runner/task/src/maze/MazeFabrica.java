package maze;

public class MazeFabrica {
    public static Maze getMaze(int width, int height) {
        return new Maze(width, height);
    }
}
