package org.lasarobotics.frc2016.command;

public class SetShooterHoodAngle extends Command {

    double value;

    public SetShooterHoodAngle(String nm, double t, double value) {
        super(nm, t);
        this.value = value;
    }

    @Override
    public boolean isDone() {
        return (this.currentTime - this.startTime > .25);
    }

    @Override
    public void run() {
    }

    @Override
    public void start() {
        shooter.setHoodValue(value);
    }

    @Override
    public void stop() {
    }

}
