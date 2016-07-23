package org.lasarobotics.frc2016.command;

public class DriveTurn extends Command {

    private final double setpoint;

    public DriveTurn(String name, double timeOut, double setpoint) {
        super(name, timeOut);
        this.setpoint = setpoint;
    }

    @Override
    public void run() {
    }

    @Override
    public boolean isDone() {
        return (drivetrain.isTurnDone() && drivetrain.getTurnSetpoint() == setpoint);
    }

    @Override
    public void stop() {
        drivetrain.setDriveSpeeds(0.0, 0.0);
    }

    @Override
    public void start() {
        drivetrain.setTurnSetpoint(setpoint);
    }
}
