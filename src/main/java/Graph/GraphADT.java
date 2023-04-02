package Graph;

import java.util.Set;

public interface GraphADT<N extends Node<N>> {
    void addNode(N node);
    void addConnection(N source, N target, int weight, Boolean bidirectional);
    N getNode(int index);
    Set<N> getNodes();
}
