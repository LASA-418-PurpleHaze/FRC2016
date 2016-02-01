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
    
    private double navXCompassHeadingVal, rightSideEncoderVal, leftSideEncoderVal;
    private boolean intakeSwitchVal;

    private SensorInput() {
        navX = new AHRS(SPI.Port.kMXP);
        leftSide = new Encoder(Ports.LEFT_SIDE_A_ENCODER, Ports.LEFT_SIDE_B_ENCODER);
        rightSide = new Encoder(Ports.RIGHT_SIDE_A_ENCODER, Ports.RIGHT_SIDE_B_ENCODER);
        intakeSwitch = new DigitalInput(Ports.INTAKE_BUMP_SWITCH);
    }

    public static SensorInput getInstance() {
        return (instance == null) ? instance = new SensorInput() : instance;
    }

    public void run() {
        navXCompassHeadingVal = navX.getCompassHeading();
        leftSideEncoderVal = leftSide.get();
        rightSideEncoderVal = rightSide.get();
        intakeSwitchVal = intakeSwitch.get();
    }
    
    public double getNavXCompassHeading() {
        return navXCompassHeadingVal;
    }
    
    public double getLeftSideValue() {
        return leftSideEncoderVal;
    }
    
    public double getRightSideValue() {
        return rightSideEncoderVal;
    }
    
    public boolean getIntakeSwitchValue() {
        return intakeSwitchVal;
    }
}
