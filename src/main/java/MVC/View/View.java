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
    void highlightNode(Point point);
    void unHighlightNode(Point point);
    void toggleHighlightNode(Point point);
    void setHighlightColor(Point point, Color color);
    void addConnection(Point point1, Point point2, int weight);
}
