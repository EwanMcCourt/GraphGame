package MVC.Model;

import java.util.Set;

public interface Model {
    void addPoint(Point point);
    void addConnection(Point source, Point target, int weight, Boolean bidirectional);
    double getWeight(Point source, Point target);
    Point getPoint(int index);
    Path getPath(Point source, Point target);
    Set<Point> getPoints();
    Set<Point> getNeighbours(Point source);
    int getMaxPathLength();
    Path getRandomPathBySize(int size);
}
