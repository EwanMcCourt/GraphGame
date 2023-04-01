package testing;
import MVC.Model.Point;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class testPoint {

    Point testPoint = new Point(1);
    Point testPoint2 = new Point(2);

    @Test
    public void testGetWeightWithoutConnection(){
        assertEquals(testPoint.getWeight(testPoint2),  Double.POSITIVE_INFINITY, 0);


    }
    @Test

    public void testGetWeightWithConnection(){
        testPoint.addConnection(testPoint2, 100);
        assertEquals(testPoint.getWeight(testPoint2),  100, 0);
    }
    @Test
    public void testGetNeighboursWithoutConnection(){
        Set<Point> empty = new HashSet<>();
        assertEquals(testPoint.getNeighbours(), empty);
    }
    @Test public void testGetNeighboursWithConnection(){
        testPoint.addConnection(testPoint2, 100);
        Set<Point> expected = new HashSet<>();
        expected.add(testPoint2);
        assertEquals(testPoint.getNeighbours(),expected );
    }
    @Test
    public void testGetIndex(){
        assertEquals(testPoint.getIndex(), 1);
    }
}
