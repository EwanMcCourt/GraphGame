package Graph;

import java.util.*;

public class ALGraph<N extends Node> implements GraphADT<N> {
    Set<N> nodes;

    public ALGraph() {
        nodes = new HashSet<>();
    }

    @Override
    public void addNode(N node) {
        nodes.add(node);
    }

    @Override
    public void addConnection(N source, N target, int weight, Boolean bidirectional) {
        if(!nodes.contains(source)) { this.addNode(source); }
        if(!nodes.contains(target)) { this.addNode(target); }
        if(bidirectional) { target.addConnection(source, weight); }
        source.addConnection(target, weight);
    }

    @Override
    public double getWeight(N source, N target) {
        return source.getWeight(target);
    }

    public Set<N> getNodes() {
        return nodes;
    }

    public Set<N> getNeighbours(N source) {
        return (Set<N>) source.getNeighbours();
    }

    public N getNode(int index) {
        for (N node : nodes) {
            if (node.getIndex()==index) {
                return node;
            }
        }
        return null;
    }
}


