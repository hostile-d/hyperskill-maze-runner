package maze;

public class Edge {
    public int nodeA;
    public int nodeB;
    public int weight;
    public Edge(int nodeA, int nodeB, int weight) {
        this.nodeA = nodeA;
        this.nodeB = nodeB;
        this.weight = weight;
    }
}
