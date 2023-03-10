package maze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class Maze {
    private int height;
    private int width;
    private final String PASSAGE = "  ";
    private final String WALL = "██";
    private final int WALL_WIDTH_TOTAL = 2;
    private Integer[][] graph;
    private ArrayList<String> printedMaze = new ArrayList<>();

    public Maze(int height, Optional<Integer> width) {
        this.height = height - WALL_WIDTH_TOTAL;
        this.width = width.map(integer -> integer - WALL_WIDTH_TOTAL).orElseGet(() -> this.height);
        var fabric = new GraphFabric(getSizeInGraph(this.height), getSizeInGraph(this.width));
        fabric.getGraph();
        fabric.buildMST();
        graph = fabric.getCleanedGraph();
    }

    public void makePrintable() {
        printedMaze.add(WALL.repeat(width + WALL_WIDTH_TOTAL));

        for (int i = 0; i < height; i++) {
            String[] row = new String[width];
            for (int j = 0; j < width; j++) {
                boolean isNodeHere = i % 2 == 0 && j % 2 == 0;
                if (isNodeHere || isEdgeHere(graph, j, i)) {
                    row[j] = PASSAGE;
                    continue;
                }

                row[j] = WALL;
            }
            printedMaze.add(getInnerLine(row, i));
        }

        printedMaze.add(WALL.repeat(width + WALL_WIDTH_TOTAL));
    }

    public ArrayList<String> getPrintedMaze() {
        return this.printedMaze;
    }

    public String getInnerLine (String[] row, int index) {
        boolean isOddRowsAmount = height % 2 == 0;

        if (isOddRowsAmount && index == height - 1) {
            return WALL + PASSAGE.repeat(width) + WALL;
        }

        int exitLine = isOddRowsAmount ? height - 2 : height - 1;
        String line = Arrays.toString(row)
                .replace(", ", "")
                .replace("[", "")
                .replace("]", "");

        String prefix = index == 0 ? PASSAGE : WALL;
        String suffix = index == exitLine ? PASSAGE : WALL;

        return prefix + line + suffix;
    }

    private int getNodeIndex(int x, int y) {
        var graphWidth = getSizeInGraph(width);
        var inGraphX = getSizeInGraph(x);
        var inGraphY = getSizeInGraph(y);
        return inGraphY * graphWidth + inGraphX;
    }

    public int getSizeInGraph(int size) {
        if (size % 2 == 0) {
            return size / 2;
        }
        return (size + 1) / 2;
    }

    private boolean isEdgeHere(Integer[][] matrix, int x, int y) {
        boolean xEdgeIsHere = false;
        boolean yEdgeIsHere = false;

        boolean isXEdgePossible = y % 2 == 0;
        boolean isYEdgePossible = x % 2 == 0;

        if(isXEdgePossible) {
            int nextNodeXIndex = getNodeIndex(x + 1, y);
            int prevNodeXIndex = getNodeIndex(x - 1, y);

            if (nextNodeXIndex < matrix.length && prevNodeXIndex >= 0) {
                xEdgeIsHere = matrix[nextNodeXIndex][prevNodeXIndex] != 0;
            }
        }

        if (isYEdgePossible) {
            int nextNodeYIndex = getNodeIndex(x, y + 1);
            int prevNodeYIndex = getNodeIndex(x, y - 1);

            if (nextNodeYIndex < matrix.length && prevNodeYIndex >= 0) {
                yEdgeIsHere = matrix[nextNodeYIndex][prevNodeYIndex] != 0;
            }
        }

        return xEdgeIsHere || yEdgeIsHere;
    }
}