package org.lasa.frc2016.command;

import edu.wpi.first.wpilibj.DriverStation;

public class SetArmPositionManual extends HazyCommand{
    
    private double tilt, elevator;

    public SetArmPositionManual(String nm, double t) {
        super(nm, t);
    }

    @Override
    public boolean isDone() {
        return DriverStation.getInstance().isDisabled();
    }

    @Override
    public void run() {
        tilt = driverInput.getTiltOverride();
        elevator = driverInput.getElevatorOverride();
        
        arm.setMotorSpeeds(tilt, elevator);
    }
}
