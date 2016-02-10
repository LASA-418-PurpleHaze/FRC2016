package org.lasa.frc2016.command;

public class StopShooter extends HazyCommand {

    private final double RPM;

    public StopShooter(String nm, double t) {
        super(nm, t);
        this.RPM = 0;
    }

    @Override
    public void start() {
        super.start();
        shooter.setShooterSpeed(RPM);
    }

    @Override
    public boolean isDone() {
        return !shooter.isSpunUp();
    }

    @Override
    public void run() {
        //lol
    }
}
