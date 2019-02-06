import com.wildgroup.game_package.Game;
import com.wildgroup.game_package.GameFunctionHandler;
import com.wildgroup.game_package.Pile;
import com.wildgroup.game_package.Whist;
import com.wildgroup.game_package.models.Player;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * Author: Dennis F. J. Dupont
 * Date: 05-02-2019
 * Time: 13:18
 */
public class WhistTest {
    @Test
    public void callTest() {
        int[] arr = new int[2];
        arr[0] = 0;
        arr[1] = 0;
        Whist g = new Whist();
        Thread t = new Thread(g);

        Collection<Player> playerCol = new ArrayList<>();
        (playerCol).add(new Player("Martin Dealer", 0));
        (playerCol).add(new Player("Marc", 1));
        (playerCol).add(new Player("Dennis", 2));
        (playerCol).add(new Player("Mikkel", 3));
        g.setJoinedPlayers(playerCol);
//        t.start();
        g.setHandler(new GameFunctionHandler() {
            @Override
            public void selectFromArray(String[] stringArray, Player p) {
                System.out.println("User " + p.getName() + " is picking");
                Random r = new Random();
                arr[0] = r.nextInt(stringArray.length - 1);
                arr[1] = stringArray.length;

                System.out.println("User " + p.getName() + " picked " + stringArray[arr[0]]);
                Thread resp = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        g.setCallResponse(arr[0] + (4-arr[1]));
                    }
                });
                resp.start();
            }

            @Override
            public void selectFromArray(String[] stringArray, Player p, int i) {

            }

            @Override
            public void messageDebug(String message) {
                Assert.assertEquals("I Expected value to be \"It Works\" but it was: " + message, "It works", message);
                System.out.println("Does");
                return;
            }

            @Override
            public void selectACard(int seatId) {

            }

            @Override
            public void dealCards(Collection<Pile> piles) {

            }
        });
        t.run();

    }
}
