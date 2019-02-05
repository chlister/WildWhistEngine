import com.wildgroup.deck_package.Card;
import com.wildgroup.deck_package.Deck;
import com.wildgroup.deck_package.DeckFactory;
import com.wildgroup.deck_package.StandardCard;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Marc Rohwedder Kær
 * @date 04-02-2019
 */
public class DeckTest {

    @Test
    public void getDeck(){
        Deck d = new Deck();
        Assert.assertNull("No card object was created", d);
    }

    @Test
    public void shuffleDeck(){
        Deck d = new Deck();
        d.setCards(DeckFactory.getStandardDeck());
        Deck shuff = new Deck();

        shuff.shuffle();
        for (Card c: d.getCards()  ) {
            System.out.println(c.getName());
            System.out.println("Card value = " + ((StandardCard)c).getValue());
        }
        System.out.println("I am shufflin' ");
        d.shuffle();
        d.shuffle();
        System.out.println(".... Shuffle done!");
        for (Card c: d.getCards()  ) {
            System.out.println(c.getName());
        }
        Assert.assertTrue(true);
    }
}
