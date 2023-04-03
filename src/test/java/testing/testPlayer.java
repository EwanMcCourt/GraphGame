package testing;
import MVC.Model.Player;
import org.junit.jupiter.api.Test;

//import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;


public class testPlayer {
    final Player testPlayer = new Player("test", 0);
    final Player testPlayer2 = new Player("test2", 0);
    @Test
    public void TestEquals(){

        assertTrue(testPlayer.equals(testPlayer));
        assertFalse(testPlayer.equals(testPlayer2));


    }
    @Test
    public void TestGetUsername() {
        assertEquals(testPlayer.getUsername(), "test");

    }
    @Test
    public void TestToString() {
        assertEquals(testPlayer.toString(), "test 0");
    }
}
