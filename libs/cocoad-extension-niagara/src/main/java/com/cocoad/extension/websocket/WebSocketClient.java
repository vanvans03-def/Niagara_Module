package com.cocoad.extension.websocket;

import java.util.function.Consumer;

public interface WebSocketClient {
    public void onMessage(Consumer<String> message);
    public void onOpen(Runnable callback);
    public void onError(Consumer<Exception> exception);
    public void onClose(Consumer<String> reason);
    public void send(String message);
    public void connect();
    public void reconnect();
    public void close();
}
