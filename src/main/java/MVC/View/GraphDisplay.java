package MVC.View;

import MVC.Model.Point;

import java.util.Collection;
import java.util.Set;

public interface GraphDisplay {
    void populateNodes(Set<Point> points);
    Collection<DisplayNode> getDisplayNodes();
    void addConnection(Point point1, Point point2, int weight);
    void highlight(Point point, Boolean active);
    void setHighlightColor(Point point, NodeColour color);
    Boolean isHighlighted(Point point);
    void highlightConnection(Point point1, Point point2, Boolean active);
    void setConnectionHighlightColor(Point point1, Point point2, ConnectionColour color);
    Boolean isConnectionHighlighted(Point point1, Point point2);
    void tempHighlight(Point point, Boolean active);
    void setTempHighlightColor(Point point, NodeColour color);
    Boolean isTempHighlighted(Point point);
    void tempHighlightConnection(Point point1, Point point2, Boolean active);
    void setTempConnectionHighlightColor(Point point1, Point point2, ConnectionColour color);
    Boolean isTempConnectionHighlighted(Point point1, Point point2);

    void clearHighlights();
}
