package org.lasa.frc2016.hazyvision;

import com.ni.vision.NIVision;
import edu.wpi.first.wpilibj.vision.AxisCamera;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HazyVision implements Runnable { // I could swap this out with Thread
    AxisCamera axis;
    NIVision.Image image;
    public HazyVision(AxisCamera axis, NIVision.Image image) {
        this.axis = axis;
        this.image = image;
        this.axis.writeResolution(AxisCamera.Resolution.k640x480);
        this.filterContours();
    }
    
    @Override
    public void run() {
        try {
            this.getImage();
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            System.out.println("Meow");
        }
    }
    
    private void getImage() {
        axis.getImage(image);
    }
    
    private void filterContours() {
        NIVision.imaqDrawLineOnImage(image, image, NIVision.DrawMode.DRAW_VALUE, new NIVision.Point(123, 234), new NIVision.Point(123, 567), 15);
    }
    
    private void drawRect() {
        
    }
    
    public double getDistanceFromGoal() {
        return 0;
    }
}
