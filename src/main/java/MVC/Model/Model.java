package MVC.Model;

import java.util.Set;

public interface Model {
    double getWeight(Point source, Point target);
    Point getPoint(int index);
    Set<Point> getPoints();
    Set<Point> getNeighbours(Point source);
    Path getPath(Point source, Point target);
    int getMaxPathLength();
    Path getRandomPathBySize(int size);
}
