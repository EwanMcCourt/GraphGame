package MVC.Model;

import Graph.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileGraph implements Model {
    private final GraphADT<Point> graph;
    private final StepGraph<Point> stepGraph;
    public FileGraph(String filepath) {
        graph = new ALGraph<>();
        BufferedReader input;
        try {
            input = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream(filepath))));
            // Regex to interpret the provided txt file of connections.
            final Pattern p = Pattern.compile("^([0-9]+) ([0-9]+) \\{'weight': ([0-9]+)}");
            String line = input.readLine();
            if (line == null) {
                throw new IllegalArgumentException("File is empty");
            }

            while (line != null) {
                Matcher m = p.matcher(line);
                // for "1 5 {'weight': 3}"
                // "1" would be matched in m.group(1)
                // "5" would be matched in m.group(2)
                // "3" would be matched in m.group(3)
                if (m.find()) {
                    // Check if source and target indexes  are already on the graph, if not create new Points.
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
    public Set<Point> getPoints() {
        return graph.getNodes();
    }
    @Override
    public Set<Point> getNeighbours(Point source) {
        return source.getNeighbours();
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
