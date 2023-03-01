package Graph;

import java.util.Set;

public interface Node {
    void addConnection(Node target, int weight);
    double getWeight(Node target);
    int getIndex();
    Set<Node> getNeighbours();
}
