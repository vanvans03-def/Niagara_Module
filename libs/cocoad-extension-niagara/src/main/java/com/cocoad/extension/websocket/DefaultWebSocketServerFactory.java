package com.cocoad.extension.websocket;

import org.java_websocket.WebSocket;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class DefaultWebSocketServerFactory implements WebSocketServerFactory {

    private Map<String, WebSocketServerFactoryContext> webSocketMap = new HashMap<>();

    @Override
    public WebSocketServer create(String name) {
        return webSocketMap.get(name).server;
    }

    @Override
    public WebSocketServer create(String name,
                                  WebSocketSereverOnOpenMethod onOpen,
                                  WebSocketSereverOnMessageMethod onMessage,
                                  WebSocketSereverOnErrorMethod onError,
                                  WebSocketSereverOnCloseMethod onClose) {
        WebSocketServerFactoryContext context = webSocketMap.get(name);
        context.options.onMessage = onMessage;
        context.options.onOpen = onOpen;
        context.options.onError = onError;
        context.options.onClose = onClose;
        return context.server;
    }

    public void register(String name, WebSocketServerFactoryContext context) {
        webSocketMap.put(name, context);
    }
}
