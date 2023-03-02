package Graph;

import java.util.Set;

public interface GraphADT {
    void addNode(Node node);
    void addConnection(Node source, Node target, int weight, Boolean bidirectional);
    double getWeight(Node source, Node target);
    Node getNode(int index);
    Set<Node> getNodes();
    Set<Node> getNeighbours(Node source);
}
