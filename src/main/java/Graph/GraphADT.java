package Graph;

import java.util.Set;

public interface GraphADT<N extends Node<N>> {
    void addNode(N node);
    void addConnection(N source, N target, int weight, Boolean bidirectional);
    double getWeight(N source, N target);
    N getNode(int index);
    Set<N> getNodes();
    Set<N> getNeighbours(N source);
}
