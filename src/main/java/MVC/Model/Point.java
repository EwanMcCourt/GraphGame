package MVC.Model;

import Graph.Node;

import java.util.HashMap;
import java.util.Set;

public class Point implements Node<Point> {
    private final int index;
    HashMap<Point,Integer> connections = new HashMap<>();
    public Point(int index) {
        this.index = index;
    }
    public void addConnection(Point target, int weight) {
        connections.put(target, weight);
    }
    public double getWeight(Point target) {
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
    public Set<Point> getNeighbours() {
        return connections.keySet();
    }
}
