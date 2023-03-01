package Graph;

import java.util.HashMap;
import java.util.Set;

public class Point implements Node {
    private final int index;
    HashMap<Node,Integer> connections = new HashMap<>();

    public Point(int index) {
        this.index = index;
    }

    public void addConnection(Node target, int weight) {
        connections.put(target, weight);
    }

    public double getWeight(Node target) {
        if(target == this) {
            return 0;
        }
        if(connections.containsKey(target)) {
            return connections.get(target);
        } else {
            return Double.POSITIVE_INFINITY;
        }
    }

    public int getIndex() {
        return index;
    }

    public Set<Node> getNeighbours() {
        return connections.keySet();
    }
}
