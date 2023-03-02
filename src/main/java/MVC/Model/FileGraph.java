package MVC.Model;

import Graph.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileGraph implements Model {
    GraphADT<Point> graph;

    public FileGraph(String filename) {
        graph = new ALGraph<>();
        BufferedReader input;
        System.out.println("reading file");
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
//                    System.out.format("Adding connection from %d to %d with weight %d\n",Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)));
                }
                line = input.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addPoint(Point point) {
        graph.addNode(point);
    }

    @Override
    public void addConnection(Point source, Point target, int weight, Boolean bidirectional) {
        graph.addConnection(source, target, weight, bidirectional);
    }

    @Override
    public double getWeight(Point source, Point target) {
        return source.getWeight(target);
    }

    @Override
    public Set<Point> getPoints() {
//        Set<Node> nodes = graph.getNodes();
//        Set<Point> points = new HashSet<>();
//        for (Node node : nodes) {
//            points.add((Point) node);
//        }
//        return points;
        return graph.getNodes();
    }

    @Override
    public Point getPoint(int index) {
        return graph.getNode(index);
    }

    @Override
    public Set<Point> getNeighbours(Point source) {
//        Set<Node> nodes = source.getNeighbours();
//        Set<Point> points = new HashSet<>();
//        for (Node node : nodes) {
//            points.add((Point) node);
//        }
//        return points;
        return source.getNeighbours();
    }

    @Override
    public Path<Point> getPath(Point source, Point target) {
        return new Dijkstra<>(source, graph).getPath(target);
    }
}
