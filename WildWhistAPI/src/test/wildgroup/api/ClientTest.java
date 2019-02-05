package test.wildgroup.api;


import com.google.gson.Gson;
import com.wildgroup.api.Client;
import com.wildgroup.api.WebsocketClientEndpoint;
import com.wildgroup.message.Message;
import com.wildgroup.message.MessageMethods;
import org.junit.Test;

/**
 * @author Martin Juul Johansen
 * @date 29/01/2019
 */
public class ClientTest {
    @Test
    public void TestLogin() {
        Client c = new Client();
        System.out.println("Client ready");
        final int[] response = new int[1];
        response[0] = 0;
        c.addNewMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
            @Override
            public void handleMessage(String message) {
                System.out.println(message);
                Message myMessage = new Message(message);
                if(myMessage.getMethod() == MessageMethods.LOGIN)
                    response[0] = 2;
            }
        });
        c.login("kagehak@gmail.com", "1234");
        while(response[0] < 1){

        }
    }

    @Test
    public void TestCreateUser(){
        Client c = new Client();
        System.out.println("Client is ready");
        final int[] response = new int[1];
        response[0] = 0;
        c.addNewMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
            @Override
            public void handleMessage(String message) {
                System.out.println(message);
                Message myMessage = new Message(message);
                if(myMessage.getMethod() == MessageMethods.CREATEUSER)
                    response[0] = 2;
            }
        });
        c.createUser("kagehak@gmail.com", "martin", "01234");
        while(response[0] < 2){

        }

    }

    @Test
    public void TestCreateRoom(){
        Client c = new Client();
        System.out.println("Client is ready");
        final int[] response = new int[1];
        response[0] = 0;
        c.addNewMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
            @Override
            public void handleMessage(String message) {
                System.out.println(message);
                Message myMessage = new Message(message);
                if(myMessage.getMethod() == MessageMethods.CREATEROOM)
                    response[0] = 2;
            }
        });
        c.createUser("kagehak@gmail.com", "martin", "01234");
        while(response[0] < 2){

        }

    }

    @Test
    public void TestJoinRoom(){
        Client c = new Client();
        System.out.println("Client is ready");
        final int[] response = new int[1];
        response[0] = 0;
        c.addNewMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
            @Override
            public void handleMessage(String message) {
                System.out.println(message);
                Message myMessage = new Message(message);
                if(myMessage.getMethod() == MessageMethods.JOINROOM)
                    response[0] = 2;
            }
        });
        c.createUser("kagehak@gmail.com", "martin", "01234");
        while(response[0] < 2){

        }

    }
}
