package org.lasa.frc2016.input;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import org.lasa.frc2016.statics.Ports;

public class SensorInput {

    private static SensorInput instance;

    public static AHRS navX;
    public static DigitalInput intakeSwitch;
    public static Encoder leftSide, rightSide;

    private SensorInput() {
        navX = new AHRS(SPI.Port.kMXP);
        
        intakeSwitch = new DigitalInput(Ports.INTAKE_BUMP_SWITCH);
    }

    public static SensorInput getInstance() {
        return (instance == null) ? instance = new SensorInput() : instance;
    }

    public void run() {

    }

    public boolean getIntakeBumpSwitch() {
        return intakeSwitch.get();
    }
    
    public double getCurrentHeading() {
        return navX.getCompassHeading() * Math.PI / 180;
    }
    
    public double getLeftDistanceTraveled() {
        return leftSide.getDistance();
    }
    
    public double getLeftArcLength() {
        return Math.sqrt(Math.pow(this.getLeftDistanceTraveled() * Math.cos(Math.toRadians(this.getCurrentHeading())), 2) + Math.pow(this.getLeftDistanceTraveled() * Math.cos(Math.toRadians(this.getCurrentHeading())), 2));
    }
    
    public double getRightDistanceTraveled() {
        return rightSide.getDistance();
    }
    
    public double getRightArcLength() {
        return Math.sqrt(Math.pow(this.getRightDistanceTraveled() * Math.cos(Math.toRadians(this.getCurrentHeading())), 2) + Math.pow(this.getRightDistanceTraveled() * Math.cos(Math.toRadians(this.getCurrentHeading())), 2));
    }
    
}
