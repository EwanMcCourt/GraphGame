package Graph;

import java.util.*;

public class StepGraph<N extends Node<N>> {
    private final GraphADT<N> graph;
    private final Map<N, Dijkstra<N>> dijkstras = new HashMap<>();
    private final int[][] pathLength;
    private int maxLength = 0;

    public StepGraph (GraphADT<N> graph) {
        this.graph = graph;
        pathLength = new int[graph.getNodes().size()][graph.getNodes().size()];
        ArrayList<N> list = new ArrayList<>(graph.getNodes());

        for (N node : graph.getNodes()) {
            dijkstras.put(node, new Dijkstra<>(node, graph, false));
        }

        for (int i = 0; i < list.size(); i++) {
            pathLength[i][i] = 0;
            for (int j = i; j < list.size(); j++) {
                int result = dijkstras.get(list.get(i)).getGraphPath(list.get(j)).getNodes().size();
                if (result>maxLength) {
                    maxLength = result;
                }
                pathLength[i][j] = result;
                pathLength[j][i] = result;
            }
        }
    }

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

    private ArrayList<GraphPath<N>> getPathSizeCandidates(int size) {
        ArrayList<GraphPath<N>> candidates = new ArrayList<>();
        ArrayList<N> list = new ArrayList<>(graph.getNodes());

        for (int i = 0; i < pathLength.length; i++) {
            for (int j = i; j < pathLength.length; j++) {
                if (pathLength[i][j] == size) {
                    candidates.add(dijkstras.get(list.get(i)).getGraphPath(list.get(j)));
                }
            }
        }

        return candidates;
    }

    public int getMaxLength() {
        return maxLength;
    }
}
