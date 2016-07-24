
package org.lasarobotics.frc2016.input;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.VictorSP;
import org.lasarobotics.frc2016.statics.Ports;

public class Input implements Runnable {

    private static Input instance;

    private static AHRS navX;
    private static Encoder leftDriveEncoder, rightDriveEncoder;
    private static DigitalInput armTopLimitSwitch, armBottomLimitSwitch, intakeSwitch;
    private static VictorSP leftFrontDriveMotor, leftBackDriveMotor, rightFrontDriveMotor, rightBackDriveMotor;

    private volatile double navXAngleVal;
    private volatile double rightSideEncoderVal, leftSideEncoderVal;
    private volatile boolean armTopLimitSwitchVal, armBottomLimitSwitchVal, intakeSwitchVal;

    private Input() {
        rightFrontDriveMotor = new VictorSP(Ports.RIGHT_FRONT_DRIVE_MOTOR);
        leftFrontDriveMotor = new VictorSP(Ports.LEFT_FRONT_DRIVE_MOTOR);
        rightBackDriveMotor = new VictorSP(Ports.RIGHT_BACK_DRIVE_MOTOR);
        leftBackDriveMotor = new VictorSP(Ports.LEFT_BACK_DRIVE_MOTOR);
        
        leftFrontDriveMotor.setInverted(true);
        leftBackDriveMotor.setInverted(true);
        
        navX = new AHRS(SPI.Port.kMXP);
        
        leftDriveEncoder = new Encoder(Ports.LEFT_DRIVE_ENCODER_A, Ports.LEFT_DRIVE_ENCODER_B);
        rightDriveEncoder = new Encoder(Ports.RIGHT_DRIVE_ENCODER_A, Ports.RIGHT_DRIVE_ENCODER_B);
        
        armTopLimitSwitch = new DigitalInput(Ports.ARM_TOP_LIMIT_SWITCH);
        armBottomLimitSwitch = new DigitalInput(Ports.ARM_BOTTOM_LIMIT_SWITCH);
        intakeSwitch = new DigitalInput(Ports.INTAKE_SWITCH);
    }

    public static Input getInstance() {
        return (instance == null) ? instance = new Input() : instance;
    }

    public void start() {
        navX.reset();
        leftDriveEncoder.reset();
        rightDriveEncoder.reset();
    }

    @Override
    public void run() {
        navXAngleVal = navX.getAngle();
        leftSideEncoderVal = leftDriveEncoder.get();
        rightSideEncoderVal = rightDriveEncoder.get();
        armTopLimitSwitchVal = armTopLimitSwitch.get();
        armBottomLimitSwitchVal = armBottomLimitSwitch.get();
        intakeSwitchVal = intakeSwitch.get();
    }
    
    public static void setDriveSpeeds(double leftspeed, double rightspeed){
        rightFrontDriveMotor.set(rightspeed);
        rightBackDriveMotor.set(rightspeed);
        leftFrontDriveMotor.set(leftspeed);
        leftBackDriveMotor.set(leftspeed);
    }
    
    public double getNavXAngle() {
        return navXAngleVal;
    }

    public double getLeftDriveDistance() {
        return -(leftSideEncoderVal / 250) * 8 * Math.PI;
    }

    public double getRightDriveDistance() {
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
