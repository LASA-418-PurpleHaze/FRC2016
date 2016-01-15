/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lasa.frc2016.hazyvision;

import com.ni.vision.NIVision;
import edu.wpi.first.wpilibj.vision.AxisCamera;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LASA Robotics
 */
public class HazyVision implements Runnable { // I could swap this out with Thread
    AxisCamera axis;
    NIVision.Image image;
    public HazyVision(AxisCamera axis, NIVision.Image image) {
        this.axis = axis;
        this.image = image;
        axis.writeResolution(AxisCamera.Resolution.k640x480);
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
}
