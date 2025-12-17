package com.cocoad.extension.websocket;

import java.util.function.Consumer;

public class WebSocketServerFactoryContext {
    WebSocketServer server;
    WebSocketServerOptions options;

    public WebSocketServerFactoryContext(WebSocketServer server, WebSocketServerOptions options){
        this.server = server;
        this.options = options;
    }
}
