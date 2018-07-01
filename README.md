Java-WebSocket Disconnection Detect Demo
=========================================

Java WebSocket detects disconnection. Run `my.Client`, and after several seconds, make net cable disconnected, and see the outputs.

To show debug information, use `WebSocketImpl.DEBUG = true;` in code.

Notice
------

- By default, it will cost 120s to detect connection disconnected.
- Use `client.setConnectionLostTimeout(5)` to set it to only 5 seconds 
- If the connection is OK, and `client.close()` will close the connection immediately
- If the connection is already disconnected, `client.close()` will not effect
- Client and server will send `PING` and `PONG` nearly every second automatically, we don't need to send heartbeat message by ourselves.

