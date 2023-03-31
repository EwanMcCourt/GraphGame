package testing;
import MVC.Model.Player;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class testPlayer {
    @Test
    public void TestEquals(){
        Player testPlayer = new Player("test", 0);
        assertEquals( testPlayer.equals(testPlayer) ,true );
        Player testPlayer2 = new Player("test2", 4);
        assertEquals( testPlayer.equals(testPlayer2) ,false );
    }
}
