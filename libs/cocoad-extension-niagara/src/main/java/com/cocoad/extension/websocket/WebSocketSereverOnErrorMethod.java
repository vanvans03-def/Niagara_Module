package com.cocoad.extension.websocket;

import java.util.UUID;

@FunctionalInterface
public interface WebSocketSereverOnErrorMethod {
    void accept(UUID uuid);
}
