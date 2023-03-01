package Graph;

import java.util.*;

public class ALGraph implements GraphADT {
    Set<Node> nodes;

    public ALGraph() {
        nodes = new HashSet<>();
    }

    @Override
    public void addNode(Node node) {
        nodes.add(node);
    }

    @Override
    public void addConnection(Node source, Node target, int weight, Boolean bidirectional) {
        if(!nodes.contains(source)) {
            this.addNode(source);
        }
        if(!nodes.contains(target)) {
            this.addNode(target);
        }
        if(bidirectional) {
            target.addConnection(source, weight);
        }
        source.addConnection(target, weight);
    }

    @Override
    public double getWeight(Node source, Node target) {
        return source.getWeight(target);
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    public Set<Node> getNeighbours(Node source) {
        return source.getNeighbours();
    }

    public Node getNode(int index) {
        for (Node node : nodes) {
            if (node.getIndex()==index) {
                return node;
            }
        }
        return null;
    }

}


