package Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Dijkstra<N extends Node<N>> {
    private final N source;
    private final Map<N, N> previous = new HashMap<>();

    public Dijkstra(N source, GraphADT<N> graph) {
        this(source, graph, false);
    }

    public Dijkstra(N source, GraphADT<N> graph, Boolean uniformCost) {
        this.source = source;
        N current = null;
        ArrayList<N> unvisited = new ArrayList<>();

        Map<N, Double> distance = new HashMap<>();
        for (N node : graph.getNodes()) {
            unvisited.add(node);
            distance.put(node, Double.POSITIVE_INFINITY);
            previous.put(node, null);
        }

        distance.put(source, (double) 0);

        while (!unvisited.isEmpty()) {
            for(N node : unvisited) {
                if (current == null || distance.get(current) > distance.get(node)) {
                    current = node;
                }
            }
            unvisited.remove(current);
            for (N neighbour : graph.getNeighbours(current)) {
                if (unvisited.contains(neighbour)) {
                    double dist;
                    if (uniformCost) {
                        dist = distance.get(current) + 1;
                    } else {
                        dist = distance.get(current) + graph.getWeight(current, neighbour);
                    }
                    if (dist < distance.get(neighbour)) {
                        distance.put(neighbour, dist);
                        previous.put(neighbour, current);
                    }
                }
            }
            current = null;
        }
    }
    public GraphPath<N> getGraphPath(N target) {
        LinkedList<N> path = new LinkedList<>();
        if(source == target) {
            path.addFirst(target);
            return new GraphPath<>(path);
        }
        if(previous.get(target) != null) {
            N tempTarget = target;
            while (tempTarget != null) {
                path.addFirst(tempTarget);
                tempTarget = previous.get(tempTarget);
            }
        }
        return new GraphPath<>(path);
    }
}
