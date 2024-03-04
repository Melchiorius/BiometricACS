package ru.boost.network.server;

import ru.boost.network.common.connection.controller.AbstractConnectionController;
import ru.boost.network.server.controller.ServerController;
import ru.boost.network.server.controller.SocketServerController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer {
    private ServerSocket serverSocket;
    private ExecutorService executorService;
    private SocketServerController serverController;
    private Thread listenerThread;
    private volatile boolean isRunning;

    public SocketServer(SocketServerController controller) throws IOException {
        serverController = controller;
        serverSocket = new ServerSocket(controller.getListeningPort());
        executorService = Executors.newCachedThreadPool();
    }

    public void startListening() {
        listenerThread = new Thread(() -> {
            try {
                isRunning = true;
                while (true) {
                    System.out.println("Waiting for a client to connect...");
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());
                    AbstractConnectionController controller = serverController.getController(clientSocket);
                    if (controller != null) {
                        executorService.submit(controller::run);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        listenerThread.start();
    }

    public void stopListening() {
        isRunning = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
