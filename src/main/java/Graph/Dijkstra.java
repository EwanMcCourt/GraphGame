package Graph;

import java.util.*;

public class Dijkstra {
    int source;
    Graph graph;
    Map<Integer, Double> distance = new HashMap<>();
    Map<Integer, Integer> previous = new HashMap<>();

    public Dijkstra(int source, Graph graph) {
        this.source = source;
        this.graph = graph;
        Integer current = null;
        ArrayList<Integer> unvisited = new ArrayList<>();

        for (int index : graph.getNodes()) {
            unvisited.add(index);
            distance.put(index, Double.POSITIVE_INFINITY);
            previous.put(index, null);
        }

        distance.put(source, (double) 0);

        while (!unvisited.isEmpty()) {
            for(Integer index : unvisited) {
                if (current == null || distance.get(current) > distance.get(index)) {
                    current = index;
//                    System.out.println("Current: "+current);
                }
            }
            unvisited.remove(current);
//            if(graph.getNeighbours(current).isEmpty()) {
//                System.out.println(current+" has no neighbours");
//            }
            for (int neighbour : graph.getNeighbours(current)) {
                if (unvisited.contains(neighbour)) {
                    double dist = distance.get(current) + graph.getWeight(current, neighbour);
//                    System.out.format("Distance from %d to %d is %f\n", current, neighbour, dist);
                    if (dist < distance.get(neighbour)) {
                        distance.put(neighbour, dist);
                        previous.put(neighbour, current);
                    }
                }
            }
            current = null;
        }
//        System.out.println(unvisited);
//        System.out.println(distance);
//        System.out.println(previous);
    }
    public Path getPath(int target) {
//        System.out.format("Trying to find path from %d to %d\n",source,target);
        LinkedList<Node> path = new LinkedList<>();
        if(source == target) {
            path.addFirst(graph.getNode(target));
            return new Path(path);
        }
        if(previous.get(target) != null) {
            Integer tempTarget = target;
            while (tempTarget != null) {
                path.addFirst(graph.getNode(tempTarget));
                tempTarget = previous.get(tempTarget);
            }
        }

        return new Path(path);
    }
}
