package testing;


import MVC.Model.FileGraph;
import MVC.Model.Model;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class testFileGraph {
    @Test
    public void testFileGraphNormalFile() {
        Model model = new FileGraph("src/main/resources/MVC/Model/points.txt");
        assertEquals(model.getPoints().size(),50);
    }
    @Test
    public void testFileGraphEmptyFile() {

        assertThrows(IllegalArgumentException.class, () -> new FileGraph("src/main/resources/MVC/Model/blank.txt"));
    }
    @Test
    public void testFileGraphNoFile() {
        assertThrows(RuntimeException.class, () -> new FileGraph("src/main/resources/MVC/Model/doesntexist.txt"));
    }
}