package org.lasarobotics.frc2016.hardware;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.VictorSP;
import org.lasarobotics.frc2016.statics.Ports;

public class Hardware implements Runnable {

    private static Hardware instance;

    public static Hardware getInstance() {
        return (instance == null) ? instance = new Hardware() : instance;
    }

    //Motors
    private VictorSP leftFrontDriveMotor, leftBackDriveMotor, rightFrontDriveMotor, rightBackDriveMotor;
    private VictorSP intakeMotor;
    private CANTalon armMotor;

    //Sensors
    private AHRS navX;
    private Encoder leftDriveEncoder, rightDriveEncoder;
    private DigitalInput armTopLimitSwitch, armBottomLimitSwitch, intakeSwitch;
    
    //States
    private volatile double navXAngle;
    
    private volatile double rightDriveEncoderPosition, leftDriveEncoderPosition;
    private volatile double rightDriveEncoderVelocity, leftDriveEncoderVelocity;
    
    private volatile boolean armTopLimitSwitchPressed, armBottomLimitSwitchPressed, intakeSwitchTriggered;

    private Hardware() {
        rightFrontDriveMotor = new VictorSP(Ports.RIGHT_FRONT_DRIVE_MOTOR);
        leftFrontDriveMotor = new VictorSP(Ports.LEFT_FRONT_DRIVE_MOTOR);
        rightBackDriveMotor = new VictorSP(Ports.RIGHT_BACK_DRIVE_MOTOR);
        leftBackDriveMotor = new VictorSP(Ports.LEFT_BACK_DRIVE_MOTOR);

        leftFrontDriveMotor.setInverted(true);
        leftBackDriveMotor.setInverted(true);

        armMotor = new CANTalon(Ports.ARM_TILTER_MASTER);
        armMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
        armMotor.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);

        
        intakeMotor = new VictorSP(Ports.INTAKE_MOTOR);
        
        navX = new AHRS(SPI.Port.kMXP);

        leftDriveEncoder = new Encoder(Ports.LEFT_DRIVE_ENCODER_A, Ports.LEFT_DRIVE_ENCODER_B);
        rightDriveEncoder = new Encoder(Ports.RIGHT_DRIVE_ENCODER_A, Ports.RIGHT_DRIVE_ENCODER_B);

        armTopLimitSwitch = new DigitalInput(Ports.ARM_TOP_LIMIT_SWITCH);
        armBottomLimitSwitch = new DigitalInput(Ports.ARM_BOTTOM_LIMIT_SWITCH);
        intakeSwitch = new DigitalInput(Ports.INTAKE_SWITCH);
    }

    public void start() {
        navX.reset();
        leftDriveEncoder.reset();
        rightDriveEncoder.reset();
    }

    @Override
    public void run() {
        navXAngle = navX.getAngle();
        
        armTopLimitSwitchPressed = armTopLimitSwitch.get();
        armBottomLimitSwitchPressed = armBottomLimitSwitch.get();
        
        intakeSwitchTriggered = intakeSwitch.get();
        
        leftDriveEncoderPosition = leftDriveEncoder.get();
        rightDriveEncoderPosition = rightDriveEncoder.get();
        leftDriveEncoderVelocity = leftDriveEncoder.getRate();
        rightDriveEncoderVelocity = rightDriveEncoder.getRate();
    }

    public void setDriveSpeeds(double leftspeed, double rightspeed) {
        rightFrontDriveMotor.set(rightspeed);
        rightBackDriveMotor.set(rightspeed);
        leftFrontDriveMotor.set(leftspeed);
        leftBackDriveMotor.set(leftspeed);
    }

    public double getArmEncoderPosition() {
        return armMotor.getEncPosition();
    }

    public double getArmEncoderVelocity() {
        return armMotor.getEncVelocity();
    }

    public double getArmOutputCurrent() {
        return armMotor.getOutputCurrent();
    }

    public void setArmMotorSpeed(double armspeed) {
        armMotor.set(armspeed);
    }

    public double getNavXAngle() {
        return navXAngle;
    }

    public double getLeftDriveDistance() {
        return -(leftDriveEncoderPosition / 250) * 8 * Math.PI;
    }

    public double getRightDriveDistance() {
        return (rightDriveEncoderPosition / 250) * 8 * Math.PI;
    }

    public double getLeftDriveVelocity() {
        return -(leftDriveEncoderVelocity / 250) * 8 * Math.PI;
    }

    public double getRightDriveVelocity() {
        return rightDriveEncoderVelocity / 250 * 8 * Math.PI;
    }

    public boolean topArmLimitPressed() {
        return !armTopLimitSwitchPressed;
    }

    public boolean bottomArmLimitPressed() {
        return !armBottomLimitSwitchPressed;
    }

    public boolean isBallInRobot() {
        return intakeSwitchTriggered;
    }
}
