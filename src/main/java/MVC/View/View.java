package MVC.View;

import MVC.Model.Path;
import MVC.Model.Point;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;

public interface View {
    void initialise();
    void setIcon(String filename) throws IOException;
    void setTitle(String title);
    void populateGraph(Set<Point> points);
    void populateEventHandlers(Consumer<? super DisplayNode> clicked, Consumer<? super DisplayNode> hover, Consumer<? super DisplayNode> unhover);
    Collection<DisplayNode> getNodes();
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
    void clearHighlights();
    void displayPath(Path path);
    void showMoves(Path path);
    void showMoves(Point point);
    void hideMoves(Point point);
    void setStart(Point point);
    void setGoal(Point point);
    void showPathView(Path path, String label);
    void clearPathView();
    void addLeaderboardButton(String label, EventHandler<ActionEvent> eventHandler);
    void addLoginTextField(ChangeListener<String> eventHandler);
    void addRegisterTextField(ChangeListener<String> eventHandler);
    void addMenuButton(String label, EventHandler<ActionEvent> eventHandler);
    void addMenuTextField(ChangeListener<String> eventHandler);
    void addOptionsButton(String label, EventHandler<ActionEvent> eventHandler);
    void addOptionsTextField(ChangeListener<String> eventHandler);
    void setMaxDifficulty(int maxDifficulty);
    void addDifficultyEventListener(ChangeListener<Number> eventHandler);
    void showInformationAlert(String header, String content);
    void showErrorAlert(String header, String content);
}
