package test.wildgroup.api;


import com.wildgroup.api.Client;
import com.wildgroup.api.Message;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Martin Juul Johansen
 * @date 29/01/2019
 */
public class ClientTest {
    @Test
    public void TestLogin(){
        Client c = new Client();
        System.out.println("Client ready");
        c.Login("kagehak@gmail.com", "kagehak", "1234");

    }
}
