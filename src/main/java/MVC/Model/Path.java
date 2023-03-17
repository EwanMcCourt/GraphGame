package MVC.Model;

import Graph.GraphPath;

import java.util.ArrayList;
import java.util.List;

public class Path extends GraphPath<Point> {
    List<Point> points;
    List<Double> weights;

    public Path() {
        points = new ArrayList<>();
        weights = new ArrayList<>();
    }
    public Path(GraphPath<Point> graphPath) {
        points = graphPath.getNodes();
        weights = new ArrayList<>();

        for (int i=0; i<points.size()-1; i++) {
            weights.add(points.get(i).getWeight(points.get(i+1)));

            if(weights.get(i) == Double.POSITIVE_INFINITY) {
                break;
            }
        }
    }

    public Point getFirst() {
        return points.get(0);
    }
    public Point getLast() {
        return points.get(points.size()-1);
    }
    public int size() {
        return points.size();
    }
    public Point get(int index) {
        return points.get(index);
    }
    public List<Point> getPoints() {
        return points;
    }
    public void addLast(Point point) {
        if (!points.isEmpty()){
            weights.add(getLast().getWeight(point));
        }
        points.add(point);
    }
    public Point removeLast() {
        Point point = getLast();
        points.remove(points.size()-1);
        if(!points.isEmpty()) {
            weights.remove(weights.size()-1);
        }
        return point;
    }
    public Double getWeight() {
        return weights.stream().mapToDouble(Double::valueOf).sum();
    }
    public void print() {
        System.out.println(points);
        if(getWeight() == Double.POSITIVE_INFINITY) {
            System.out.format("A path from %d to %d is not possible\n", getFirst().getIndex(), getLast().getIndex());
            return;
        }

        System.out.format("From %d to %d has weight %f\n", getFirst().getIndex(), getLast().getIndex(), getWeight());
        for (int i=0; i<points.size()-1; i++) {
            System.out.format("|%03d|\n  |\n %03d\n  V\n", points.get(i).getIndex(), (int) points.get(i).getWeight(points.get(i+1)));
        }
        System.out.format("|%02d|\n", points.get(points.size()-1).getIndex());
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }
}
