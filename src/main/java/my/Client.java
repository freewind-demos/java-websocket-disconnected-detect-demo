package my;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class Client {

    public static void main(String[] args) throws Exception {
        WebSocketImpl.DEBUG = true;
        run("wss://api.fcoin.com/v2/ws");
    }

    private static long messageTime = 0;

    private static void run(String url) throws URISyntaxException, InterruptedException {

        WebSocketClient client = new WebSocketClient(new URI(url), new Draft_6455()) {
            @Override
            public void onOpen(ServerHandshake handshake) {
            }


            @Override
            public void onMessage(String message) {
                System.out.println("onMessage: " + message);
                messageTime = System.currentTimeMillis();
            }

            @Override
            public void onError(Exception ex) {
                System.out.println("onError");
                ex.printStackTrace();
            }

            @Override
            public void onWebsocketPong(WebSocket conn, Framedata f) {
                System.out.println("onWebsocketPong!!!");
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                System.out.println(String.format("onClose(code: %s, reason: %s, remote: %s)", code, reason, remote));
            }
        };

        client.connectBlocking();
        client.send("{" +
                "  \"cmd\": \"sub\", " +
                "  \"args\": [\"ticker.ftusdt\"]" +
                "}");

        //
        client.setConnectionLostTimeout(5);

        while (true) {
            System.out.println("----------------- " + (System.currentTimeMillis() - messageTime) + "ms ------------------");
            System.out.println("client.isOpen(): " + client.isOpen());
            System.out.println("client.isClosing(): " + client.isClosing());
            System.out.println("client.isClosed(): " + client.isClosed());
            Thread.sleep(1000);
            if (client.isClosed()) {
                break;
            }
        }
    }

}