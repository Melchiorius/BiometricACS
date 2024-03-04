package ru.boost.network.client;

import ru.boost.network.client.controller.VideoController;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private Thread inputThread;
    private Thread outputThread;
    private boolean running;

    public Client(String serverAddress, int port) {
        try {
            socket = new Socket(serverAddress, port);
            inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void start() {
        inputThread = new Thread(this::readData);
        outputThread = new Thread(this::writeData);
        running = true;
        inputThread.start();
        outputThread.start();
    }

    public void stop() {
        running = false;
        try {
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void readData() {
        try {
            while (running) {
                int dataLength = inputStream.readInt();
                // Если изображение существует, то
                if (dataLength > 0) {
                    /* принимаем изображение в виде массива байтов (пикселей). "последовательность байтов" */
                    byte[] imageData = new byte[dataLength];
                    inputStream.readFully(imageData, 0, dataLength);
                    /* Преобразуем массив пикселей в ImageIcon, изображение которое будет отображатся. */
                    ImageIcon ic = new ImageIcon(imageData);
                    VideoController.setFrame(ic);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeData() {

    }

    public static void main(String[] args) {
        Client client = new Client("localhost", 12345);
        client.start();
    }
}
