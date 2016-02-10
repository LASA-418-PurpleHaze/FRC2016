package org.lasa.frc2016.input;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import org.lasa.frc2016.statics.Ports;

public class SensorInput implements Runnable {

    private static SensorInput instance;

    public static AHRS navX;
    public static DigitalInput intakeSwitch;
    public static Encoder leftSide, rightSide, armTilt, armExtension;

    private AnalogInput stringPot, distanceVal;
    private double navXCompassHeadingVal;
    private double rightSideEncoderVal, leftSideEncoderVal;
    private double armTiltPostionVal, armExtensionPositionVal;
    private double armTiltRateVal, armExtensionRateVal;
    private boolean intakeSwitchVal;

    private SensorInput() {
        navX = new AHRS(SPI.Port.kMXP);
        leftSide = new Encoder(Ports.LEFT_SIDE_A_ENCODER, Ports.LEFT_SIDE_B_ENCODER);
        rightSide = new Encoder(Ports.RIGHT_SIDE_A_ENCODER, Ports.RIGHT_SIDE_B_ENCODER);
        intakeSwitch = new DigitalInput(Ports.INTAKE_BUMP_SWITCH);
        armTilt = new Encoder(Ports.ARM_TILT_A_ENCODER, Ports.ARM_TILT_B_ENCODER);
        armExtension = new Encoder(Ports.ARM_EXTENSION_A_ENCODER, Ports.ARM_EXTENSION_B_ENCODER);
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

    public double getArmTiltPostion() {
        return armTiltPostionVal;
    }

    public double getArmExtensionPostion() {
        return armExtensionPositionVal;
    }

    public double getArmTiltRate() {
        return armTiltRateVal;
    }

    public double getArmExtensionRate() {
        return armExtensionRateVal;
    }

}
