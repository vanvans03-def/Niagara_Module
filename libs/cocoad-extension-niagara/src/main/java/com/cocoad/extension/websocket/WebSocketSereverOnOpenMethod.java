package com.cocoad.extension.websocket;

import org.java_websocket.WebSocket;

import java.util.UUID;

@FunctionalInterface
public interface WebSocketSereverOnOpenMethod {
    void accept(UUID uuid, WebSocket conn);
}
