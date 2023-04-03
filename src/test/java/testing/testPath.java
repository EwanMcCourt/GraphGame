package testing;

import Graph.ALGraph;
import Graph.GraphPath;
import Graph.Node;
import Graph.StepGraph;
import MVC.Model.Path;
import MVC.Model.Point;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class testPath {
    LinkedList<Node> list = new LinkedList<>();
    Point point1 = new Point(1);
    Point point2 = new Point(2);
    GraphPath graphPath = new GraphPath<>(list);
    Path path2 = new Path(graphPath);

    @Test
    public void testGetPoints(){
        List<Point> expected = new ArrayList<>();
        list.add(point1);
        list.add(point2);
        expected.add(point1);
        expected.add(point2);
        assertEquals(path2.getPoints(),expected);
    }

}
