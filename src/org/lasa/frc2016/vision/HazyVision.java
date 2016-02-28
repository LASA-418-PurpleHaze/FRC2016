package org.lasa.frc2016.vision;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Range;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

    private int session;
    static NIVision.Image image, filteredImage;
    private NIVision.ROI roi;
    private NIVision.CoordinateSystem plane;

    private NIVision.DetectRectanglesResult detectRectangleReport;
    private NIVision.RectangleDescriptor rectDescriptor;
    private NIVision.CurveOptions curveOptions;
    private NIVision.ShapeDetectionOptions shapeDectectOptions;
    private NIVision.RangeFloat[] angleRanges = new NIVision.RangeFloat[1];
    private NIVision.RangeFloat scaleRange;
    private ArrayList<NIVision.PointFloat> cornerPoints;
    private NIVision.Rect rectangle;
    private NIVision.RGBValue rectColor;

    private float top = 0;
    private float left = Float.MAX_VALUE;

    private NIVision.Range hue, saturation, luminence;
    ;

    private int tick;
    private double distance;

    private HazyVision() {

        image
                = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_HSL,
                        Constants.USBCAMERA_IMAGE_WIDTH.getInt());
        session
                = NIVision.IMAQdxOpenCamera("cam0",
                        NIVision.IMAQdxCameraControlMode.CameraControlModeController);

        roi = NIVision.imaqCreateROI();
        plane = new NIVision.CoordinateSystem(new NIVision.PointFloat(320, 240), 0, NIVision.AxisOrientation.INDIRECT);

        rectDescriptor = new NIVision.RectangleDescriptor(20, 100, 10, 70);
        curveOptions = new NIVision.CurveOptions(NIVision.ExtractionMode.NORMAL_IMAGE, 75,
                NIVision.EdgeFilterSize.NORMAL, 25, 15, 15, 10, 1, 0);
        angleRanges[0] = new NIVision.RangeFloat(0, 45);
        scaleRange = new NIVision.RangeFloat(600, 3000);
        shapeDectectOptions = new NIVision.ShapeDetectionOptions(1, angleRanges,
                scaleRange, distance);
        cornerPoints = new ArrayList<>(4);
        rectColor = new NIVision.RGBValue(0, 255, 0, 0);
        NIVision.IMAQdxConfigureGrab(session);
        NIVision.IMAQdxStartAcquisition(session);

    }

    public static HazyVision getInstance() {
        return (instance == null) ? instance
                = new HazyVision() : instance;
    }

    public void run() {
        DriverStation.reportError("Meow", false);
        NIVision.imaqColorThreshold(image,
                image, 0, NIVision.ColorMode.HSL, hue, saturation, luminence);
        detectRectangleReport = NIVision.imaqDetectRectangles(image, rectDescriptor,
                curveOptions, shapeDectectOptions, roi);
        for (int x = 0; x < detectRectangleReport.array.length; x++) {
            for (int y = 0; y < 4; y++) {
                cornerPoints.add(detectRectangleReport.array[x].corner[y]);
            }
            for (NIVision.PointFloat pointFloat : cornerPoints) {
                if (pointFloat.y > top) {
                    top = pointFloat.y;
                }
                if (pointFloat.x < left) {
                    left = pointFloat.x;
                }
            }
            rectangle.height = (int) detectRectangleReport.array[x].height;
            rectangle.width = (int) detectRectangleReport.array[x].width;
            rectangle.top = (int) top;
            rectangle.left = (int) left;
            NIVision.imaqOverlayRect(image, rectangle, rectColor, NIVision.DrawMode.DRAW_VALUE, "Target");
        }

        CameraServer.getInstance().setImage(image);

        image.free();
    }

    private double calculate() {
        return 0;
    }

    public synchronized int getRPM() {
        return visionLookUpTable.get((int) distance);
    }

    public void pushToDashboard() {
        SmartDashboard.putNumber("C_TickCount", tick);
    }

    public void updateConstants() {
        tick = 0;
        visionLookUpTable = new ArrayList<>(11);
        try {
            BufferedReader r = new BufferedReader(new FileReader("home/admin/visiontable.txt"));
            String line;
            while ((line = r.readLine()) != null) {
                for (int x = 0; !line.equals(""); x++) {
                    visionLookUpTable.add(Integer.parseInt(line));
                }
            }
            r.close();
        } catch (FileNotFoundException ex) {
            DriverStation.reportError(ex.toString(), true);

        } catch (IOException ex) {
            Logger.getLogger(HazyVision.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        hue = new Range(Constants.HAZYVISION_HUE_LOWER_BOUND.getInt(), Constants.HAZYVISION_HUE_UPPER_BOUND.getInt());
        saturation = new Range(Constants.HAZYVISION_SATURATION_LOWER_BOUND.getInt(), Constants.HAZYVISION_SATURATION_UPPER_BOUND.getInt());
        luminence = new Range(Constants.HAZYVISION_LUMINENCE_LOWER_BOUND.getInt(), Constants.HAZYVISION_LUMINENCE_UPPER_BOUND.getInt());
    }
}
