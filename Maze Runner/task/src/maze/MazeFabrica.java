package maze;

import java.util.Optional;

public class MazeFabrica {
    public static Maze getMaze(int height, Optional<Integer> width) {
        return new Maze(height, width);
    }
}
