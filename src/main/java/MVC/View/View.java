package MVC.View;

import MVC.Model.Path;
import MVC.Model.Point;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public interface View {
    void initialise();
    void setIcon(String filename) throws IOException;
    void setTitle(String title);
    void clearLeaderboard();
    void populateLeaderboard(List<String> topPlayers);
    void populateGraph(Set<Point> points);
    void populateEventHandlers(Consumer<? super DisplayNode> clicked, Consumer<? super DisplayNode> hover, Consumer<? super DisplayNode> unhover);
    void addConnection(Point point1, Point point2, int weight);
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
    void addLeaderboardTextField(ChangeListener<String> eventHandler);
    void addMenuButton(String label, EventHandler<ActionEvent> eventHandler);
    void setMaxDifficulty(int maxDifficulty);
    void addDifficultyEventListener(ChangeListener<Number> eventHandler);
    void showInformationAlert(String header, String content);
    void showErrorAlert(String header, String content);
}
