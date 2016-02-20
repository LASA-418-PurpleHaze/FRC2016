package org.lasa.frc2016.command;

import edu.wpi.first.wpilibj.DriverStation;

public class OverrideShot extends HazyCommand {

    public OverrideShot(String nm, double t) {
        super(nm, t);
    }

    @Override
    public void run() {
        shooter.setMotorOutput(1);
    }

    @Override
    public boolean isDone() {
        return true;
    }
    
    @Override
    public void stop() {
        super.stop();
    }
}
