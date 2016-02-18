package org.lasa.frc2016.command;

import org.lasa.frc2016.subsystem.Shooter;

public class LongShooter extends HazyCommand {

    public LongShooter(String nm, double t) {
        super(nm, t);
    }

    @Override
    public void start() {
        super.start();
        shooter.setMode(Shooter.Mode.CONTROLLED);
        shooter.setHoodAngle(angle goes here);
    }

    @Override
    public void stop() {
        super.stop();
        shooter.setControlPoint(0.0);
    }
    
    @Override
    public boolean isDone() {
        return shooter.getShooterSpeed() == 0;
    }

    @Override
    public void run() {
    }
    
}
