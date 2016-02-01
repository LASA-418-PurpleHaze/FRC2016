package org.lasa.frc2016.command;

import org.lasa.lib.CheesyDriveHelper;
import org.lasa.lib.HazyCommand;

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
        
        cheesyDrive.cheesyDrive(throttle, wheel, quickTurnEnabled);
        drivetrain.setDriveSpeeds(cheesyDrive.getLeftPWM(), cheesyDrive.getRightPWM());
    }
}
