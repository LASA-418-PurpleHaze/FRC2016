package org.lasarobotics.frc2016.command;

public class SetShooterRPM extends Command {

    double RPM;

    public SetShooterRPM(String nm, double t, double RPM) {
        super(nm, t);
        this.RPM = RPM;
    }

    @Override
    public boolean isDone() {
        return shooter.isSpunUp();
    }

    @Override
    public void run() {
    }

    @Override
    public void start() {
        shooter.setControlPoint(RPM);
    }

    @Override
    public void stop() {
    }

}
