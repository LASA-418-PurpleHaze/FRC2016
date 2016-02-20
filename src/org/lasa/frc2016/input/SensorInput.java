package org.lasa.frc2016.input;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import org.lasa.frc2016.statics.Constants;
import org.lasa.frc2016.statics.Ports;

public class SensorInput implements Runnable {

    private static SensorInput instance;

    private static AHRS navX;
    private static DigitalInput intakeSwitch;
    private static Encoder leftSide, rightSide, armTilt, armExtension;
    private static AnalogPotentiometer armTiltPot, armExtensionPot;

    private double navXCompassHeadingVal;
    private double rightSideEncoderVal, leftSideEncoderVal;
    private double armTiltPostionVal, armExtensionPositionVal;
    private double armTiltRateVal, armExtensionRateVal;
    private double armTiltPotVal, armExtensionPotVal;
    private boolean intakeSwitchVal;

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

    @Override
    public void run() {
        navXCompassHeadingVal = navX.getCompassHeading();
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

    public double getArmTiltPosition() {
        return armTiltPostionVal - (armTiltPotVal * Constants.TILT_POT_CONVERSION.getDouble());
    }

    public double getArmExtensionPosition() {
        return armExtensionPositionVal - (armExtensionPotVal * Constants.ELEVATOR_POT_CONVERSION.getDouble());
    }

    public double getArmTiltRate() {
        return armTiltRateVal;
    }

    public double getArmExtensionRate() {
        return armExtensionRateVal;
    }
}
