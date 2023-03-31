package Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Dijkstra<N extends Node<N>> {
    N source;
    GraphADT<N> graph;
    Map<N, Double> distance = new HashMap<>();
    Map<N, N> previous = new HashMap<>();

    public Dijkstra(N source, GraphADT<N> graph) {
        this.source = source;
        this.graph = graph;
        N current = null;
        ArrayList<N> unvisited = new ArrayList<>();

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
//                    System.out.println("Current: "+current.getIndex());
                }
            }
            unvisited.remove(current);
//            if(graph.getNeighbours(current).isEmpty()) {
//                System.out.println(current+" has no neighbours");
//            }
            for (N neighbour : graph.getNeighbours(current)) {
                if (unvisited.contains(neighbour)) {
                    double dist = distance.get(current) + graph.getWeight(current, neighbour);
//                    System.out.format("Distance from %d to %d is %f\n", current.getIndex(), neighbour.getIndex(), dist);
                    if (dist < distance.get(neighbour)) {
                        distance.put(neighbour, dist);
                        previous.put(neighbour, current);
                    }
                }
            }
            current = null;
        }
    }
    public Path<N> getPath(N target) {
//        System.out.format("Trying to find path from %d to %d\n",source,target);
        LinkedList<N> path = new LinkedList<>();
        if(source == target) {
            path.addFirst(target);
            return new Path<>(path);
        }
        if(previous.get(target) != null) {
            N tempTarget = target;
            while (tempTarget != null) {
                path.addFirst(tempTarget);
                tempTarget = previous.get(tempTarget);
            }
        }
        return new Path<>(path);
    }
}
