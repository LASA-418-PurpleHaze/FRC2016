package org.lasarobotics.frc2016.command;

public class SetShooterOverridePower extends Command {

    double motorOutput;

    public SetShooterOverridePower(String nm, double t, double motorOutput) {
        super(nm, t);
        this.motorOutput = motorOutput;
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
        shooter.setMotorOutput(motorOutput);
    }

    @Override
    public void stop() {
    }

}
