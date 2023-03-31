package Graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GraphPath<N extends Node<N>> {
    private final List<N> nodes;
    private final List<Double> weights;

    public GraphPath() {
        this.nodes = new ArrayList<>();
        this.weights = new ArrayList<>();
    }
    public GraphPath(LinkedList<N> nodes) {
        this.nodes = nodes;
        this.weights = new ArrayList<>();
        if(nodes.isEmpty()) {
            return;
        }

        for (int i=0; i<nodes.size()-1; i++) {
            weights.add(nodes.get(i).getWeight(nodes.get(i+1)));

            if(weights.get(i) == Double.POSITIVE_INFINITY) {
                break;
            }
        }
    }
    public GraphPath(GraphPath<N> graphPath) {
        this.nodes = graphPath.getNodes();
        this.weights = graphPath.getWeights();

    }
    public N getFirst() {
        return nodes.get(0);
    }
    public N getLast() {
        return nodes.get(nodes.size()-1);
    }
    public int size() {
        return nodes.size();
    }
    public N get(int index) {
        return nodes.get(index);
    }
    public List<N> getNodes() {
        return nodes;
    }
    public List<Double> getWeights() {
        return weights;
    }
    public void addLast(N node) {
        if (!nodes.isEmpty()){
            weights.add(getLast().getWeight(node));
        }
        nodes.add(node);
    }
    public N removeLast() {
        N node = getLast();
        nodes.remove(nodes.size()-1);
        if(!nodes.isEmpty()) {
            weights.remove(weights.size()-1);
        }
        return node;
    }
    public Double getWeight() {
        return weights.stream().mapToDouble(Double::valueOf).sum();
    }
    public boolean isEmpty() {
        return nodes.isEmpty();
    }
}
