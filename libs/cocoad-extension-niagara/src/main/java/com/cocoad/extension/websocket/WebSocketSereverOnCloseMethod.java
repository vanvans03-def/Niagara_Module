package com.cocoad.extension.websocket;

import java.util.UUID;

@FunctionalInterface
public interface WebSocketSereverOnCloseMethod {
    void accept(UUID uuid);
}
