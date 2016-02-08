package org.lasa.frc2016.command;

public class StopShooter extends HazyCommand {
    
    private double RPM;
    
    public StopShooter(String nm, double t) {
        super(nm, t);
        this.RPM = 0;
    }
    
    public void start() {
        super.start();
        flywheel.setFlywheelSpeed(RPM);
    }

    @Override
    public boolean isDone() {
        return flywheel.isSpunUp();
    }

    @Override
    public void run() {
        //lol
    }
}
