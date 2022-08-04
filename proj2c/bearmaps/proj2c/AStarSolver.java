package bearmaps.proj2c;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.introcs.Stopwatch;
import java.util.Collections;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex>{
    private int numStatesExplored;
    private ArrayHeapMinPQ<Vertex> MinPQ;
    private HashMap<Vertex, Double> distTo;
    private HashMap<Vertex, Vertex> edgeTo;
    private boolean isFoundTarget;
    private double explorationTime;
    private boolean isTimeOut;
    private Vertex source;
    private Vertex target;
    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        /*
        For distTo map, we store the actual distances of the vertex from the source.
        And in the priority queue, we add up the heuristic results.
         */
        Stopwatch sw = new Stopwatch();
        MinPQ = new ArrayHeapMinPQ<>();
        distTo = new HashMap<>();
        edgeTo = new HashMap<>();
        isFoundTarget = false;
        isTimeOut = false;
        numStatesExplored = 0;
        source = start;
        target = end;

        MinPQ.add(source, input.estimatedDistanceToGoal(source, target));
        distTo.put(source, 0.0);
        edgeTo.put(source, null);
        double timeTemp = sw.elapsedTime();

        while (MinPQ.size() > 0) {
            double timeTemp1 = sw.elapsedTime();
            Vertex current = MinPQ.removeSmallest();
            double timeTemp2 = sw.elapsedTime();
            numStatesExplored += 1;
            double timeSpent = sw.elapsedTime();
            if (timeSpent > timeout) {
                isTimeOut = true;
                break;
            }
            if (current.equals(target)) {
                isFoundTarget = true;
                break;
            }

            for (WeightedEdge<Vertex> edge : input.neighbors(current)) {
                relax(edge, input, target);
            }
        }
        explorationTime = sw.elapsedTime();
    }
    /*
    Helper method for edge relaxation.
     */
    private void relax(WeightedEdge<Vertex> edge, AStarGraph<Vertex> graph, Vertex goal) {
        Vertex from = edge.from();
        Vertex to = edge.to();
        double dist = distTo.get(from) + edge.weight();
        double priority = dist + graph.estimatedDistanceToGoal(to, goal);
        /*
        If the vertex does not exist in the MinPQ, add it directly to the MinPQ.
         */
        if (!distTo.containsKey(to)) {
            edgeTo.put(to, from);
            distTo.put(to, dist);
            MinPQ.add(to, priority);
            return;
        }
        if (distTo.get(to) > dist) {
            edgeTo.replace(to, from);
            distTo.replace(to, dist);
            MinPQ.changePriority(to, priority);
        }
    }

    @Override
    public SolverOutcome outcome() {
        if (isTimeOut) {
            return SolverOutcome.TIMEOUT;
        }
        if (isFoundTarget) {
            return SolverOutcome.SOLVED;
        }
        else {
            return SolverOutcome.UNSOLVABLE;
        }
    }
    @Override
    public List<Vertex> solution() {
        List<Vertex> paths = new ArrayList<>();
        if (outcome().equals(SolverOutcome.TIMEOUT) || outcome().equals(SolverOutcome.UNSOLVABLE)) {
            return paths;
        }
        Vertex current = target;
        while (!current.equals(source)) {
            paths.add(current);
            current = edgeTo.get(current);
        }
        paths.add(source);
        Collections.reverse(paths);
        return paths;
    }
    @Override
    public double solutionWeight() {
        if (outcome().equals(SolverOutcome.TIMEOUT) || outcome().equals(SolverOutcome.UNSOLVABLE)) {
            return 0;
        }
        return distTo.get(target);
    }
    @Override
    public int numStatesExplored() {
        return numStatesExplored;
    }
    public double explorationTime() {
        return explorationTime;
    }
}
