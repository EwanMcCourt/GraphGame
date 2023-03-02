package Graph;

import java.util.*;

public class Dijkstra {
    Node source;
    GraphADT graph;
    Map<Node, Double> distance = new HashMap<>();
    Map<Node, Node> previous = new HashMap<>();

    public Dijkstra(Node source, GraphADT graph) {
        this.source = source;
        this.graph = graph;
        Node current = null;
        ArrayList<Node> unvisited = new ArrayList<>();

        for (Node node : graph.getNodes()) {
            unvisited.add(node);
            distance.put(node, Double.POSITIVE_INFINITY);
            previous.put(node, null);
        }

        distance.put(source, (double) 0);

        while (!unvisited.isEmpty()) {
            for(Node node : unvisited) {
                if (current == null || distance.get(current) > distance.get(node)) {
                    current = node;
//                    System.out.println("Current: "+current.getIndex());
                }
            }
            unvisited.remove(current);
//            if(graph.getNeighbours(current).isEmpty()) {
//                System.out.println(current+" has no neighbours");
//            }
            for (Node neighbour : graph.getNeighbours(current)) {
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
    public Path getPath(Node target) {
//        System.out.format("Trying to find path from %d to %d\n",source,target);
        LinkedList<Node> path = new LinkedList<>();
        if(source == target) {
            path.addFirst(target);
            return new Path(path);
        }
        if(previous.get(target) != null) {
            Node tempTarget = target;
            while (tempTarget != null) {
                path.addFirst(tempTarget);
                tempTarget = previous.get(tempTarget);
            }
        }
        return new Path(path);
    }
}
