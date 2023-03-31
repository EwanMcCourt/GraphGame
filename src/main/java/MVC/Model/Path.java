package MVC.Model;

import Graph.GraphPath;

import java.util.List;

public class Path extends GraphPath<Point> {
    public Path() {
        super();
    }
    public Path(GraphPath<Point> graphPath) {
        super(graphPath);
    }
    public List<Point> getPoints() {
        return super.getNodes();
    }
}
