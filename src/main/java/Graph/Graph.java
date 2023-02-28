package Graph;

import java.util.Set;

public interface Graph {
    void addNode(int index);
    void addConnection(int source, int target, int weight, Boolean bidirectional);
    double getWeight(int source, int target);
    Set<Integer> getNodes();
    Node getNode(int index);
    Set<Integer> getNeighbours(int source);
}
