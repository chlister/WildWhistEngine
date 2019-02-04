package test.wildgroup.message;

import com.wildgroup.message.Message;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Martin Juul Johansen
 * @date 29/01/2019
 */

public class MessageTest {
    
    @Test
    public void TestEncoding(){
        List<Message> list = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            list.add(new Message(i, "hallo world"));
        }
        Message m = new Message(100, list);
        String json = m.encode();
        System.out.println(json);
        Assert.assertFalse(json == null);
        Assert.assertFalse(json.isEmpty());
    }

    @Test
    public void TestDecoding(){
        List<Message> list = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            list.add(new Message(i, "hallo world"));
        }
        Message m = new Message(200, list);
        String json = m.encode();
        Message test = new Message(json);
        Assert.assertNotNull(test);
    }
}
