package MVC.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface Model {
    double getWeight(Point source, Point target);
    Set<Point> getPoints();
    Set<Point> getNeighbours(Point source);
    int getMaxPathLength();
    Path getRandomPathBySize(int size);
    ArrayList<String> getTopTenPlayers();
    Player loadPlayer(String givenUsername);
    List<Player>  loadPlayers();
    void addPlayer(String givenUsername);
}
