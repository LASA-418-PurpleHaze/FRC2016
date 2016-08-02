package org.lasarobotics.frc2016.input;

import org.lasarobotics.frc2016.statics.Constants;
import org.lasarobotics.frc2016.subsystem.Arm;
import org.lasarobotics.frc2016.subsystem.Drivetrain;
import org.lasarobotics.frc2016.subsystem.Intake;
import org.lasarobotics.lib.CheesyDriveHelper;
import org.lasarobotics.lib.HazyJoystick;

public class DriverInput implements Runnable {

    private static DriverInput instance;

    public static DriverInput getInstance() {
        return (instance == null) ? instance = new DriverInput() : instance;
    }

    private HazyJoystick driver = new HazyJoystick(0, 0.15);
    private HazyJoystick operator = new HazyJoystick(1, 0.15);

    private CheesyDriveHelper cheesyDrive;

    private Drivetrain drivetrain;
    private Intake intake;
    private Arm arm;

    private boolean overrideMode;

    private double throttle, wheel;
    private boolean quickTurn;

    private DriverInput() {
        drivetrain = Drivetrain.getInstance();
        intake = Intake.getInstance();
        arm = Arm.getInstance();

        cheesyDrive = new CheesyDriveHelper();
    }

    @Override
    public void run() {
        if (operator.getStart()) {
            overrideMode = false;
        } else if (operator.getBack()) {
            overrideMode = true;
        }
        
        drivetrainControl();
        intakeControl();
        armControl();
    }

    private void drivetrainControl() {
        throttle = -driver.getLeftY();
        wheel = driver.getRightX();
        quickTurn = driver.getRightBumper();
        
        cheesyDrive.cheesyDrive(throttle, wheel, quickTurn);
        
        drivetrain.setDriveSpeeds(cheesyDrive.getLeftPWM(), cheesyDrive.getRightPWM());
    }

    private void intakeControl() {
        if (operator.getRightBumper()) {
            intake.setMode(Intake.Mode.INTAKING);
        } else if (operator.getLeftBumper() || driver.getLeftBumper()) {
            intake.setMode(Intake.Mode.OUTTAKING);
        } else {
            intake.setMode(Intake.Mode.OFF);
        }
    }

    private void armControl() {
        if (!overrideMode) {
            arm.setMode(Arm.Mode.CONTROLLED);
            
            if (operator.getY()) {
                arm.setAngle(Constants.TILT_UP_ANGLE.getDouble());
            } else if (operator.getB()) {
                arm.setAngle(Constants.TILT_MIDDLE_ANGLE.getDouble());
            } else if (operator.getA()) {
                arm.setAngle(Constants.TILT_DOWN_ANGLE.getDouble());
            }
            
        } else {
            arm.setMode(Arm.Mode.OVERRIDE);
            arm.setArmMotorSpeed(0.5 * operator.getLeftY());
        }
    }
}
