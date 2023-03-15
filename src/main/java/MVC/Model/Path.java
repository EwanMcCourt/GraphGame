package MVC.Model;

import Graph.GraphPath;

import java.util.List;

public class Path {
    List<Point> points;
    double weight = Double.POSITIVE_INFINITY;
    public Path(GraphPath<Point> graphPath) {
        points = graphPath.getNodes();

        double weight = 0;
        for (int i=0; i<points.size()-1; i++) {
            double tempweight = points.get(i).getWeight(points.get(i+1));

            if(tempweight == Double.POSITIVE_INFINITY) {
                break;
            }

            weight += tempweight;
        }

        this.weight = weight;
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
        points.add(point);
    }
    public Point removeLast() {
        Point point = getLast();
        points.remove(points.size()-1);
        return point;
    }

    public void print() {
        System.out.println(points);
        if(weight == Double.POSITIVE_INFINITY) {
            System.out.format("A path from %d to %d is not possible\n", getFirst().getIndex(), getLast().getIndex());
            return;
        }

        System.out.format("From %d to %d has weight %d\n", getFirst().getIndex(), getLast().getIndex(), (int) weight);
        for (int i=0; i<points.size()-1; i++) {
            System.out.format("|%03d|\n  |\n %03d\n  V\n", points.get(i).getIndex(), (int) points.get(i).getWeight(points.get(i+1)));
        }
        System.out.format("|%02d|\n", points.get(points.size()-1).getIndex());
    }
}
