package org.lasa.frc2016.command;

import edu.wpi.first.wpilibj.DriverStation;

public class OverridePrepShooter extends HazyCommand {

    private double output;
    
    public OverridePrepShooter(String nm, double t) {
        super(nm, t);
    }

    @Override
    public void start() {
        super.start();
        output = driverInput.getPrepElevatorOverride();
        shooter.setMotorOutput(output);
    }

    @Override
    public boolean isDone() {
        return DriverStation.getInstance().isDisabled();
    }

    @Override
    public void run() {
        //lol
    }

}
