package ru.boost.network.server.controller;

import ru.boost.network.common.connection.controller.AbstractConnectionController;

import java.io.IOException;
import java.net.Socket;

public interface ServerController {

    int getListeningPort();

    void start() throws IOException;

    void stop();

}
