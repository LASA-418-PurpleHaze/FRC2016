package org.lasarobotics.frc2016.command;

public class SetShooterOverridePower extends HazyCommand {

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
        super.run();
    }

    @Override
    public void start() {
        super.start();
        shooter.setMotorOutput(motorOutput);
    }

}
