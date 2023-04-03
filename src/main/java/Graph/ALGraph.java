package Graph;

import java.util.*;

public class ALGraph<N extends Node<N>> implements GraphADT<N> {
    private final Set<N> nodes;
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
    public N getNode(int index) {
        for (N node : nodes) {
            if (node.getIndex()==index) {
                return node;
            }
        }
        return null;
    }
    @Override
    public Set<N> getNodes() {
        return nodes;
    }
}


