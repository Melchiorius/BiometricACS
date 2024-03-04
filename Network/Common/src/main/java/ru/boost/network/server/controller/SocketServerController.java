package ru.boost.network.server.controller;

import ru.boost.network.common.connection.controller.AbstractConnectionController;
import ru.boost.network.common.connection.controller.CommonConnectionController;
import ru.boost.network.server.SocketServer;

import java.io.IOException;
import java.net.Socket;

public abstract class SocketServerController implements ServerController {

    private final int listeningPort;
    private SocketServer server;

    public SocketServerController(int listeningPort) {
        this.listeningPort = listeningPort;
    }

    @Override
    public int getListeningPort(){
        return listeningPort;
    }
    @Override
    public void start() throws IOException {
        server = new SocketServer(this);
        server.startListening();
    }
    @Override
    public void stop(){
        server.stopListening();
    }

    public abstract AbstractConnectionController getController(Socket socket);

}
