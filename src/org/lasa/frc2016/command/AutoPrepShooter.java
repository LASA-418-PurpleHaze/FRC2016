package org.lasa.frc2016.command;

public class AutoPrepShooter extends HazyCommand {

    
    public AutoPrepShooter(String nm, double t) {
        super(nm, t);
        
    }

    @Override
    public void start() {
//        shooter.setShooterSpeed(hazyVision.getRPM());
    }

    @Override
    public void run() {
    }

    @Override
    public void stop() {
    }

    @Override
    public boolean isDone() {
        return shooter.isSpunUp();
    }

}
