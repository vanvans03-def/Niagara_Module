package com.cocoad.extension.websocket;


import java.util.function.Consumer;

public class WebSocketServerOptions {
    public String address;
    public int port;
    public WebSocketSereverOnOpenMethod onOpen;
    public WebSocketSereverOnErrorMethod onError;
    public WebSocketSereverOnCloseMethod onClose;
    public WebSocketSereverOnMessageMethod onMessage;
}




