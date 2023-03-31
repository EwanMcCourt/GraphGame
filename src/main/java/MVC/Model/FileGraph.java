package MVC.Model;

import Graph.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileGraph implements Model {
    private final GraphADT<Point> graph;
    private final StepGraph<Point> stepGraph;
    public FileGraph(String filename) {
        graph = new ALGraph<>();
        BufferedReader input;
        try {
            input = new BufferedReader(new FileReader(filename));
            final Pattern p = Pattern.compile("^([0-9]+) ([0-9]+) \\{'weight': ([0-9]+)}");
            String line = input.readLine();

            while (line != null) {
                Matcher m = p.matcher(line);
                if (m.find()) {
                    Point source;
                    if(graph.getNode(Integer.parseInt(m.group(1))) == null) {
                        source = new Point(Integer.parseInt(m.group(1)));
                    } else {
                        source = graph.getNode(Integer.parseInt(m.group(1)));
                    }

                    Point target;
                    if(graph.getNode(Integer.parseInt(m.group(2))) == null) {
                        target = new Point(Integer.parseInt(m.group(2)));
                    } else {
                        target = graph.getNode(Integer.parseInt(m.group(2)));
                    }

                    graph.addConnection(source, target, Integer.parseInt(m.group(3)), true);
                }
                line = input.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stepGraph = new StepGraph<>(graph);
    }
    @Override
    public double getWeight(Point source, Point target) {
        return source.getWeight(target);
    }
    @Override
    public Point getPoint(int index) {
        return graph.getNode(index);
    }
    @Override
    public Set<Point> getPoints() {
        return graph.getNodes();
    }
    @Override
    public Set<Point> getNeighbours(Point source) {
        return source.getNeighbours();
    }
    @Override
    public Path getPath(Point source, Point target) {
        return new Path(new Dijkstra<>(source, graph).getGraphPath(target));
    }
    @Override
    public int getMaxPathLength() {
        return stepGraph.getMaxLength();
    }
    @Override
    public Path getRandomPathBySize(int size) {
        return new Path(stepGraph.getPathBySize(size));
    }
    @Override
    public ArrayList<String> getTopTenPlayers() {
        return Leaderboard.getTopTenPlayers();
    }
    @Override
    public Player loadPlayer(String givenUsername) {
        return Leaderboard.loadPlayer(givenUsername);
    }
    @Override
    public List<Player> loadPlayers() {
        return Leaderboard.loadPlayers();
    }
    @Override
    public void addPlayer(String givenUsername) {
        Leaderboard.addPlayer(givenUsername);
    }
}
