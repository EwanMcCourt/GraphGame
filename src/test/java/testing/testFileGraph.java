package testing;

import Graph.Dijkstra;

import java.util.List;
import java.util.Random;
import MVC.Model.FileGraph;
import MVC.Model.Model;
import MVC.Model.Point;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertThrows;

public class testFileGraph {
    @Test
    public void testFileGraphNormalFile() {
        Model model = new FileGraph("src/main/resources/MVC/Model/points.txt");
        assertEquals(model.getPoints().size(),50);
    }
    @Test
    public void testFileGraphEmptyFile() {

        assertThrows(IllegalArgumentException.class, () -> {
            Model model2 = new FileGraph("src/main/resources/MVC/Model/blank.txt");
        });
    }
    @Test
    public void testFileGraphNoFile() {
        assertThrows(RuntimeException.class, () -> {
            Model model3 = new FileGraph("src/main/resources/MVC/Model/doesntexist.txt");
        });
    }
}