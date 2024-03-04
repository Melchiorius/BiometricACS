package ru.boost.network.common.connection.handler.rest;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RESTContext {

    private static Map<String, RESTHandlerInitiateFunction<?>> handlers = new HashMap<>();

    public static void register(RESTHandlerInitiateFunction<?> initializer){
        handlers.put(initializer.getPath(),initializer);
    }

    public static Collection<RESTHandlerInitiateFunction<?>> getHandlers(){
        return handlers.values();
    }
}
