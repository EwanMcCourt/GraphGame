package testing;
import MVC.Model.Player;
import org.junit.jupiter.api.Test;

//import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class testPlayer {
    Player testPlayer = new Player("test", 0);
    Player testPlayer2 = new Player("test2", 0);
    @Test
    public void TestEquals(){

        assertEquals( testPlayer.equals(testPlayer) ,true );
        assertEquals( testPlayer.equals(testPlayer2) ,false );


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
