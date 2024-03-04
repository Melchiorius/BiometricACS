package ru.boost.network.common.connection.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public abstract class AbstractConnectionController implements Runnable{
    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private volatile boolean running = false;

    public AbstractConnectionController(Socket socket) {
        this.socket = socket;
        try {
            this.inputStream = new DataInputStream(socket.getInputStream());
            this.outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        running = true;
        try {
            new Thread(() ->{
                try {
                    while (running) {
                        readData();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
            new Thread(() ->{
                try {
                    while (running) {
                        writeData();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        running = false;
    }


    protected abstract void readData() throws IOException;

    protected abstract void writeData() throws IOException;

    protected DataInputStream getInputStream() {
        return inputStream;
    }

    protected DataOutputStream getOutputStream() {
        return outputStream;
    }

    public boolean isAlive(){
        return running;
    }


}
