package ru.boost.network.server.test;

import ru.boost.tools.factory.ProcessingLevel;
import ru.boost.tools.file.VideoFileReader;
import ru.boost.tools.opencv.OpenCVInitiator;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.*;

public class VideoSender {

    private static final String filePath = "C:/Projects/test.mp4";

    public static void main(String[] args) {
        //OpenCVInitiator.getInstance();
        try {
            VideoFileReader reader = new VideoFileReader(filePath,16, ProcessingLevel.DETECTION);
            if(!reader.isAlive()){
                System.out.println("Unknown video-reader error!");
                return;
            }
            // Создание сокета
            ServerSocket serverSocket = new ServerSocket(12345); // Порт для прослушивания
            System.out.println("Ready");
            // Принятие соединения от клиента
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connection");
            DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
            for (byte[] buffer = reader.getNextFrame(); buffer != null; buffer = reader.getNextFrame()) {
                dataOutputStream.writeInt(buffer.length);
                dataOutputStream.write(buffer);
            }
            System.out.println("The end");
            // Закрытие потоков и сокета
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
