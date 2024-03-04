package ru.boost.network.server;

import ru.boost.network.server.controller.DatagramServerController;
import ru.boost.network.server.controller.SocketServerController;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class DatagramServer {

    private static final int PACKET_SIZE = 65507;
    private DatagramSocket serverSocket;
    private SocketServerController serverController;
    private Thread listenerThread;
    private volatile boolean isRunning;

    public DatagramServer(DatagramServerController controller) throws SocketException {
        serverSocket = new DatagramSocket(controller.getListeningPort());
    }

    public void startListening() {
        isRunning = true;
        listenerThread = new Thread(() -> {
            try (DatagramSocket serverSocket = new DatagramSocket(serverController.getListeningPort())) {
                System.out.println("Сервер запущен. Ожидание подключений...");

                while (isRunning) {
                    // Ожидаем подключения клиента
                    DatagramPacket receivePacket = new DatagramPacket(new byte[PACKET_SIZE], PACKET_SIZE);
                    serverSocket.receive(receivePacket);
                    InetAddress clientAddress = receivePacket.getAddress();
                    int clientPort = receivePacket.getPort();
                    System.out.println("Подключен клиент с адресом: " + clientAddress + ", портом: " + clientPort);

                    // Создаем PacketBuilder для данного клиента
//                    PacketBuilder packetBuilder = new PacketBuilder(SERVER_PORT, clientAddress);
//
//                    // Запускаем новый поток для обработки клиента
//                    new Thread(() -> {
//                        try {
//                            // Отправляем данные клиенту
//                            byte[] data = "Hello, client!".getBytes(); // Ваше видео или другие данные
//                            DatagramPacket packet = packetBuilder.buildPacket(data);
//                            serverSocket.send(packet);
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        }
//
//                    }).start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        listenerThread.start();
    }

    public void stopListening() {
        isRunning = false;
        serverSocket.close();
    }
}
