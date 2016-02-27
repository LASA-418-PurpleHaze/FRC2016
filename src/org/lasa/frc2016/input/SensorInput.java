package org.lasa.frc2016.input;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import org.lasa.frc2016.statics.Ports;

public class SensorInput implements Runnable {

    private static SensorInput instance;

    private static AHRS navX;
    private static Encoder leftSide, rightSide, armExtension;
    private static AnalogPotentiometer armTiltPot, armExtensionPot;
    private static DigitalInput armLimitSwitch;

    private volatile double navXAngleVal;
    private volatile double rightSideEncoderVal, leftSideEncoderVal;
    private volatile double armExtensionPositionVal;
    private volatile double armExtensionRateVal;
    private volatile boolean armLimitSwitchVal;

    private SensorInput() {
        navX = new AHRS(SPI.Port.kMXP);
        leftSide = new Encoder(Ports.LEFT_SIDE_A_ENCODER, Ports.LEFT_SIDE_B_ENCODER);
        rightSide = new Encoder(Ports.RIGHT_SIDE_A_ENCODER, Ports.RIGHT_SIDE_B_ENCODER);
        armExtension = new Encoder(Ports.ARM_EXTENSION_A_ENCODER, Ports.ARM_EXTENSION_B_ENCODER);
        armExtensionPot = new AnalogPotentiometer(Ports.ARM_EXTENSION_POTENTIOMETER);
        armLimitSwitch = new DigitalInput(Ports.ARM_LIMIT_SWITCH);
    }

    public static SensorInput getInstance() {
        return (instance == null) ? instance = new SensorInput() : instance;
    }

    public void start() {
        navX.reset();
    }

    @Override
    public void run() {
        navXAngleVal = navX.getAngle();
        leftSideEncoderVal = leftSide.get();
        rightSideEncoderVal = rightSide.get();
        armExtensionPositionVal = armExtension.get();
        armExtensionRateVal = armExtension.getRate();
        armLimitSwitchVal = armLimitSwitch.get();
    }

    public double getNavXAngle() {
        return navXAngleVal;
    }

    public double getLeftDistance() {
        return (leftSideEncoderVal / 250) * 8 * Math.PI;
    }

    public double getRightDistance() {
        return (rightSideEncoderVal / 250) * 8 * Math.PI;
    }

    public double getArmExtensionPosition() {
        return armExtensionPositionVal / 250.0 * 1.432;
    }

    public double getArmExtensionRate() {
        return -(armExtensionRateVal / 250.0 * 1.432);
    }
    
    public boolean getArmLimitSwitch() {
        return armLimitSwitchVal;
    }
}
