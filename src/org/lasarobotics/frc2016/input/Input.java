
package org.lasarobotics.frc2016.input;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import org.lasarobotics.frc2016.statics.Ports;

public class Input implements Runnable {

    private static Input instance;

    private static AHRS navX;
    private static Encoder leftSide, rightSide;
    private static DigitalInput armTopLimitSwitch, armBottomLimitSwitch, intakeSwitch;

    private volatile double navXAngleVal;
    private volatile double rightSideEncoderVal, leftSideEncoderVal;
    private volatile boolean armTopLimitSwitchVal, armBottomLimitSwitchVal, intakeSwitchVal;

    private Input() {
        navX = new AHRS(SPI.Port.kMXP);
        leftSide = new Encoder(Ports.LEFT_SIDE_A_ENCODER, Ports.LEFT_SIDE_B_ENCODER);
        rightSide = new Encoder(Ports.RIGHT_SIDE_A_ENCODER, Ports.RIGHT_SIDE_B_ENCODER);
        armTopLimitSwitch = new DigitalInput(Ports.ARM_TOP_LIMIT_SWITCH);
        armBottomLimitSwitch = new DigitalInput(Ports.ARM_BOTTOM_LIMIT_SWITCH);
        intakeSwitch = new DigitalInput(Ports.INTAKE_SWITCH);
    }

    public static Input getInstance() {
        return (instance == null) ? instance = new Input() : instance;
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

    public boolean getArmTopLimitSwitch() {
        return !armTopLimitSwitchVal;
    }

    public boolean getArmBottomLimitSwitch() {
        return !armBottomLimitSwitchVal;
    }
    
    public boolean getIntakeSwitch() {
        return intakeSwitchVal;
    }
}
