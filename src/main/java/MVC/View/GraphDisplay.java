package MVC.View;

import MVC.Model.Point;
import javafx.scene.paint.Color;

import java.util.Collection;
import java.util.Set;

public interface GraphDisplay {
    void populateNodes(Set<Point> points);
    Collection<DisplayNode> getDisplayNodes();
    void addConnection(Point point1, Point point2, int weight);
    void highlight(Point point, Boolean active);
    void setHighlightColor(Point point, Color color);
    Boolean isHighlighted(Point point);
    void highlightConnection(Point point1, Point point2, Boolean active);
    void setConnectionHighlightColor(Point point1, Point point2, Color color);
    Boolean isConnectionHighlighted(Point point1, Point point2);
}
