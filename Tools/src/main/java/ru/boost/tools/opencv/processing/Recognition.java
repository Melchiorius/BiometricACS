package ru.boost.tools.opencv.processing;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.FaceDetectorYN;
import org.opencv.objdetect.FaceRecognizerSF;
import org.opencv.imgproc.Imgproc;

import java.io.File;

public class Recognition extends FaceDetection {
    private static double cosine_similar_threshold = 0.363;
    private static double l2norm_similar_threshold = 1.128;

    private static String testImagePath = "C:\\Projects\\Minaev.jpg";
    private static Mat testImage;

    private static String recognizeModelPath = "C:\\Projects\\BiometricACS\\Tools\\src\\main\\resources\\ru\\boost\\tools\\opencv\\model\\face_recognition_sface_2021dec.onnx";
    private static String detectionModelPath = "C:\\Projects\\BiometricACS\\Tools\\src\\main\\resources\\ru\\boost\\tools\\opencv\\model\\face_detection_yunet_2023mar.onnx";

    private FaceRecognizerSF faceRecognizer;
    private FaceDetectorYN faceDetector;
    public Recognition(File file, int fps){
        super(file,fps);
        faceRecognizer = FaceRecognizerSF.create(recognizeModelPath,"");
        faceDetector = FaceDetectorYN.create(detectionModelPath, "", new Size());
        testImage = Imgcodecs.imread(testImagePath);
    }

    @Override
    public Mat getCurrentFrame() {
        Mat frame = super.getCurrentFrame();
        MatOfRect faces = super.getFaces();
        recognition(frame, faces);
        return frame;
    }

    private void recognition(Mat frame, MatOfRect faces){
        Mat grayFrame = new Mat();

        Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist(grayFrame, grayFrame);

        if(faces != null){
            for (Rect faceRect : faces.toArray()){
                Mat face = frame.submat(faceRect);
                if(faceComparison(face,testImage)){
                    System.out.println("HOORAY!");
                }
            }
        }
    }

    /**
     * Calculating the distance between two face features
     * @param imgA
     * @param imgB
     * @return
     */
    private boolean faceComparison(Mat imgA, Mat imgB) {
        // 2.detect face from given image
        Mat faceA = new Mat();
        faceDetector.setInputSize(imgA.size());
        faceDetector.detect(imgA, faceA);
        Mat faceB = new Mat();
        faceDetector.setInputSize(imgB.size());
        faceDetector.detect(imgB, faceB);

        /*// Draw face info in img
        // Draw a face frame in imgA
        Imgproc.rectangle(imgA, new Rect((int) (faceA.get(0, 0)[0]), (int) (faceA.get(0, 1)[0]), (int) (faceA.get(0, 2)[0]), (int) (faceA.get(0, 3)[0])), new Scalar(0, 255, 0), 2);
        // Draw eyes/nose/mouth points on imgB
        Imgproc.circle(imgA, new Point(faceA.get(0, 4)[0], faceA.get(0, 5)[0]), 2, new Scalar(255, 0, 0), 2);
        Imgproc.circle(imgA, new Point(faceA.get(0, 6)[0], faceA.get(0, 7)[0]), 2, new Scalar(0, 0, 255), 2);
        Imgproc.circle(imgA, new Point(faceA.get(0, 8)[0], faceA.get(0, 9)[0]), 2, new Scalar(0, 255, 0), 2);
        Imgproc.circle(imgA, new Point(faceA.get(0, 10)[0], faceA.get(0, 11)[0]), 2, new Scalar(255, 0, 255), 2);
        Imgproc.circle(imgA, new Point(faceA.get(0, 12)[0], faceA.get(0, 13)[0]), 2, new Scalar(0, 255, 255), 2);
        // Imgcodecs.imwrite("", imgA);

        // Draw a face frame in imgB
        Imgproc.rectangle(imgB, new Rect((int) (faceB.get(0, 0)[0]), (int) (faceB.get(0, 1)[0]), (int) (faceB.get(0, 2)[0]), (int) (faceB.get(0, 3)[0])), new Scalar(0, 255, 0), 2);
        // Draw eyes/nose/mouth in imgB
        Imgproc.circle(imgB, new Point(faceB.get(0, 4)[0], faceB.get(0, 5)[0]), 2, new Scalar(255, 0, 0), 2);
        Imgproc.circle(imgB, new Point(faceB.get(0, 6)[0], faceB.get(0, 7)[0]), 2, new Scalar(0, 0, 255), 2);
        Imgproc.circle(imgB, new Point(faceB.get(0, 8)[0], faceB.get(0, 9)[0]), 2, new Scalar(0, 255, 0), 2);
        Imgproc.circle(imgB, new Point(faceB.get(0, 10)[0], faceB.get(0, 11)[0]), 2, new Scalar(255, 0, 255), 2);
        Imgproc.circle(imgB, new Point(faceB.get(0, 12)[0], faceB.get(0, 13)[0]), 2, new Scalar(0, 255, 255), 2);
        // Imgcodecs.imwrite("", imgB);*/

        // Aligning image to put face on the standard position
        Mat alignFaceA = new Mat();
        faceRecognizer.alignCrop(imgA, faceA.row(0), alignFaceA);
        Mat alignFaceB = new Mat();
        faceRecognizer.alignCrop(imgB, faceB.row(0), alignFaceB);

        // Extracting face feature from aligned image
        Mat featureA = new Mat();
        faceRecognizer.feature(alignFaceA, featureA);
        featureA = featureA.clone();
        Mat featureB = new Mat();
        faceRecognizer.feature(alignFaceB, featureB);
        featureB = featureB.clone();

        // faceRecogniz. Calculating the distance between two face features
        // get cosine similar
        double match1 = faceRecognizer.match(featureA, featureB, FaceRecognizerSF.FR_COSINE);
        // get l2norm similar
        double match2 = faceRecognizer.match(featureA, featureB, FaceRecognizerSF.FR_NORM_L2);
        if (match1 >= cosine_similar_threshold && match2 <= l2norm_similar_threshold) {
            return true;
        } else {
            return false;
        }
    }


//    private static BufferedImage readImage(String path){
//        BufferedImage bufferedImage = null;
//        try {
//            return ImageIO.read(new File(path));
//        } catch (IOException e) {
//            System.out.println("Не удалось загрузить изображение: " + path);
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private static Mat bufferedImageToMat(BufferedImage bufferedImage) {
//        if(bufferedImage == null){
//            return null;
//        }
//        Mat matImage = new Mat();
//        byte[] data = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
//        matImage.put(0, 0, data);
//        return matImage;
//    }
}
