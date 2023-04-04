package Graph;

import java.util.ArrayList;
import java.util.List;

public class GraphPath<N extends Node<N>> {
    private final List<N> nodes;
    private final List<Double> weights;

    // Create blank GraphPath to be built from scratch.
    public GraphPath() {
        this.nodes = new ArrayList<>();
        this.weights = new ArrayList<>();
    }

    // Create GraphPath from List of Nodes, like that from Dijkstra.
    public GraphPath(List<N> nodes) {
        this.nodes = nodes;
        this.weights = new ArrayList<>();
        if(nodes.isEmpty()) {
            return;
        }

        for (int i=0; i<nodes.size()-1; i++) {
            weights.add(nodes.get(i).getWeight(nodes.get(i+1)));
        }
    }

    // Copy constructor for a GraphPath.
    public GraphPath(GraphPath<N> graphPath) {
        this.nodes = graphPath.getNodes();
        this.weights = graphPath.getWeights();
    }

    // Return the first Node in the GraphPath.
    public N getFirst() {
        return nodes.get(0);
    }

    // Return the last Node in the GraphPath.
    public N getLast() {
        return nodes.get(nodes.size()-1);
    }

    // Return the size of the GraphPath.
    public int size() {
        return nodes.size();
    }

    // get the Node at a specified index in the GraphPath.
    public N get(int index) {
        return nodes.get(index);
    }

    // Return a list of all Nodes in the GraphPath.
    public List<N> getNodes() {
        return nodes;
    }

    // Return a list of all weights in the GraphPath.
    public List<Double> getWeights() {
        return weights;
    }

    // Add a Node to the end of the GraphPath.
    public void addLast(N node) {
        if (!nodes.isEmpty()){
            weights.add(getLast().getWeight(node));
        }
        nodes.add(node);
    }

    // Remove the last Node in the GraphPath
    public void removeLast() {
        if(nodes.isEmpty()){
            return;
        }
        nodes.remove(nodes.size()-1);
        if(!nodes.isEmpty()) {
            weights.remove(weights.size()-1);
        }
    }

    // Return the total weight from beginning to end of the GraphPath.
    public Double getWeight() {
        return weights.stream().mapToDouble(Double::valueOf).sum();
    }

    // Return Boolean for whether the GraphPath is empty.
    public boolean isEmpty() {
        return nodes.isEmpty();
    }
}
