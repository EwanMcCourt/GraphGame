package Graph;

import java.util.*;

// An Adjacency List implementation of the GraphADT interface.
public class ALGraph<N extends Node<N>> implements GraphADT<N> {
    private final Set<N> nodes;

    // Create empty Adjacency List (AL) graph.
    public ALGraph() {
        nodes = new HashSet<>();
    }

    // Add a Node to the graph.
    @Override
    public void addNode(N node) {
        nodes.add(node);
    }

    // Add connection between two Nodes, adding those nodes if they're not already present on the graph.
    @Override
    public void addConnection(N source, N target, int weight, Boolean bidirectional) {
        if(!nodes.contains(source)) { this.addNode(source); }
        if(!nodes.contains(target)) { this.addNode(target); }
        if(bidirectional) { target.addConnection(source, weight); }
        source.addConnection(target, weight);
    }

    // Get a node by index.
    @Override
    public N getNode(int index) {
        for (N node : nodes) {
            if (node.getIndex()==index) {
                return node;
            }
        }
        return null;
    }

    // Return a Set of all Nodes on the graph.
    @Override
    public Set<N> getNodes() {
        return nodes;
    }
}


