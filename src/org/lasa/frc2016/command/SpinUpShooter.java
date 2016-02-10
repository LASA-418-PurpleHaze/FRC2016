package org.lasa.frc2016.command;

public class SpinUpShooter extends HazyCommand {

    private double RPM;

    public SpinUpShooter(String nm, double t, double rpm) {
        super(nm, t);
        this.RPM = rpm;
    }

    public void start() {
        super.start();
        shooter.setShooterSpeed(RPM);
    }

    @Override
    public boolean isDone() {
        return shooter.isSpunUp();
    }

    @Override
    public void run() {
        //lol
    }

}
