package maze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GraphFabric {
    private Integer[][] graph;
    private Integer[][] cleanedGraph;
    private Integer[][] MST;
    private final int MAX_WEIGHT = Integer.MAX_VALUE;
    private final int height;
    private final int width;
    private final int nodesCount;
    private final Random random = new Random();
    public GraphFabric (int height, int width) {
        this.height = height;
        this.width = width;
        this.nodesCount = height * width;
        generateGraph();
    }
    public Integer[][] getGraph() {
        return graph;
    }
    private void generateGraph() {
        Integer[][] graph = new Integer[nodesCount][nodesCount];
        for (int i = 0; i < nodesCount; i++) {
            Integer[] row = new Integer[nodesCount];

            for (int j = 0; j < nodesCount; j++) {
                boolean areDifferentNodes = i != j;
                if (areDifferentNodes && areNeighbors(i, j)) {
                    boolean isWeightSet = graph[j][i] != null;
                    row[j] = isWeightSet ? graph[j][i] : random.nextInt(MAX_WEIGHT) + 1;
                    continue;
                }
                row[j] = 0;
            }
            graph[i] = row;
        }
        this.graph = graph;
    }

    private boolean areNeighbors(int a, int b) {
        double ax = Math.floor(a / width);
        double ay = a % width;
        double bx = Math.floor(b / width);
        double by = b % width;
        boolean neighborsX = ax - bx == 0 && Math.abs(ay - by) == 1;
        boolean neighborsY = ay - by == 0 && Math.abs(ax - bx) == 1;
        return neighborsX || neighborsY;
    }

    public void buildMST() {
        boolean[] inMST = new boolean[nodesCount];
        MST = new Integer[nodesCount][nodesCount];
        int edgesCount = 0;

        inMST[0] = true;

        while (edgesCount < nodesCount - 1) {
            int min = MAX_WEIGHT;
            int a = -1;
            int b = -1;

            for (int i = 0; i < nodesCount; i++) {
                for (int j = 0; j < nodesCount; j++) {
                    int currentWeight = graph[i][j];
                    if (currentWeight < min && canAddToMST(i, j, inMST)) {
                        min = currentWeight;
                        a = i;
                        b = j;
                    }
                }
            }
            if (a != -1 && b != -1) {
                edgesCount++;
                inMST[b] = true;
                inMST[a] = true;
                MST[a][b] = graph[a][b];
                MST[b][a] = graph[b][a];
            }
        }
    }

    private boolean canAddToMST(int u, int v, boolean[] inMST) {
        if (u == v) {
            return false;
        }
        if (!inMST[u] && !inMST[v]) {
            return false;
        }
        if (graph[u][v] == 0) {
            return false;
        }
        return !inMST[u] || !inMST[v];
    }

    public Integer[][] getCleanedGraph() {
        Integer[][] cleanedGraph = new Integer[nodesCount][nodesCount];

        for (int i = 0; i < nodesCount; i++) {
            for (int j = 0; j < nodesCount; j++) {
                if (MST[i][j] != null) {
                    cleanedGraph[i][j] = MAX_WEIGHT;
                    continue;
                }
                cleanedGraph[i][j] = 0;
            }
        }

        this.cleanedGraph = cleanedGraph;
        return cleanedGraph;
    }
}
