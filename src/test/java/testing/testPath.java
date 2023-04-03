package testing;

import Graph.GraphPath;
import MVC.Model.Path;
import MVC.Model.Point;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class testPath {
    final LinkedList<Point> list = new LinkedList<>();
    final Point point1 = new Point(1);
    final Point point2 = new Point(2);
    final GraphPath<Point> graphPath = new GraphPath<>(list);
    final Path path2 = new Path(graphPath);

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
