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
        DriverStation.reportError("CheesyDrive", false);

        cheesyDrive.cheesyDrive(throttle, wheel, quickTurnEnabled);
        drivetrain.setDriveSpeeds(cheesyDrive.getLeftPWM(), cheesyDrive.getRightPWM());
    }

    @Override
    public void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void stop() {
        super.stop(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isDone() {
        return false;
    }
    
    
}
