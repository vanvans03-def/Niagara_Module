package com.cocoad.extension.websocket;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

public class DefaultWebSocketServer implements com.cocoad.extension.websocket.WebSocketServer {

    private static final Logger LOGGER = Logger.getLogger("cocoad-websocket");
    private WebSocketServer mWebSocketServer = null;
    private Map<UUID, WebSocket> clientList = new HashMap<UUID, WebSocket>();

    public DefaultWebSocketServer(WebSocketServerOptions options) {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(options.address, options.port);
        mWebSocketServer = new WebSocketServer(inetSocketAddress) {
            @Override
            public void onOpen(WebSocket conn, ClientHandshake handshake) {
                UUID id = UUID.randomUUID();
                LOGGER.info("Open connection: " + conn.getRemoteSocketAddress() + " assigned id: " + id);
                conn.setAttachment(id);
                clientList.put(id, conn);
                if(options.onOpen != null){
                    options.onOpen.accept(id,conn);
                }
            }

            @Override
            public void onClose(WebSocket conn, int code, String reason, boolean remote) {
                UUID id = conn.<UUID>getAttachment();
                LOGGER.info("Close connection: " + conn.getRemoteSocketAddress() + " reason: " + reason + " id: " + id);
                clientList.remove(id);
                if(options.onClose != null){
                    options.onClose.accept(id);
                }
            }

            @Override
            public void onMessage(WebSocket conn, String message) {
                UUID id = conn.<UUID>getAttachment();
                LOGGER.info("Message: " + conn.getRemoteSocketAddress() + " id: " + id + " message: " + message);
                if(options.onMessage != null){
                    options.onMessage.accept(id,message);
                }
            }

            @Override
            public void onError(WebSocket conn, Exception ex) {
                if (conn != null) {
                    UUID id = conn.<UUID>getAttachment();
                    if(options.onError != null){
                        options.onError.accept(id);
                    }
                    LOGGER.severe(
                            "Error: " + conn.getRemoteSocketAddress() + " id: " + id + " error: " + ex.getMessage());
                } else {
                    if(options.onError != null){
                        options.onError.accept(null);
                    }
                    LOGGER.severe("Error: " + ex.getMessage());
                }
            }

            @Override
            public void onStart() {
                //LOGGER.info("Start websocket: " + address + ":" + port);
            }
        };
        mWebSocketServer.start();
    }

    public void close() throws IOException {
        try{
            mWebSocketServer.stop(5);
        } catch (InterruptedException e) {
            throw new IOException(e);
        }
    }

    @Override
    public Map<UUID, WebSocket> getClientList() {
        return clientList;
    }

    public void broadcast(String message) {
        mWebSocketServer.broadcast(message);
    }
}
