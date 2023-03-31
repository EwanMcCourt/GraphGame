package MVC.Model;

import Graph.Node;

import java.util.HashMap;
import java.util.Set;

public class Point implements Node<Point> {
    private final int index;
    private final HashMap<Point,Integer> connections = new HashMap<>();
    public Point(int index) {
        this.index = index;
    }
    @Override
    public void addConnection(Point target, int weight) {
        connections.put(target, weight);
    }
    @Override
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
    @Override
    public int getIndex() {
        return index;
    }
    @Override
    public Set<Point> getNeighbours() {
        return connections.keySet();
    }
}
