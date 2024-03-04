package ru.boost.tools.opencv.processing;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;

import java.io.File;

public class FaceDetection extends VideoCapture {
    private CascadeClassifier faceCascade;
    private MatOfRect faces;

    public FaceDetection(File file, int fps){
        super(file,fps);
        faceCascade = new CascadeClassifier();
        faceCascade.load("Data/haarcascade_frontalface_alt.xml");
    }

    public void detect(Mat frame){
        faces = new MatOfRect();
        Mat grayFrame = new Mat();


        Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);

        Imgproc.equalizeHist(grayFrame, grayFrame);


        int absoluteFaceSize = 0;
        int height = grayFrame.rows();
        if (Math.round(height * 0.2f) > 0) {
            absoluteFaceSize = Math.round(height * 0.2f);
        }


        faceCascade.detectMultiScale(grayFrame, faces, 1.1, 2, 0 | Objdetect.CASCADE_SCALE_IMAGE,
                new Size(absoluteFaceSize, absoluteFaceSize), new Size());
    }

    public void display(Mat frame)
    {
        if(faces != null){
            Rect[] facesArray = faces.toArray();
            for (int i = 0; i < facesArray.length; i++)
                Imgproc.rectangle(frame, facesArray[i].tl(), facesArray[i].br(), new Scalar(0, 255, 0), 3);
        }
    }

    @Override
    public Mat getCurrentFrame() {
        Mat frame = super.getCurrentFrame();
        detect(frame);
        display(frame);
        return frame;
    }

    protected final MatOfRect getFaces() {
        return faces;
    }
}
