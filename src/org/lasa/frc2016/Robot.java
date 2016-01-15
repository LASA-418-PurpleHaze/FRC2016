package org.lasa.frc2016;

import com.ni.vision.NIVision;
import org.lasa.frc2016.lib.util.HazyIterative;
import edu.wpi.first.wpilibj.vision.AxisCamera;
import org.lasa.frc2016.ports.Ports;
import org.lasa.frc2016.hazyvision.HazyVision;

public class Robot extends HazyIterative {

    static AxisCamera axis;
    volatile NIVision.Image image;
    HazyVision hazyvision;
    Thread vision;
    @Override
    public void robotInit() {
        axis = new AxisCamera(Ports.AXIS_CAMERA_IP);
        image = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_HSL, 480);
        hazyvision = new HazyVision(axis, image);
        vision = new Thread(hazyvision);
        vision.start();
   }

    @Override
    public void autonomousInit() {
        super.autonomousInit(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void disabledInit() {
        super.disabledInit(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void testInit() {
        super.testInit(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void teleopInit() {
        super.teleopInit(); //To change body of generated methods, choose Tools | Templates.
    }
   
}
