package org.lasa.frc2016.command;

import edu.wpi.first.wpilibj.DriverStation;
import org.lasa.lib.CheesyDriveHelper;

public class CheesyDrive extends HazyCommand {

    CheesyDriveHelper cheesyDrive;

    private double throttle, wheel;
    private boolean quickTurnEnabled;

    public CheesyDrive(String nm, double t) {
        super(nm, t);
    }

    @Override
    public void run() {
        throttle = driverInput.getThrottle();
        wheel = driverInput.getWheel();
        quickTurnEnabled = driverInput.getQuickTurn();
        DriverStation.reportError("Meow", false);

        //cheesyDrive.cheesyDrive(throttle, wheel, quickTurnEnabled);
        drivetrain.setDriveSpeeds(/**cheesyDrive.getLeftPWM()**/throttle, /**12/11*cheesyDrive.getRightPWM()**/ wheel);
    }
}
