package maze;

import java.util.Arrays;
import java.util.Random;

public class Maze {
    private final int width;
    private final int height;
    private final String[][] map;
    private final String passage = " ";
    private final String wall = "██";

    private Random random;

    public Maze(int width, int height) {
        this.width = width;
        this.height = height;
        random = new Random();
        this.map = new String[height][width];
        generateDefaultMap();
    }

    private void generateDefaultMap() {
        var stringArry = new String[]{
            "████████████████████",
            "    ██  ██  ██    ██",
            "██  ██      ██  ████",
            "██      ██████      ",
            "██  ██          ████",
            "██  ██  ██████  ████",
            "██  ██  ██      ████",
            "██  ██  ██████  ████",
            "██  ██      ██    ██",
            "████████████████████",
        };
        for (int i = 0; i < height; i++) {
            map[i] = stringArry[i].split("");
        }
    }

    private void generateMap() {
        for (int i = 0; i < height; i++) {
            String[] row = new String[width];

            for (int j = 0; j < width; j++) {
                if (i == 0 || i == height - 1 || j == 0 || j == width - 1) {
                    row[j] = wall;
                    continue;
                }

                row[j] = random.nextBoolean() ? wall : passage;
            }
            map[i] = row;
        }
    }

    public void print() {
        for (String[] row: map) {
            System.out.println(String.join("", row));
        }
    }
}
