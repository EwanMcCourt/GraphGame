package testing;

import Graph.GraphPath;
import MVC.Model.Point;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class testGraphPath {



    @Test
    public void testGetNodes(){
        LinkedList<Point> list = new LinkedList<>();
        Point point1 = new Point(1);
        Point point2 = new Point(2);
        Point point3 = new Point(3);
        Point point4 = new Point(4);
        list.add(point1);
        list.add(point2);
        list.add(point3);
        list.add(point4);
        GraphPath<Point> testPath = new GraphPath<>(list);
        assertEquals(testPath.getNodes(), list);
    }


    @Test
    public void testGetFirst(){
        LinkedList<Point> list = new LinkedList<>();
        Point point1 = new Point(1);
        Point point2 = new Point(2);
        Point point3 = new Point(3);
        Point point4 = new Point(4);
        list.add(point1);
        list.add(point2);
        list.add(point3);
        list.add(point4);
        GraphPath<Point> testPath = new GraphPath<>(list);
        assertEquals(testPath.getFirst(), point1);
    }


    @Test
    public void testGetLast(){
        LinkedList<Point> list = new LinkedList<>();
        Point point1 = new Point(1);
        Point point2 = new Point(2);
        Point point3 = new Point(3);
        Point point4 = new Point(4);
        list.add(point1);
        list.add(point2);
        list.add(point3);
        list.add(point4);
        GraphPath<Point> testPath = new GraphPath<>(list);
        assertEquals(testPath.getLast(), point4);
    }
    @Test
    public void testIsEmpty(){
        LinkedList<Point> list = new LinkedList<>();
        Point point1 = new Point(1);
        GraphPath<Point> testPath = new GraphPath<>();
        assertTrue(testPath.isEmpty());
        GraphPath<Point> testPath2 = new GraphPath<>(list);
        assertTrue(testPath2.isEmpty());
        list.add(point1);
        GraphPath<Point> testPath3 = new GraphPath<>(list);
        assertFalse(testPath3.isEmpty());
    }
    @Test
    public void testGetWeights(){
        LinkedList<Point> list = new LinkedList<>();
        Point point1 = new Point(1);
        Point point2 = new Point(2);
        GraphPath<Point> testPath = new GraphPath<>();
        List<Double> empty = new ArrayList<>();
        assertEquals(testPath.getWeights(), empty);
        list.add(point1);
        list.add(point2);
        GraphPath<Point> testPath2 = new GraphPath<>(list);
        List<Double> expected = new ArrayList<>();
        expected.add(Double.POSITIVE_INFINITY);
        assertEquals(testPath2.getWeights(), expected);
        expected.clear();
        point1.addConnection(point2, 100);
        GraphPath<Point> testPath3 = new GraphPath<>(list);
        expected.add(100.00);
        assertEquals(testPath3.getWeights(),expected);
    }
    @Test
    public void testSize(){
        LinkedList<Point> list = new LinkedList<>();
        Point point1 = new Point(1);
        Point point2 = new Point(2);
        GraphPath<Point> testPath = new GraphPath<>();
        assertEquals(testPath.size(), 0);
        GraphPath<Point> testPath2 = new GraphPath<>(list);
        assertEquals(testPath2.size(), 0);
        list.add(point1);
        assertEquals(testPath2.size(), 1);
        list.add(point2);
        assertEquals(testPath2.size(), 2);
    }
    @Test
    public void testGet(){
        LinkedList<Point> list = new LinkedList<>();
        Point point1 = new Point(1);
        Point point2 = new Point(2);
        GraphPath<Point> testPath2 = new GraphPath<>(list);
        list.add(point1);
        assertEquals(testPath2.get(0), point1);
        list.add(point2);
        assertEquals(testPath2.get(1), point2);
    }

    @Test
    public void testRemoveLast(){
        LinkedList<Point> list = new LinkedList<>();
        Point point1 = new Point(1);
        Point point2 = new Point(2);
        list.add(point1);
        list.add(point2);
        GraphPath<Point> testPath = new GraphPath<>(list);
        testPath.removeLast();
        assertTrue(testPath.getNodes().contains(point1));
        assertFalse(testPath.getNodes().contains(point2));
    }
    @Test
    public void testAddLast(){
        LinkedList<Point> list = new LinkedList<>();
        Point point1 = new Point(1);
        Point point2 = new Point(2);
        list.add(point1);
        GraphPath<Point> testPath = new GraphPath<>(list);
        testPath.addLast(point2);
        assertTrue(testPath.getNodes().contains(point1));
        assertTrue(testPath.getNodes().contains(point2));
    }


}
