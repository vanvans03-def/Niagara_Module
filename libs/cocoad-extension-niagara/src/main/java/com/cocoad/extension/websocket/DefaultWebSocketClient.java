package com.cocoad.extension.websocket;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class DefaultWebSocketClient extends WebSocketClient implements com.cocoad.extension.websocket.WebSocketClient {

    private static final Logger LOGGER = Logger.getLogger("cocoad-swimdclient-websocket");
    private Consumer<String> _onMessageCallback = null;
    private Consumer<String> _onCloseCallback = null;
    private Consumer<Exception> _onErrorCallback = null;
    private Runnable _onOpenCallback = null;

    public DefaultWebSocketClient(URI serverURI) throws URISyntaxException {
        super(serverURI);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        if(_onOpenCallback != null){
            _onOpenCallback.run();
        }
    }

    @Override
    public void onMessage(String message) {
        if(_onMessageCallback != null){
            _onMessageCallback.accept(message);
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        LOGGER.severe("onclose code " + code + " reason " + reason + " remote " + remote);
        if(_onCloseCallback != null) {
            _onCloseCallback.accept(reason);
        }
    }

    @Override
    public void onError(Exception ex) {
        if(_onErrorCallback != null){
            _onErrorCallback.accept(ex);
        }
    }

    @Override
    public void onMessage(Consumer<String> callback) {
        _onMessageCallback = callback;
    }

    @Override
    public void onOpen(Runnable callback) {
        _onOpenCallback = callback;
    }

    @Override
    public void onError(Consumer<Exception> callback) {
        _onErrorCallback = callback;
    }

    @Override
    public void onClose(Consumer<String> reason) {
        _onCloseCallback = reason;
    }
}
