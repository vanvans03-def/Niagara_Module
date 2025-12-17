package com.cocoad.extension.websocket;

import java.util.UUID;
import java.util.function.Consumer;

public interface WebSocketServerFactory {
    WebSocketServer create(String name);
    WebSocketServer create(String name,
                           WebSocketSereverOnOpenMethod onOpen,
                           WebSocketSereverOnMessageMethod onMessage,
                           WebSocketSereverOnErrorMethod onError,
                           WebSocketSereverOnCloseMethod onClose);
}
