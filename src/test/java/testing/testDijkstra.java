package testing;

import Graph.ALGraph;
import Graph.Dijkstra;
import Graph.GraphADT;
import Graph.StepGraph;
import MVC.Model.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class testDijkstra {


    @Test
    public void testGetGraphPath(){
        Point point1 = new Point(1);
        Point point2 = new Point(2);
        Point point3 = new Point(3);
        Point point4 = new Point(4);
        ALGraph<Point> graph = new ALGraph<Point>();
        graph.addConnection(point1, point2, 100, true);
        graph.addConnection(point2, point4, 1, false);
        graph.addConnection(point1, point3, 2, true);
        graph.addConnection(point3, point4, 100, false);
        Dijkstra<Point> test = new Dijkstra<Point>(point1,graph, false);
        assertEquals(test.getGraphPath(point4).getNodes().get(0).getIndex(), 1);
        assertEquals(test.getGraphPath(point4).getNodes().get(1).getIndex(), 2);
        assertEquals(test.getGraphPath(point4).getNodes().get(2).getIndex(), 4);
    }
    @Test
    public void testGetGraphPathWithUniformCost(){
        Point point1 = new Point(1);
        Point point2 = new Point(2);
        Point point3 = new Point(3);
        Point point4 = new Point(4);
        ALGraph<Point> graph = new ALGraph<Point>();
        graph.addConnection(point1, point2, 100, true);
        graph.addConnection(point2, point4, 1, false);
        graph.addConnection(point1, point3, 2, true);
        graph.addConnection(point3, point4, 100, false);
        Dijkstra<Point> test = new Dijkstra<Point>(point1,graph, true);
        assertEquals(test.getGraphPath(point4).getNodes().get(0).getIndex(), 1);
        assertEquals(test.getGraphPath(point4).getNodes().get(1).getIndex(), 3);
        assertEquals(test.getGraphPath(point4).getNodes().get(2).getIndex(), 4);
    }
}
