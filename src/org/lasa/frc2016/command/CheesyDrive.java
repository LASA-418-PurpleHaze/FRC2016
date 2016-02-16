package org.lasa.frc2016.command;

import edu.wpi.first.wpilibj.DriverStation;
import org.lasa.lib.CheesyDriveHelper;

public class CheesyDrive extends HazyCommand {

    CheesyDriveHelper cheesyDrive;

    private double leftSide, rightSide, throttle, wheel;
    private boolean quickTurnEnabled;

    public CheesyDrive(String nm, double t) {
        super(nm, t);
        cheesyDrive = new CheesyDriveHelper();
    }

    @Override
    public void run() {
        throttle = driverInput.getThrottle();
        wheel = driverInput.getWheel();
        quickTurnEnabled = driverInput.getQuickTurn();

        cheesyDrive.cheesyDrive(throttle, -wheel, quickTurnEnabled);
        drivetrain.setDriveSpeeds(cheesyDrive.getLeftPWM(), cheesyDrive.getRightPWM());
    }

    @Override
    public void stop() {
        drivetrain.setDriveSpeeds(0.0, 0.0);
        super.stop();
    }

    @Override
    public boolean isDone() {
        return DriverStation.getInstance().isDisabled();
    }

}
