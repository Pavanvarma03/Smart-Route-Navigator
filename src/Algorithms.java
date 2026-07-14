import java.util.*;

public class Algorithms {

    // shortest path, non-negative weights
    public static PathResult dijkstra(Graph g, int src, int dest) {
        long start = System.currentTimeMillis();
        int explored = 0;

        double[] dist = new double[g.n];
        int[] parent = new int[g.n];
        boolean[] visited = new boolean[g.n];

        Arrays.fill(dist, Double.MAX_VALUE);
        Arrays.fill(parent, -1);
        dist[src] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Double.compare(dist[a[0]], dist[b[0]]));
        pq.offer(new int[]{src});

        while (!pq.isEmpty()) {
            int u = pq.poll()[0];
            if (visited[u]) continue;
            visited[u] = true;
            explored++;

            if (u == dest) break;

            for (Edge e : g.adj.get(u)) {
                if (dist[u] + e.weight < dist[e.to]) {
                    dist[e.to] = dist[u] + e.weight;
                    parent[e.to] = u;
                    pq.offer(new int[]{e.to});
                }
            }
        }

        List<Integer> path = buildPath(parent, src, dest, dist);
        boolean found = !path.isEmpty();
        return new PathResult(found ? dist[dest] : Double.MAX_VALUE, path, explored,
                System.currentTimeMillis() - start, found);
    }

    // fewest hops (ignores weights during search)
    public static PathResult bfs(Graph g, int src, int dest) {
        long start = System.currentTimeMillis();
        int explored = 0;

        Queue<Integer> q = new LinkedList<>();
        int[] parent = new int[g.n];
        boolean[] visited = new boolean[g.n];

        Arrays.fill(parent, -1);
        q.offer(src);
        visited[src] = true;

        while (!q.isEmpty()) {
            int u = q.poll();
            explored++;
            if (u == dest) break;

            for (Edge e : g.adj.get(u)) {
                if (!visited[e.to]) {
                    visited[e.to] = true;
                    parent[e.to] = u;
                    q.offer(e.to);
                }
            }
        }

        List<Integer> path = buildPath(parent, src, dest, visited);
        double total = pathWeight(g, path);
        return new PathResult(total, path, explored, System.currentTimeMillis() - start, !path.isEmpty());
    }

    // heuristic search (f = g + h)
    public static PathResult aStar(Graph g, int src, int dest) {
        long start = System.currentTimeMillis();
        int explored = 0;

        double[] gScore = new double[g.n];
        double[] fScore = new double[g.n];
        int[] parent = new int[g.n];
        boolean[] visited = new boolean[g.n];

        Arrays.fill(gScore, Double.MAX_VALUE);
        Arrays.fill(fScore, Double.MAX_VALUE);
        Arrays.fill(parent, -1);

        gScore[src] = 0;
        fScore[src] = heuristic(g, src, dest);

        PriorityQueue<int[]> open = new PriorityQueue<>((a, b) -> Double.compare(fScore[a[0]], fScore[b[0]]));
        open.offer(new int[]{src});

        while (!open.isEmpty()) {
            int u = open.poll()[0];
            if (visited[u]) continue;
            visited[u] = true;
            explored++;

            if (u == dest) break;

            for (Edge e : g.adj.get(u)) {
                double tentative = gScore[u] + e.weight;
                if (tentative < gScore[e.to]) {
                    parent[e.to] = u;
                    gScore[e.to] = tentative;
                    fScore[e.to] = gScore[e.to] + heuristic(g, e.to, dest);
                    open.offer(new int[]{e.to});
                }
            }
        }

        List<Integer> path = buildPath(parent, src, dest, gScore);
        boolean found = !path.isEmpty();
        return new PathResult(found ? gScore[dest] : Double.MAX_VALUE, path, explored,
                System.currentTimeMillis() - start, found);
    }

    private static double heuristic(Graph g, int u, int dest) {
        Node a = g.nodes.get(u);
        Node b = g.nodes.get(dest);
        return Math.hypot(a.x - b.x, a.y - b.y);
    }

    private static List<Integer> buildPath(int[] parent, int src, int dest, double[] dist) {
        List<Integer> path = new ArrayList<>();
        if (src == dest) {
            path.add(src);
            return path;
        }
        if (dist[dest] == Double.MAX_VALUE) return path;
        for (int cur = dest; cur != -1; cur = parent[cur])
            path.add(0, cur);
        return path;
    }

    private static List<Integer> buildPath(int[] parent, int src, int dest, boolean[] visited) {
        List<Integer> path = new ArrayList<>();
        if (src == dest) {
            path.add(src);
            return path;
        }
        if (!visited[dest]) return path;
        for (int cur = dest; cur != -1; cur = parent[cur])
            path.add(0, cur);
        return path;
    }

    private static double pathWeight(Graph g, List<Integer> path) {
        double total = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            int u = path.get(i), v = path.get(i + 1);
            for (Edge e : g.adj.get(u)) {
                if (e.to == v) {
                    total += e.weight;
                    break;
                }
            }
        }
        return total;
    }
}
