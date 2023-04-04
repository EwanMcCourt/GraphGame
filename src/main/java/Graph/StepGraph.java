package Graph;

import java.util.*;

// StepGraph is used to determine the longest optimal path and finding a random optimal path by path length.
public class StepGraph<N extends Node<N>> {
    private final GraphADT<N> graph;
    private final Map<N, Dijkstra<N>> dijkstras = new HashMap<>();
    private final int[][] pathLength;
    private int maxLength = 0;

    // Create a StepGraph on a GraphADT graph which uses Dijkstra to map the shortest path from every node to every other nodes and stores those distances.
    public StepGraph (GraphADT<N> graph) {
        this.graph = graph;
        pathLength = new int[graph.getNodes().size()][graph.getNodes().size()];
        ArrayList<N> list = new ArrayList<>(graph.getNodes());

        for (N node : graph.getNodes()) {
            dijkstras.put(node, new Dijkstra<>(node, graph, false));
        }

        for (int i = 0; i < list.size(); i++) {
            pathLength[i][i] = 0;
            for (int j = 0; j < list.size(); j++) {
                int result = dijkstras.get(list.get(i)).getGraphPath(list.get(j)).getNodes().size();
                if (result>maxLength) {
                    maxLength = result;
                }
                pathLength[i][j] = result;
            }
        }
    }

    // Returns a random optimal path of a specified size, if none are found the pool of candidates is widened by 1 in both directions until one is found.
    public GraphPath<N> getPathBySize(int size) {
        int upper = size, lower = size;
        ArrayList<GraphPath<N>> candidates;

        candidates = getPathSizeCandidates(size);
        while (candidates.isEmpty() && lower > 1 && upper < maxLength) {
            System.out.format("Path with size %d or %d not found.", lower, upper);
            upper++;
            lower--;
            candidates.addAll(getPathSizeCandidates(upper));
            candidates.addAll(getPathSizeCandidates(lower));
        }

        if (candidates.isEmpty()) {
            return null;
        }
        return candidates.get(new Random().nextInt(candidates.size()));
    }

    // Returns all optimal paths of a specified size, returning an empty list if none are found.
    private ArrayList<GraphPath<N>> getPathSizeCandidates(int size) {
        ArrayList<GraphPath<N>> candidates = new ArrayList<>();
        ArrayList<N> list = new ArrayList<>(graph.getNodes());

        for (int i = 0; i < pathLength.length; i++) {
            for (int j = 0; j < pathLength.length; j++) {
                if (pathLength[i][j] == size) {
                    candidates.add(dijkstras.get(list.get(i)).getGraphPath(list.get(j)));
                }
            }
        }

        return candidates;
    }

    // Return the size of the longest optimal path.
    public int getMaxLength() {
        return maxLength;
    }
}
