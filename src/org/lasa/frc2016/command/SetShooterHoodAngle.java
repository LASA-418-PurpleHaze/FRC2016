package org.lasa.frc2016.command;

public class SetShooterHoodAngle extends HazyCommand {

    double value;

    public SetShooterHoodAngle(String nm, double t, double value) {
        super(nm, t);
        this.value = value;
    }

    @Override
    public boolean isDone() {
        return true;
    }

    @Override
    public void run() {
    }

    @Override
    public void start() {
        super.start();
        shooter.setHoodValue(value);
    }

}