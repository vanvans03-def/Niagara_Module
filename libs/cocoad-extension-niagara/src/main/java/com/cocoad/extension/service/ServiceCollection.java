package com.cocoad.extension.service;

import com.cocoad.extension.websocket.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ServiceCollection {

    private final Map<String, Object> maps = new HashMap<>();

    private static ServiceCollection instance;

    DefaultWebSocketServerFactory defaultWebSocketServerFactory = new DefaultWebSocketServerFactory();

    private ServiceCollection() {
        this.addSingleton(WebSocketServerFactory.class, defaultWebSocketServerFactory);
    }

    public static ServiceCollection getInstance() {
        if (instance == null) {
            instance = new ServiceCollection();
        }

        return instance;
    }

    public <T, U extends T> ServiceCollection addSingleton(Class<T> interfaceType, Class<U> implementationType) throws Exception {
        T _object = null;
        Constructor[] ctors = implementationType.getDeclaredConstructors();
        if(ctors.length != 1){
            throw new Exception("More than one constructor found " + implementationType.getName());
        }
        Constructor ctor = ctors[0];
        Parameter[] parameters = ctor.getParameters();
        if (parameters.length > 0) {
            ArrayList<Object> args = new ArrayList<>(parameters.length);
            for (Parameter parameter : parameters) {
                Class _interfaceType = parameter.getType();
                Object _dependancy = maps.get(_interfaceType.getCanonicalName());
                args.add(_dependancy);
            }
            _object = (T) ctor.newInstance(args.toArray());
        } else {
            _object = (T) ctor.newInstance();
        }
        return addSingleton(interfaceType, _object);
    }

    public <T> ServiceCollection addSingleton(Class<T> interfaceType, Object implementationInstance) {
        if(!(interfaceType.isInstance(implementationInstance))){
            throw new RuntimeException(MessageFormat.format(
                    implementationInstance.getClass().getName(), interfaceType.getName()));
        }
        maps.put(interfaceType.getCanonicalName(), implementationInstance);
        return this;
    }

    public <T> T getService(Class<T> interfaceType) {
        T instance = (T) maps.get(interfaceType.getCanonicalName());
        return instance;
    }

    public void clear(){
        maps.clear();
    }

    public <T> ServiceCollection addWebSocketServer(String name, Consumer<WebSocketServerOptions> configWebSocketServer) {
        WebSocketServerOptions options = new WebSocketServerOptions();
        configWebSocketServer.accept(options);
        DefaultWebSocketServer webSocketServer = new DefaultWebSocketServer(options);

        defaultWebSocketServerFactory.register(name, new WebSocketServerFactoryContext(webSocketServer, options));
        return this;
    }
}
