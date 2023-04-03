package testing;

import Graph.ALGraph;
import Graph.GraphPath;
import Graph.StepGraph;
import MVC.Model.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class testStepGraph {
    @Test
    public void testGetMaxLength(){
        Point point1 = new Point(1);
        ALGraph<Point> ALgraph = new ALGraph<>();
        StepGraph<Point> graph1 = new StepGraph<>(ALgraph);
        ALgraph.addNode(point1);
        StepGraph<Point> graph2 = new StepGraph<>(ALgraph);
        int result = graph1.getMaxLength();
        assertEquals(result, 0);
        result = graph2.getMaxLength();
        assertEquals(result, 1);
    }
    @Test //not working rn: runs forever
    public void testGetPathBySizeWhenEmpty(){
        ALGraph<Point> ALgraph = new ALGraph<>();
        StepGraph<Point> graph1 = new StepGraph<>(ALgraph);
        GraphPath<Point> result = graph1.getPathBySize(1);
        assertNull(result);
    }
    @Test
    public void testGetPathBySize(){
        Point p1 = new Point(1);
        Point p2 = new Point(2);
        Point p10 = new Point(10);
        ALGraph<Point> ALgraph = new ALGraph<>();
        ALgraph.addNode(p1);
        ALgraph.addNode(p2);
        ALgraph.addNode(p10);
        ALgraph.addConnection(p1, p2, 10, false);
        ALgraph.addConnection(p2, p10, 15, true);
        StepGraph<Point> graph = new StepGraph<>(ALgraph);
        GraphPath<Point> result = graph.getPathBySize(3);
        System.out.println(result.getNodes());
        System.out.println(result.getNodes().get(0).getIndex());
        System.out.println(result.getNodes().get(1).getIndex());
        System.out.println(result.getNodes().get(2).getIndex());
        assertEquals(result.getNodes().get(0).getIndex(), 1);
        assertEquals(result.getNodes().get(1).getIndex(), 2);
        assertEquals(result.getNodes().get(2).getIndex(), 10);
    }
}
