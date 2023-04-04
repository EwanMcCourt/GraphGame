package Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

// Finds the cheapest path from a source node to every other node within a graph.
public class Dijkstra<N extends Node<N>> {
    private final N source;

    // Stores the previous node in the shortest path to a given node.
    private final Map<N, N> previous = new HashMap<>();

    // Allows the uniformCost Boolean to be omitted and have a default value of false be used.
    public Dijkstra(N source, GraphADT<N> graph) {
        this(source, graph, false);
    }

    public Dijkstra(N source, GraphADT<N> graph, Boolean uniformCost) {
        this.source = source;
        N current = null;
        ArrayList<N> unvisited = new ArrayList<>();

        // Stores the shortest distance to a given node.
        Map<N, Double> distance = new HashMap<>();

        // Populate unwanted, distance, and previous with default data to have them correctly sized and initialised for Dijkstra's algorithm.
        for (N node : graph.getNodes()) {
            unvisited.add(node);
            distance.put(node, Double.POSITIVE_INFINITY);
            previous.put(node, null);
        }

        // Set distance from source to itself to 0.
        distance.put(source, (double) 0);

        while (!unvisited.isEmpty()) {
            // Set current to unvisited node with the lowest distance.
            for(N node : unvisited) {
                if (current == null || distance.get(current) > distance.get(node)) {
                    current = node;
                }
            }

            // Remove current from unvisited list.
            unvisited.remove(current);

            // Calculate the cost of reaching all neighbours of current that are still unvisited.
            for (N neighbour : current.getNeighbours()) {
                if (unvisited.contains(neighbour)) {
                    double dist;

                    // If uniformCost == true then the cost of moving to any node is treated as 1.
                    // If uniformCost == false then the cost of moving to a node is the weight.
                    if (uniformCost) {
                        dist = distance.get(current) + 1;
                    } else {
                        dist = distance.get(current) + current.getWeight(neighbour);
                    }

                    // If the cost of this path is cheaper than any previously found then update distance and previous.
                    if (dist < distance.get(neighbour)) {
                        distance.put(neighbour, dist);
                        previous.put(neighbour, current);
                    }
                }
            }
            current = null;
        }
    }

    // Builds a GraphPath by querying previous from the target node until null is reached, indicating the source has been reached.
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
