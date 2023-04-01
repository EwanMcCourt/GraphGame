package testing;

import Graph.ALGraph;
import MVC.Model.Point;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class testALGraph {

    Point point1 = new Point(1);
    Point point2 = new Point(2);
    Point point3 = new Point(3);



    ALGraph<Point> testGraph = new ALGraph();
    @Test
    public void testEmptyGetNodes(){
        assertEquals(testGraph.getNodes(), new HashSet<>());
    }
    @Test
    public void testGetNodes(){
        testGraph.addNode(point1);
        Set<Point> result = testGraph.getNodes();
        assertTrue(result.contains(point1));
    }
    @Test
    public void testGetMultipleNodes(){
        testGraph.addNode(point1);
        testGraph.addNode(point2);
        testGraph.addNode(point3);
        Set<Point> result = testGraph.getNodes();
        assertTrue(result.contains(point1));
        assertTrue(result.contains(point2));
        assertTrue(result.contains(point3));
    }
    @Test
    public void testGetWeight(){
        testGraph.addConnection(point1, point2, 20, false);
        assertEquals(testGraph.getWeight(point1, point2), 20);
        assertNotEquals(testGraph.getWeight(point2, point1), 20);
    }
    @Test
    public void testGetWeightBidirectional(){
        testGraph.addConnection(point1, point3, 44, true);
        assertEquals(testGraph.getWeight(point1, point3), 44);
        assertEquals(testGraph.getWeight(point1, point3), 44);
    }
    @Test
    public void testGetNode(){
        testGraph.addNode(point1);
        assertEquals(testGraph.getNode(1), point1);
    }
}
