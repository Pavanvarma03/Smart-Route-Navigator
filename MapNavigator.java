import java.util.Random;

// Shortest-path demo: Dijkstra, BFS, and A* on a small city graph
public class MapNavigator {

    public static void main(String[] args) {
        System.out.println("Map Navigator - shortest path algorithms\n");

        Graph city = buildCityGraph();
        int[][] routes = {{0, 19}, {3, 17}, {0, 0}};

        for (int[] route : routes) {
            runComparison(city, route[0], route[1]);
        }

        // disconnected graph check
        Graph split = new Graph(4);
        split.addEdge(0, 1, 1);
        split.addEdge(2, 3, 1);
        PathResult noPath = Algorithms.dijkstra(split, 0, 3);
        System.out.println("\nEdge case - disconnected 0 -> 3: found=" + noPath.found);
    }

    static Graph buildCityGraph() {
        Graph g = new Graph(20);
        Random rand = new Random(42);

        // chain backbone with some cross streets
        for (int i = 0; i < 19; i++) {
            g.addEdge(i, i + 1, 2 + rand.nextDouble() * 3);
        }
        g.addEdge(0, 5, 4);
        g.addEdge(2, 8, 3);
        g.addEdge(4, 12, 5);
        g.addEdge(7, 15, 4);
        g.addEdge(10, 18, 6);
        g.addEdge(1, 14, 7);
        g.addEdge(6, 11, 2);
        g.addEdge(9, 16, 3);
        return g;
    }

    static void runComparison(Graph g, int src, int dest) {
        System.out.println("Route: " + src + " -> " + dest);

        PathResult d = Algorithms.dijkstra(g, src, dest);
        PathResult a = Algorithms.aStar(g, src, dest);
        PathResult b = Algorithms.bfs(g, src, dest);

        printResult("Dijkstra", d);
        printResult("A*", a);
        printResult("BFS", b);

        if (d.found) {
            System.out.println("  Shortest path: " + d.path);
        }
        System.out.println();
    }

    static void printResult(String name, PathResult r) {
        if (r.found) {
            System.out.printf("  %s: dist=%.2f, explored=%d, time=%dms%n",
                    name, r.distance, r.nodesExplored, r.timeMs);
        } else {
            System.out.printf("  %s: no path found (explored=%d)%n", name, r.nodesExplored);
        }
    }
}
