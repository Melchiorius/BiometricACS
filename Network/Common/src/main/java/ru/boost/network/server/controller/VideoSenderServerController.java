package ru.boost.network.server.controller;

import ru.boost.network.common.connection.controller.AbstractConnectionController;
import ru.boost.network.common.connection.controller.CommonConnectionController;
import ru.boost.network.common.connection.controller.VideoSenderConnectionController;
import ru.boost.network.server.SocketServer;

import java.io.IOException;
import java.net.Socket;

public class VideoSenderServerController extends SocketServerController {

    private SocketServer server;

    public VideoSenderServerController(int listeningPort) {
        super(listeningPort);
    }

    @Override
    public AbstractConnectionController getController(Socket socket){
        return new VideoSenderConnectionController(socket);
    }

}
