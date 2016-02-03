package org.lasa.frc2016.command;

import org.lasa.frc2016.subsystem.Drivetrain.Mode;
import org.lasa.lib.CheesyDriveHelper;

public class CheesyDrive extends HazyCommand {

    CheesyDriveHelper cheesyDrive;

    private double leftSide, rightSide;
    private boolean quickTurnEnabled;

    public CheesyDrive(String nm, double t) {
        super(nm, t);
    }

    @Override
    public void run() {
        leftSide = driverInput.getLeftSide();
        rightSide = driverInput.getRightSide();
        quickTurnEnabled = driverInput.getQuickTurn();
        
        //cheesyDrive.cheesyDrive(throttle, wheel, quickTurnEnabled);
        //drivetrain.setDriveSpeeds(cheesyDrive.getLeftPWM(), cheesyDrive.getRightPWM());
        drivetrain.setDriveSpeeds(leftSide, rightSide);
    }

    @Override
    public void start() {
        drivetrain.setMode(Mode.RAW);
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
