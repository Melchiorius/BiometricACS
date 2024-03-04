package ru.boost.network.server.controller;

import com.sun.net.httpserver.HttpServer;
import ru.boost.network.common.connection.handler.rest.*;

import java.io.IOException;
import java.net.InetSocketAddress;

public class RESTServerController implements ServerController {

    private final int listeningPort;
    private HttpServer server;

    public RESTServerController(int listeningPort) {
        this.listeningPort = listeningPort;
        initRESTServerHandlers();
    }

    private void initRESTServerHandlers(){
        RESTContext.register(RESTPersonAll.getRegister());
        RESTContext.register(RESTPersonUpdate.getRegister());
        RESTContext.register(RESTPersonDelete.getRegister());
    }

    @Override
    public int getListeningPort() {
        return listeningPort;
    }

    @Override
    public void start() throws IOException {
        server = HttpServer.create(new InetSocketAddress(listeningPort), 0);
        for(RESTHandlerInitiateFunction initializer : RESTContext.getHandlers()){
            server.createContext(initializer.getPath(),initializer.getHandler());
        }
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    @Override
    public void stop() {
        server.stop(0);
    }
}
