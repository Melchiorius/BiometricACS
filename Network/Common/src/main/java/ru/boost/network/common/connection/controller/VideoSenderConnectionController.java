package ru.boost.network.common.connection.controller;

import ru.boost.network.common.businesslogic.setttings.Settings;
import ru.boost.tools.factory.ProcessingLevel;
import ru.boost.tools.file.VideoFileReader;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class VideoSenderConnectionController extends AbstractConnectionController{

    private int fps = 16;
    private VideoFileReader reader;
    private String filePath = Settings.VideoFilePath;
    public VideoSenderConnectionController(Socket socket) {
        super(socket);
        try {
            reader = new VideoFileReader(filePath, fps, ProcessingLevel.DETECTION);
        }
        catch (IOException e){
            System.out.println(e);
        }
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(!isAlive()){
                    timer.cancel();
                }
                try {

                    writeData();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }, 0, 33);
    }

    @Override
    protected void readData() throws IOException {

    }

    @Override
    protected void writeData() throws IOException {
        byte[] buffer = reader.getNextFrame();
        if(buffer != null) {
            DataOutputStream dataOutputStream = getOutputStream();
            dataOutputStream.writeInt(buffer.length);
            dataOutputStream.write(buffer);
        }
    }
}
