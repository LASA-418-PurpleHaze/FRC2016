package org.lasa.frc2016.command;

import edu.wpi.first.wpilibj.DriverStation;

public class OverridePrepShooter extends HazyCommand {

    private double output;
    
    public OverridePrepShooter(String nm, double t) {
        super(nm, t);
    }

    @Override
    public void run() {
        output = driverInput.getPrepElevatorOverride();
        shooter.setMotorOutput(output);
    }

    @Override
    public boolean isDone() {
        return DriverStation.getInstance().isDisabled();
    }

    @Override
    public void stop() {
        shooter.setMotorOutput(0);
        super.stop();
    }
}
