package org.lasa.frc2016.input;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import org.lasa.frc2016.statics.Ports;

public class SensorInput implements Runnable {

    private static SensorInput instance;

    private static AHRS navX;
    private static DigitalInput intakeSwitch;
    private static Encoder leftSide, rightSide, armTilt, armExtension;
    private static AnalogPotentiometer armTiltPot, armExtensionPot;

    private volatile double navXAngleVal;
    private volatile double rightSideEncoderVal, leftSideEncoderVal;
    private volatile double armTiltPostionVal, armExtensionPositionVal;
    private volatile double armTiltRateVal, armExtensionRateVal;
    private volatile double armTiltPotVal, armExtensionPotVal;
    private volatile boolean intakeSwitchVal;

    private SensorInput() {
        navX = new AHRS(SPI.Port.kMXP);
        leftSide = new Encoder(Ports.LEFT_SIDE_A_ENCODER, Ports.LEFT_SIDE_B_ENCODER);
        rightSide = new Encoder(Ports.RIGHT_SIDE_A_ENCODER, Ports.RIGHT_SIDE_B_ENCODER);
        intakeSwitch = new DigitalInput(Ports.INTAKE_BUMP_SWITCH);
        armTilt = new Encoder(Ports.ARM_TILT_A_ENCODER, Ports.ARM_TILT_B_ENCODER);
        armExtension = new Encoder(Ports.ARM_EXTENSION_A_ENCODER, Ports.ARM_EXTENSION_B_ENCODER);
        armTiltPot = new AnalogPotentiometer(Ports.ARM_TILT_POTENTIOMETER);
        armExtensionPot = new AnalogPotentiometer(Ports.ARM_EXTENSION_POTENTIOMETER);
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
        intakeSwitchVal = intakeSwitch.get();
        armTiltPostionVal = armTilt.get();
        armExtensionPositionVal = armExtension.get();
        armTiltRateVal = armTilt.getRate();
        armExtensionRateVal = armExtension.getRate();
        armTiltPotVal = armTiltPot.get();
        armExtensionPotVal = armExtensionPot.get();
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

    public boolean getIntakeSwitchValue() {
        return intakeSwitchVal;
    }

    public double getArmTiltPosition() {
        return -(armTiltPostionVal / 1000 * 120.0);
    }

    public double getArmExtensionPosition() {
        return armExtensionPositionVal / 250.0 * 1.432;
    }

    public double getArmTiltRate() {
        return -(armTiltRateVal / 1000 * 120.0);
    }

    public double getArmExtensionRate() {
        return -(armExtensionRateVal / 250.0 * 1.432);
    }

    public double getArmTiltPot() {
        return armTiltPot.get();
    }

    public double getArmExtensionPot() {
        return armExtension.get();
    }
}
