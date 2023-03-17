package MVC.View;

import MVC.Model.Path;
import MVC.Model.Point;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

public interface View {
    void initialise();
    void setIcon(String filename) throws IOException;
    void populateGraph(Set<Point> points);
    Collection<DisplayNode> getNodes();
    void setTitle(String title);
    void addConnection(Point point1, Point point2, int weight);
    void highlightNode(Point point, Boolean active);
    void setHighlightColor(Point point, NodeColour color);
    Boolean isHighlighted(Point point);
    void highlightConnection(Point point1, Point point2, Boolean active);
    void setConnectionHighlightColor(Point point1, Point point2, ConnectionColour color);
    Boolean isConnectionHighlighted(Point point1, Point point2);
    void tempHighlightNode(Point point, Boolean active);
    void setTempHighlightColor(Point point, NodeColour color);
    Boolean isTempHighlighted(Point point);
    void tempHighlightConnection(Point point1, Point point2, Boolean active);
    void setTempConnectionHighlightColor(Point point1, Point point2, ConnectionColour color);
    Boolean isTempConnectionHighlighted(Point point1, Point point2);
    void displayPath(Path path);
    void addMenuButton(String label, EventHandler<ActionEvent> eventHandler);
    void addMenuTextField(ChangeListener<String> eventHandler);
    void addOptionsButton(String label, EventHandler<ActionEvent> eventHandler);
    void addOptionsTextField(ChangeListener<String> eventHandler);
    void setMaxDifficulty(int maxDifficulty);
    void addDifficultyEventListener(ChangeListener<Number> eventHandler);
    void clearHighlights();
    void showMoves(Path path);
    void showMoves(Point point);
    void hideMoves(Point point);
    // V Experimenting, Will Probably Remove V
    void showPathView(Path path);
}
