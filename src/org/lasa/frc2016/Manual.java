package org.lasa.frc2016;

import edu.wpi.first.wpilibj.DriverStation;
import org.lasa.frc2016.input.DriverInput;
import org.lasa.frc2016.subsystem.Arm;
import org.lasa.frc2016.subsystem.Drivetrain;
import org.lasa.frc2016.subsystem.Shooter;
import org.lasa.lib.CheesyDriveHelper;

public class Manual implements Runnable {

    private static Manual instance;

    private final DriverInput driverInput;
    private final Drivetrain drivetrain;
    private final Shooter shooter;
    private final Arm arm;
    private CheesyDriveHelper cheesyDrive;

    private double throttle, wheel, tilt, elevator, output;
    private boolean quickTurn;

    private Manual() {
        driverInput = DriverInput.getInstance();
        drivetrain = Drivetrain.getInstance();
        shooter = Shooter.getInstance();
        arm = Arm.getInstance();
    }

    public static Manual getInstance() {
        return (instance == null) ? instance = new Manual() : instance;
    }

    @Override
    public void run() {
        while (DriverStation.getInstance().isEnabled()) {
            throttle = driverInput.getThrottle();
            wheel = driverInput.getWheel();
            quickTurn = driverInput.getQuickTurn();
            tilt = driverInput.getTiltOverride();
            elevator = driverInput.getElevatorOverride();
            output = driverInput.getPrepShooterOverride();

            shooter.setMotorOutput(output);
            arm.setMotorSpeeds(tilt, elevator);

            cheesyDrive.cheesyDrive(throttle, wheel, quickTurn);
            drivetrain.setDriveSpeeds(cheesyDrive.getLeftPWM(), cheesyDrive.getRightPWM());
        }
    }
}
