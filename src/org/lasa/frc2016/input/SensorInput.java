
package org.lasa.frc2016.input;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import org.lasa.frc2016.statics.Ports;

public class SensorInput implements Runnable {

    private static SensorInput instance;

    private static AHRS navX;
    private static Encoder leftSide, rightSide, armExtension;
    private static DigitalInput armTopLimitSwitch, armBottomLimitSwitch, intakeSwitch;

    private volatile double navXAngleVal;
    private volatile double rightSideEncoderVal, leftSideEncoderVal;
    private volatile double armExtensionPositionVal;
    private volatile double armExtensionRateVal;
    private volatile boolean armTopLimitSwitchVal, armBottomLimitSwitchVal, intakeSwitchVal;

    private SensorInput() {
        navX = new AHRS(SPI.Port.kMXP);
        leftSide = new Encoder(Ports.LEFT_SIDE_A_ENCODER, Ports.LEFT_SIDE_B_ENCODER);
        rightSide = new Encoder(Ports.RIGHT_SIDE_A_ENCODER, Ports.RIGHT_SIDE_B_ENCODER);
        armExtension = new Encoder(Ports.ARM_EXTENSION_A_ENCODER, Ports.ARM_EXTENSION_B_ENCODER);
        armTopLimitSwitch = new DigitalInput(Ports.ARM_TOP_LIMIT_SWITCH);
        armBottomLimitSwitch = new DigitalInput(Ports.ARM_BOTTOM_LIMIT_SWITCH);
        intakeSwitch = new DigitalInput(Ports.INTAKE_SWITCH);
    }

    public static SensorInput getInstance() {
        return (instance == null) ? instance = new SensorInput() : instance;
    }

    public void start() {
        navX.reset();
        leftSide.reset();
        rightSide.reset();
    }

    @Override
    public void run() {
        navXAngleVal = navX.getAngle();
        leftSideEncoderVal = leftSide.get();
        rightSideEncoderVal = rightSide.get();
        armExtensionPositionVal = armExtension.get();
        armExtensionRateVal = armExtension.getRate();
        armTopLimitSwitchVal = armTopLimitSwitch.get();
        armBottomLimitSwitchVal = armBottomLimitSwitch.get();
        intakeSwitchVal = intakeSwitch.get();
    }

    public double getNavXAngle() {
        return navXAngleVal;
    }

    public double getLeftDistance() {
        return -(leftSideEncoderVal / 250) * 8 * Math.PI;
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

    public boolean getArmTopLimitSwitch() {
        return !armTopLimitSwitchVal;
    }

    public boolean getArmBottomLimitSwitch() {
        return !armBottomLimitSwitchVal;
    }
    
    public boolean getIntakeSwitch() {
//<<<<<<< HEAD
//        return intakeSwitchVal;
//=======
//        return !intakeSwitchVal;
//>>>>>>> test
        return true;
    }
}
