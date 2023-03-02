package MVC.View;

import MVC.Model.Point;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

public interface View {
    void initialise();
    void setIcon(String filename) throws IOException;
    void populateGraph(Set<Point> points);
    Collection<DisplayNode> getNodes();
    void setTitle(String title);
    void addNode();
    void removeNode();
    void addConnection(Point point1, Point point2, int weight);
    void highlightNode(Point point, Boolean active);
    void setHighlightColor(Point point, Color color);
    Boolean isHighlighted(Point point);
    void highlightConnection(Point point1, Point point2, Boolean active);
    void setConnectionHighlightColor(Point point1, Point point2, Color color);
    Boolean isConnectionHighlighted(Point point1, Point point2);
}
