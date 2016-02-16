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
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.vision.USBCamera;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lasa.frc2016.statics.Constants;

public final class HazyVision implements Runnable {

    private static HazyVision instance;

    private ArrayList<Integer> visionLookUpTable;
    
    USBCamera camera;
    static Image image;
    ROI roi;
    CoordinateSystem plane;
    FindEdgeOptions2 findEdgeOptions;
    StraightEdgeOptions straightEdgeOptions;
    FindEdgeReport findEdgeReport;

    private Range hue, saturation, luminence;
    private Point startPoint, endPoint;
    private int lowestX, lowestY = Integer.MAX_VALUE;
    private int highestX, highestY = 0;
    private int midX, midY;
    private double length;

    private double distance;
    private final CameraServer cameraServer;

    private HazyVision() {
        this.updateConstants();
        camera = new USBCamera();
        cameraServer = CameraServer.getInstance();
        camera.setExposureManual(30);
        camera.setSize(Constants.USBCAMERA_IMAGE_WIDTH.getInt(), Constants.USBCAMERA_IMAGE_HEIGHT.getInt());
        //NIVision.IMAQdxOpenCamera("cam0", NIVision.IMAQdxCameraControlMode.CameraControlModeGuard);
        image = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_HSL, Constants.USBCAMERA_IMAGE_WIDTH.getInt());
        //roi = NIVision.imaqCreateROI();
        //plane = NIVision.imaqCalibrationSetAxisInfo(image);
        //findEdgeOptions = new FindEdgeOptions2();
        //straightEdgeOptions = new StraightEdgeOptions();
        
        
    }

    public static HazyVision getInstance() {
        return (instance == null) ? instance = new HazyVision() : instance;
    }

    @Override
    public void run() {
        while (true) {
            try {
                cameraServer.setImage(this.getImage());
                distance = this.calculate();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Meow");
            }
        }
    }

    private Image getImage() {
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
        return image;
    }
    
    private double calculate() {
        midX = (highestX - lowestX)/2;
        midY = (highestY - lowestY)/2;
        length = Math.sqrt(Math.pow(highestX - midX, 2) + Math.pow(highestY - midY, 2));
        return 0;
    }

    public synchronized int getRPM() {
        return visionLookUpTable.get((int) distance);
    }

    private void updateConstants() {
        visionLookUpTable = new ArrayList<>(1);
        try {
            BufferedReader r = new BufferedReader(new FileReader("VisionTable.txt"));
            String line;
            while ((line = r.readLine()) != null) {
                for(int x = 0;!line.equals(""); x++) {
                    visionLookUpTable.add(Integer.parseInt(line));
                }
            }
            r.close();
        } catch (FileNotFoundException ex) {
            DriverStation.reportError(ex.toString(), true);

        } catch (IOException ex) {
            Logger.getLogger(HazyVision.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        hue = new Range(Constants.HAZYVISION_HUE_LOWER_BOUND.getInt(), Constants.HAZYVISION_HUE_UPPER_BOUND.getInt());
        saturation = new Range(Constants.HAZYVISION_SATURATION_LOWER_BOUND.getInt(), Constants.HAZYVISION_SATURATION_UPPER_BOUND.getInt());
        luminence = new Range(Constants.HAZYVISION_LUMINENCE_LOWER_BOUND.getInt(), Constants.HAZYVISION_LUMINENCE_UPPER_BOUND.getInt());
    }
}
