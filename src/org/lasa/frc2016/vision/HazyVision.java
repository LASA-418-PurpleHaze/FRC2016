package org.lasa.frc2016.vision;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.CoordinateSystem;
import com.ni.vision.NIVision.FindEdgeOptions2;
import com.ni.vision.NIVision.FindEdgeReport;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.Point;
import com.ni.vision.NIVision.ROI;
import com.ni.vision.NIVision.Range;
import com.ni.vision.NIVision.StraightEdgeOptions;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.USBCamera;
import org.lasa.frc2016.statics.Constants;

public final class HazyVision implements Runnable {

    private static HazyVision instance;

    USBCamera camera;
    Image image;
    ROI roi;
    CoordinateSystem plane;
    FindEdgeOptions2 findEdgeOptions;
    StraightEdgeOptions straightEdgeOptions;
    FindEdgeReport findEdgeReport;

    private Range hue, saturation, luminence;
    private Point startPoint, endPoint;
    private int lowestX, lowestY = Integer.MAX_VALUE;
    private int highestX, highestY = 0;

    private double distance;
    private final CameraServer cameraServer;

    private HazyVision() {
        this.updateConstants();
        camera = new USBCamera();
        cameraServer = CameraServer.getInstance();
        //NIVision.IMAQdxOpenCamera("cam0", NIVision.IMAQdxCameraControlMode.CameraControlModeGuard);
        //image = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_HSL, Constants.USBCAMERA_IMAGE_WIDTH);
        //roi = NIVision.imaqCreateROI();
        //plane = NIVision.imaqCalibrationSetAxisInfo(image);
        //findEdgeOptions = new FindEdgeOptions2();
        //straightEdgeOptions = new StraightEdgeOptions();
        //camera.setExposureManual(30);
        //camera.setSize(Constants.USBCAMERA_IMAGE_WIDTH, Constants.USBCAMERA_IMAGE_HEIGHT);
        cameraServer.startAutomaticCapture(camera);
    }

    public static HazyVision getInstance() {
        return (instance == null) ? instance = new HazyVision() : instance;
    }

    @Override
    public void run() {
        while (true) {
            try {
                ;
                //this.getImage();
                distance = this.calculate();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Meow");
            }
        }
    }

    private void getImage() {
        camera.getImage(image);
        NIVision.imaqColorThreshold(null, image, 0, NIVision.ColorMode.HSL, hue, saturation, luminence);
        findEdgeReport = NIVision.imaqFindEdge2(image, roi, plane, plane, findEdgeOptions, straightEdgeOptions);
        for (NIVision.StraightEdge straightEdge : findEdgeReport.straightEdges) {
            NIVision.imaqDrawLineOnImage(null, image, NIVision.DrawMode.DRAW_VALUE,
                    startPoint = new Point((int) straightEdge.straightEdgeCoordinates.start.x, (int) straightEdge.straightEdgeCoordinates.start.y),
                    endPoint = new Point((int) straightEdge.straightEdgeCoordinates.end.x, (int) straightEdge.straightEdgeCoordinates.end.y), 15);
            if (lowestX > startPoint.x) {
                lowestX = startPoint.x;
            } else if (lowestX > endPoint.x) {
                lowestX = endPoint.x;
            }
            if (lowestY > startPoint.y) {
                lowestY = startPoint.y;
            } else if (lowestY > endPoint.y) {
                lowestY = endPoint.y;
            }
            if (highestX < startPoint.x) {
                highestX = startPoint.x;
            } else if (highestX > endPoint.x) {
                highestX = endPoint.x;
            }
            if (highestY < startPoint.y) {
                highestY = startPoint.y;
            } else if (highestY > endPoint.y) {
                highestY = endPoint.y;
            }
        }
    }

    private double calculate() {
        return 0;
    }

    public double getDistanceFromHighGoal() {
        return distance;
    }

    public void updateConstants() {
        hue = new Range(Constants.HAZYVISION_HUE_LOWER_BOUND.getInt(), Constants.HAZYVISION_HUE_UPPER_BOUND.getInt());
        saturation = new Range(Constants.HAZYVISION_SATURATION_LOWER_BOUND.getInt(), Constants.HAZYVISION_SATURATION_UPPER_BOUND.getInt());
        luminence = new Range(Constants.HAZYVISION_LUMINENCE_LOWER_BOUND.getInt(), Constants.HAZYVISION_LUMINENCE_UPPER_BOUND.getInt());
    }
}
