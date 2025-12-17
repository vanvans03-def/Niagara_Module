package com.cocoad.extension.websocket;
import java.util.UUID;

@FunctionalInterface
public interface WebSocketSereverOnMessageMethod {
    void accept(UUID uuid, String message);
}