import com.wildgroup.game_package.Game;
import com.wildgroup.game_package.Whist;
import com.wildgroup.game_package.models.Player;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Author: Dennis F. J. Dupont
 * Date: 05-02-2019
 * Time: 13:18
 */
public class WhistTest {
    @Test
    void callTest()
    {
        Game g = new Whist();
        Thread t = new Thread(g);
        Collection<Player> playerCol = new ArrayList<>();
        ((ArrayList<Player>) playerCol).add(new Player("Martin",0));
        ((ArrayList<Player>) playerCol).add(new Player("Marc",1));
        ((ArrayList<Player>) playerCol).add(new Player("Dennis",2));
        ((ArrayList<Player>) playerCol).add(new Player("Mikkel",3));
        g.setJoinedPlayers(playerCol);
        t.start();
    }
}
