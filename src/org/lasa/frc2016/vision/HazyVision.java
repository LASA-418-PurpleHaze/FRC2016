package org.lasa.frc2016.vision;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.CoordinateSystem;
import com.ni.vision.NIVision.FindEdgeOptions2;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ROI;
import com.ni.vision.NIVision.Range;
import com.ni.vision.NIVision.StraightEdgeOptions;
import edu.wpi.first.wpilibj.vision.USBCamera;
import oracle.jrockit.jfr.tools.ConCatRepository;
import org.lasa.frc2016.statics.Constants;

public final class HazyVision implements Runnable { // I could swap this out with Thread

    private static HazyVision instance;

    USBCamera camera;
    Image image;
    ROI roi;
    CoordinateSystem plane;
    FindEdgeOptions2 fe02;
    StraightEdgeOptions se0;

    private Range hue;
    private Range saturation;
    private Range luminence;

    private HazyVision() {
        this.updateConstants();
        camera.openCamera();
        image = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_HSL, Constants.USBCAMERA_IMAGE_WIDTH);
        roi = NIVision.imaqCreateROI();
        plane = NIVision.imaqCalibrationSetAxisInfo(image);
        fe02 = new FindEdgeOptions2();
        se0 = new StraightEdgeOptions();
        camera.setExposureManual(30);
        camera.setSize(Constants.USBCAMERA_IMAGE_WIDTH, Constants.USBCAMERA_IMAGE_HEIGHT);
    }

    public static HazyVision getInstance() {
        return (instance == null) ? instance = new HazyVision() : instance;
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.getImage();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Meow");
            }
        }
    }

    private void getImage() {
        camera.getImage(image);
        NIVision.imaqColorThreshold(null, image, 0, NIVision.ColorMode.HSL, hue, saturation, luminence);
        NIVision.imaqFindEdge2(image, roi, plane, plane, fe02, se0);
        NIVision.imaqDrawLineOnImage(null, image, NIVision.DrawMode.DRAW_VALUE, new NIVision.Point(123, 234), new NIVision.Point(123, 567), 15);
    }

    public void updateConstants() {
        hue = new Range(Constants.HAZYVISION_HUE_LOWER_BOUND, Constants.HAZYVISION_HUE_UPPER_BOUND);
        saturation = new Range(Constants.HAZYVISION_SATURATION_LOWER_BOUND, Constants.HAZYVISION_SATURATION_UPPER_BOUND);
        luminence = new Range(Constants.HAZYVISION_LUMINENCE_LOWER_BOUND, Constants.HAZYVISION_LUMINENCE_UPPER_BOUND);
    }
}
