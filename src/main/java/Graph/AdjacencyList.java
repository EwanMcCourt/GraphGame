package Graph;

import java.util.*;

public class AdjacencyList implements Graph{
    Map<Integer,Node> nodes;

    public AdjacencyList() {
        nodes = new HashMap<>();
    }

    public void addNode(int index) {
        nodes.put(index, new Node(index));
    }

    public void addConnection(int source, int target, int weight, Boolean bidirectional) {
        if(!nodes.containsKey(source)) {
            this.addNode(source);
        }
        if(!nodes.containsKey(target)) {
            this.addNode(target);
        }
        if(bidirectional) {
            nodes.get(target).addConnection(source, weight);
        }
        nodes.get(source).addConnection(target, weight);
    }

    public double getWeight(int source, int target) {
        return nodes.get(source).getWeight(target);
    }

    public Set<Integer> getNodes() {
        return nodes.keySet();
    }

    public Set<Integer> getNeighbours(int source) {
        return nodes.get(source).getNeighbours();
    }

    public Node getNode(int index) {
        return nodes.get(index);
    }
}

