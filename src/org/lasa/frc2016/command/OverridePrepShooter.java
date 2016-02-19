package org.lasa.frc2016.command;

import edu.wpi.first.wpilibj.DriverStation;

public class OverridePrepShooter extends HazyCommand {

    public OverridePrepShooter(String nm, double t) {
        super(nm, t);
    }

    @Override
    public void run() {
        shooter.setMotorOutput(1);
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
