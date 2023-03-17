package Graph;

import java.util.*;

public class StepGraph<N extends Node<N>> {
    GraphADT<N> graph;
    Map<N, Dijkstra<N>> dijkstras = new HashMap<>();
    int[][] pathLength;
    int maxLength = 0;

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

//        System.out.println("StepGraph");
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(Arrays.toString(pathLength[i]));
//        }
    }

    public GraphPath<N> getPathBySize(int size) {
        ArrayList<GraphPath<N>> candidates = new ArrayList<>();
        ArrayList<N> list = new ArrayList<>(graph.getNodes());

        for (int i = 0; i < pathLength.length; i++) {
            for (int j = i; j < pathLength.length; j++) {
                if (pathLength[i][j] == size) {
                    candidates.add(dijkstras.get(list.get(i)).getGraphPath(list.get(j)));
                }
            }
        }
        return candidates.get(new Random().nextInt(candidates.size()));
    }

    public int getMaxLength() {
        return maxLength;
    }
}
