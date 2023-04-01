package testing;
import MVC.Model.Player;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
    public void TestGetGamesPlayed() {
        assertEquals(testPlayer.getGamesPlayed(), 0);
    }
    public void TestToString() {
        assertEquals(testPlayer.toString(), "test 0");
    }
}
