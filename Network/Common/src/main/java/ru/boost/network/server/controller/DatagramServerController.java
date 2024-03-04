package ru.boost.network.server.controller;

import ru.boost.network.common.connection.controller.AbstractConnectionController;
import ru.boost.network.common.connection.controller.CommonConnectionController;
import ru.boost.network.server.DatagramServer;

import java.io.IOException;
import java.net.Socket;

public class DatagramServerController implements ServerController{
    private final int listeningPort;
    private DatagramServer server;

    public DatagramServerController(int listeningPort) {
        this.listeningPort = listeningPort;
    }

    @Override
    public int getListeningPort(){
        return listeningPort;
    }
    @Override
    public void start() throws IOException {
        server = new DatagramServer(this);
        server.startListening();
    }
    @Override
    public void stop(){
        server.stopListening();
    }

    public AbstractConnectionController getController(Socket socket){
        return new CommonConnectionController(socket);
    }
}
