import java.util.List;

public class PathResult {
    public double distance;
    public List<Integer> path;
    public int nodesExplored;
    public long timeMs;
    public boolean found;

    public PathResult(double distance, List<Integer> path, int nodesExplored, long timeMs, boolean found) {
        this.distance = distance;
        this.path = path;
        this.nodesExplored = nodesExplored;
        this.timeMs = timeMs;
        this.found = found;
    }
}
