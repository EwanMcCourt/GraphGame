import java.util.ArrayList;
import java.util.Set;

abstract class Graph {
    abstract void addNode(int index);
    abstract public void addConnection(int source, int target, int weight, Boolean bidirectional);
    public void addConnection(int source, int target, int weight) {
        this.addConnection(source, target, weight, true);
    }
    abstract public Double getWeight(int source, int target);
    abstract public Path getPath(int source, int target);

    abstract public Set<Integer> getNodes();

    abstract public Node getNode(int index);

    abstract public Set<Integer> getNeighbours(int source);
}
