package ru.boost.tools.opencv.processing.prototipe;


import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import ru.boost.tools.opencv.OpenCVInitiator;

public abstract class FrameProcessor implements FrameProvider{


    public FrameProcessor() {
        OpenCVInitiator.getInstance();
    }

    @Override
    public final byte[] getNextFrame() {
        Mat frame = getCurrentFrame();
        if(frame != null) {
            MatOfByte buf = new MatOfByte();
            Imgcodecs.imencode(".png", frame, buf);
            return buf.toArray();
        }
        return new byte[0];
    }

    protected abstract Mat getCurrentFrame();
}
