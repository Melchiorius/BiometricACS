package ru.boost.tools.opencv.processing;

import org.opencv.core.Mat;
import ru.boost.tools.opencv.processing.prototipe.FrameProcessor;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class VideoCapture extends FrameProcessor {
    private File file;
    private int fps = 24;
    private int period = 1000 / fps;

    private Mat currentFrame;
    private org.opencv.videoio.VideoCapture capture;

    private volatile boolean running;


    public VideoCapture(File file, int fps){
        this.file = file;
        setFPS(fps);

        currentFrame = new Mat();
        capture = new org.opencv.videoio.VideoCapture(file.getAbsolutePath());
        run();
    }

    public void setFPS(int fps){
        this.fps = fps;
        period = 1000 / fps;
    }

    @Override
    public Mat getCurrentFrame(){
        return currentFrame.clone();
    }

    private void run() {
        running = true;
        System.out.println("VideoCapture.start; file:=\""+file.getAbsolutePath()+"\"; fps:="+fps+"; period:="+period);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (running) {
                    if (capture.grab()) {
                        capture.read(currentFrame);
                    }
                    else{
                        running = false;
                    }
                } else {
                    System.out.println("VideoCapture.stopped");
                    timer.cancel(); // Остановить таймер, если running станет false
                }
            }
        }, 0, period);
    }

    @Override
    public boolean isAlive() {
        return running;
    }
}
