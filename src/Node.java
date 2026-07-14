// Represents a location on the map (used for A* heuristic)
public class Node {
    int id;
    double x, y;

    public Node(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }
}
