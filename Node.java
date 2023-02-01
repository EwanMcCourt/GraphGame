import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Node {
    private final int index;
    HashMap<Integer,Integer> connections = new HashMap<>();
    Set<Integer> neighbours = new HashSet<>();

    public Node(int index) {
        this.index = index;
    }

    public void addConnection(int target, int weight) {
        connections.put(target, weight);
        neighbours.add(target);
    }

    public double getWeight(int target) {
        if(target == index) {
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

    public Set<Integer> getNeighbours() {
        return neighbours;
    }
}
