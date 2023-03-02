package Graph;

import java.util.Set;

public interface Node<N> {
    void addConnection(N target, int weight);
    double getWeight(N target);
    int getIndex();
    Set<N> getNeighbours();
}
