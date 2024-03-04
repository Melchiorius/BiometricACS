package ru.boost.network.common.connection.controller;

import java.io.IOException;
import java.net.Socket;

public class CommonConnectionController extends AbstractConnectionController {

    public CommonConnectionController(Socket socket) {
        super(socket);
    }

    protected void readData() throws IOException {
        String message = getInputStream().readUTF();
        System.out.println("Received from client: " + message);
    }

    protected void writeData() throws IOException {
        getOutputStream().writeUTF("Hello from server");
        getOutputStream().flush();
    }
}
