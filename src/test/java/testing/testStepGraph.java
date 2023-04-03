package testing;

import Graph.ALGraph;
import Graph.GraphPath;
import Graph.StepGraph;
import MVC.Model.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class testStepGraph {
    @Test
    public void testGetMaxLength(){
        Point point1 = new Point(1);
        ALGraph<Point> ALgraph = new ALGraph<Point>();
        StepGraph<Point> graph1 = new StepGraph<Point>(ALgraph);
        ALgraph.addNode(point1);
        StepGraph<Point> graph2 = new StepGraph<Point>(ALgraph);
        int result = graph1.getMaxLength();
        assertEquals(result, 0);
        result = graph2.getMaxLength();
        assertEquals(result, 1);
    }
//    @Test //not working rn: runs forever
//    public void testGetPathBySizeWhenEmpty(){
//        ALGraph<Point> ALgraph = new ALGraph<Point>();
//        StepGraph<Point> graph1 = new StepGraph<Point>(ALgraph);
//        GraphPath<Point> result = graph1.getPathBySize(1);
//        System.out.println(result);
//    }
//    @Test
//    public void testGetPathBySize(){
//        Point point1 = new Point(1);
//        Point point2 = new Point(2);
//        Point point10 = new Point(10);
//        ALGraph<Point> ALgraph = new ALGraph<>();
//        ALgraph.addNode(point1);
//        ALgraph.addNode(point2);
//        ALgraph.addNode(point10);
//        ALgraph.addConnection(point1, point2, 10, false);
//        ALgraph.addConnection(point2, point10, 15, true);
//        StepGraph<Point> graph = new StepGraph<>(ALgraph);
//        GraphPath<Point> result = graph.getPathBySize(3);
//        System.out.println(result.getNodes());
//        System.out.println(result.getNodes().get(0).getIndex());
//        System.out.println(result.getNodes().get(1).getIndex());
//        System.out.println(result.getNodes().get(2).getIndex());
//        assertEquals(result.getNodes().get(2).getIndex(), 1);
//        assertEquals(result.getNodes().get(1).getIndex(), 2);
//        assertEquals(result.getNodes().get(0).getIndex(), 10);
//    }
}
