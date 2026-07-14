import java.util.ArrayList;
import java.util.List;

public class Graph {
    int n;
    List<Node> nodes;
    List<List<Edge>> adj;

    public Graph(int n) {
        this.n = n;
        nodes = new ArrayList<>();
        adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            nodes.add(new Node(i, i % 10, i / 10));
            adj.add(new ArrayList<>());
        }
    }

    public void addEdge(int u, int v, double weight) {
        adj.get(u).add(new Edge(v, weight));
        adj.get(v).add(new Edge(u, weight));
    }
}
