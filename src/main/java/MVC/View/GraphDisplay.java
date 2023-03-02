package MVC.View;

import MVC.Model.Point;
import javafx.scene.paint.Color;

import java.util.Collection;
import java.util.Set;

public interface GraphDisplay {
    void populateNodes(Set<Point> points);
    Collection<DisplayNode> getDisplayNodes();
    void highlight(Point point);
    void unhighlight(Point point);
    void toggleHighlight(Point point);
    void setHighlightColor(Point point, Color color);
    void addConnection(Point point1, Point point2, int weight);
}
