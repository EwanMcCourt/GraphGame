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

public class test {
    @Test
    public void testFileGraph() {
        Model model = new FileGraph("src/main/resources/MVC/Model/points.txt");
        //Model model2 = new FileGraph("src/main/resources/MVC/Model/blank.txt");
        //Model model3 = new FileGraph("src/main/resources/MVC/Model/doesntexist.txt");
        //System.out.println(model.getPoints().size());
        // when file isn't found gives runtime exception
        // when file is empty it runs forever
        assertEquals(model.getPoints().size(),50);
    }
    @Test
    public void testGetRandomPath(){
        Model model = new FileGraph("src/main/resources/MVC/Model/points.txt");
        int n = 0;
        for (int i = 0; i < 10; i++) {
            if (model.getRandomPathBySize(4) != model.getRandomPathBySize(4)) {
                n++;
            }
        }
        assert((n/10) > 0.5);
    }

    @Test
    public void testSetDifficulty() {
        Model model = new FileGraph("src/main/resources/MVC/Model/points.txt");
        Random random = new Random();
        int n = random.nextInt(8);
        n += 3;
        assertEquals(model.getRandomPathBySize(n).size(),n);
    }
}