import com.wildgroup.deck_package.Card;
import com.wildgroup.deck_package.StandardCard;
import com.wildgroup.game_package.Game;
import com.wildgroup.game_package.GameFunctionHandler;
import com.wildgroup.game_package.Pile;
import com.wildgroup.game_package.Whist;
import com.wildgroup.game_package.models.Player;
import org.junit.Assert;
import org.junit.Test;

import java.io.Console;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
        boolean[] giveMeInput = new boolean[1];
        giveMeInput[0] = false;
        Pile[] allpiles = new Pile[5];

        Collection<Player> playerCol = new ArrayList<>();
        (playerCol).add(new Player("Martin Dealer", 0));
        (playerCol).add(new Player("Marc", 1));
        (playerCol).add(new Player("Dennis", 2));
        (playerCol).add(new Player("Mikkel", 3));
        g.setJoinedPlayers(playerCol);
//        t.start();
        g.setHandler(new GameFunctionHandler() {
            @Override
            public void selectFromArray(String[] stringArray, int seatId) throws IOException {
                System.out.println("User " + seatId + " is picking");
                Random r = new Random();
                do {
                    arr[0] = r.nextInt(stringArray.length);
                    arr[1] = stringArray.length;
                }
                //while(arr[0] == stringArray.length-2 ); // don't allow Grandi
                while(arr[0] != stringArray.length-1); // only allow Pass
                //arr[0] = System.in.read();

                System.out.println("User " + seatId + " picked " + stringArray[arr[0]]);
                Thread resp = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        g.setCallResponse(arr[0] + (4-arr[1]));
                    }
                });
                resp.start();
            }

            @Override
            public void messageDebug(String message) {
                //Assert.assertEquals("I Expected value to be \"It Works\" but it was: " + message, "It works", message);
                System.out.println(message);
                return;
            }

            @Override
            public void selectACard(int seatId) {
                System.out.println("User " + seatId + " is picking");
                Random r = new Random();

                arr[0] = r.nextInt(allpiles[seatId].getCardsInPile().size());
                arr[1] = allpiles[seatId].getCardsInPile().size();

                //arr[0] = System.in.read();

                System.out.println("User " + seatId + " Selected card " + ((StandardCard)((ArrayList)allpiles[seatId].getCardsInPile()).get(arr[0])).getName());
                Thread resp = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        try {
                            g.selectedCardResponse(seatId, (StandardCard)((ArrayList)allpiles[seatId].getCardsInPile()).get(arr[0]));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                resp.start();
            }

            @Override
            public void dealCards(Collection<Pile> piles) {

            }

            @Override
            public void updatePiles(Collection<Pile> piles) throws IOException {
                Object[] temp = piles.toArray();
                for (int i = 0; i < temp.length; i++)
                    allpiles[i] = (Pile) temp[i];
                System.out.println("--------###Piles###---------------------");
                for (Pile p: piles
                     ) {
                    System.out.println("Player " + p.getPileOwner() + " cards:");
                    for (Card c: p.getCardsInPile()
                         ) {
                        System.out.print("(" + c.getName() + ")  ");
                    }
                    System.out.println("");
                    System.out.println("----------------------------------------------------------------------");

                }
            }

            @Override
            public void scoreUpdate(HashMap<Integer, Integer> scoreSet) {
                System.out.println("--------###Player SCORES###---------------------");
                for(int i = 0; i < scoreSet.size(); i++){
                    System.out.println(i + ": " + scoreSet.get(i));
                }
                System.out.println("--------------------------------------------------------------------------");
            }
        });
        t.run();

    }
}
