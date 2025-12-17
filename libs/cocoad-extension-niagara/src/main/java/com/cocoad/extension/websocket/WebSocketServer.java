package com.cocoad.extension.websocket;

import org.java_websocket.WebSocket;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public interface WebSocketServer {
    void broadcast(String message);
    void close() throws IOException;
    Map<UUID, WebSocket> getClientList();
}
