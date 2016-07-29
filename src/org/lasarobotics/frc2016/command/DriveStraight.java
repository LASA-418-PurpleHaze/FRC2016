package org.lasarobotics.frc2016.command;

public class DriveStraight extends Command {

    private final double setpoint;

    public DriveStraight(String name, double timeOut, double setpoint) {
        super(name, timeOut);
        this.setpoint = setpoint;
    }

    @Override
    public void run() {
    }

    @Override
    public boolean isDone() {
        return drivetrain.isDistanceDone() && drivetrain.getStraightSetpoint() == setpoint;
    }

    @Override
    public void stop() {
        drivetrain.setDriveSpeeds(0.0, 0.0);
    }

    @Override
    public void start() {
        hardware.resetDriveDistance();
        hardware.resetAngle();
        drivetrain.setDistanceSetpoint(setpoint);
    }

}
