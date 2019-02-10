import com.wildgroup.deck_package.Card;
import com.wildgroup.deck_package.StandardCard;
import com.wildgroup.game_package.Game;
import com.wildgroup.game_package.GameFunctionHandler;
import com.wildgroup.game_package.Pile;
import com.wildgroup.game_package.Whist;
import com.wildgroup.game_package.models.Player;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
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
        int[] gameCount = new int[1];
        gameCount[0] = 1;
        Whist g = new Whist();
        Thread t = new Thread(g);
        boolean[] giveMeInput = new boolean[1];
        giveMeInput[0] = false;
        Pile[] allpiles = new Pile[5];
        int[] calls = {0,0,0,0};
        int[] score = {0,0,0,0};
        int[] tricks = {0,0,0,0};

        char hollowHeart = '\u2661';
        String hollowDiamond = "\u2662";
        String spade = "\u2660";
        String club = "\u2663";

        Collection<Player> playerCol = new ArrayList<>();
        (playerCol).add(new Player("Martin", 0));
        (playerCol).add(new Player("Marc", 1));
        (playerCol).add(new Player("Dennis", 2));
        (playerCol).add(new Player("Mikkel", 3));
        g.setJoinedPlayers(playerCol);
//        t.start();
        g.setHandler(new GameFunctionHandler() {

            public void updateDisplay(String msg){
                StringBuilder sb = new StringBuilder();
                sb.append("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                System.out.flush();
                String format = "%s%-8s%s";
                String format2 = "  %-2s%-12s%-5s%-6s%-2s%-12s%-5s%s";
                sb.append("Game Round: " + gameCount[0] + "\n\n");
                sb.append(String.format(format2, "", "Tricks", "", "", "", "Score", "", ""));
                sb.append("\n");
                sb.append(String.format(format2, "##", "############", "#####", "#", "##", "############", "#####", "#"));
                sb.append("\n");
                for (Player p: playerCol) {
                    sb.append(String.format(format2, "#", "", "", "#", "#", "", "", "#"));
                    sb.append("\n");
                    sb.append(String.format(format2, "#", p.getName(), tricks[p.getId()], "#", "#", p.getName(), score[p.getId()], "#"));
                    sb.append("\n");
                }
                sb.append(String.format(format2, "#", "", "", "#", "#", "", "", "#"));
                sb.append("\n");
                sb.append(String.format(format2, "##", "############", "#####", "#", "##", "############", "#####", "#"));
                sb.append("\n\n");

                for (Pile p: allpiles) {
                    System.out.println("");
                    if(p.getPileOwner() == 4)
                        sb.append("\n\nGlobal Cards : \n");
                    else {
                        String call = "";
                        if(calls[p.getPileOwner()] == 0) call = "Grand";
                        if(calls[p.getPileOwner()] == 1) call = "Sol";
                        if(calls[p.getPileOwner()] == 2) call = "Grandi";
                        if(calls[p.getPileOwner()] == 3) call = "Pas";
                        sb.append(((ArrayList<Player>) playerCol).get(p.getPileOwner()).getName() + "'s Cards:     ( call: " + call +")\n");
                    }
                    for(Card c : p.getCardsInPile())
                        sb.append("#--------#  ");
                    sb.append("\n");
                    for(Card c : p.getCardsInPile()){
                        StandardCard sc = (StandardCard)c;
                        sb.append(String.format(format, "|", sc.getName().split("of ")[0], "|  "));
                    }
                    sb.append("\n");
                    for(Card c : p.getCardsInPile()){
                        StandardCard sc = (StandardCard)c;
                        sb.append("|of      |  " );
                    }
                    sb.append("\n");
                    for(Card c : p.getCardsInPile()){
                        StandardCard sc = (StandardCard)c;
                        sb.append(String.format(format, "|", sc.getName().split("of ")[1], "|  "));
                    }
                    sb.append("\n");
                    for(Card c : p.getCardsInPile())
                        sb.append("#--------#  ");
                    sb.append("\n\n");
                }

                sb.append(msg);
                sb.append("\n");


                System.out.print(sb);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void selectFromArray(String[] stringArray, int seatId) throws IOException {
                //System.out.println("User " + seatId + " is picking");
                Random r = new Random();
                //do {
                    arr[0] = r.nextInt(stringArray.length+2);
                    arr[1] = stringArray.length;
                //}
                //while(arr[0] == stringArray.length-2 ); // don't allow Grandi
                //while(arr[0] != stringArray.length-1); // only allow Pass
                //arr[0] = System.in.read();
                if(arr[0] > stringArray.length-1)
                    arr[0] = stringArray.length-1;
                calls[seatId] = arr[0] + (4-arr[1]);
                //System.out.println("User " + seatId + " picked " + stringArray[arr[0]]);
                Thread resp = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(100);
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
                updateDisplay(message);
                //System.out.println(message);
                return;
            }

            @Override
            public void selectACard(int seatId) {
                //System.out.println("User " + seatId + " is picking");
                Random r = new Random();

                arr[0] = r.nextInt(allpiles[seatId].getCardsInPile().size());
                arr[1] = allpiles[seatId].getCardsInPile().size();

                //arr[0] = System.in.read();

                //System.out.println("User " + seatId + " Selected card " + ((StandardCard)((ArrayList)allpiles[seatId].getCardsInPile()).get(arr[0])).getName());
                Thread resp = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(100);
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
                /*
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
                */
                updateDisplay("");
            }

            @Override
            public void scoreUpdate(HashMap<Integer, Integer> scoreSet) {
                //System.out.println("--------###Player SCORES###---------------------");
                for(int i = 0; i < scoreSet.size(); i++){
                    //System.out.println(i + ": " + scoreSet.get(i));
                    score[i] = scoreSet.get(i);
                }
                updateDisplay("");
                gameCount[0]++;
                //System.out.println("--------------------------------------------------------------------------");
            }

            @Override
            public void tricksUpdate(int[] tricksscore) {
                for(int i = 0; i < tricksscore.length; i++)
                    tricks[i] = tricksscore[i];
            }
        });
        t.run();



    }


}
