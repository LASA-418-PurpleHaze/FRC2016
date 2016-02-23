package org.lasa.frc2016.command;

public class DriveStraight extends HazyCommand {

    private final double setpoint;

    public DriveStraight(String name, double timeOut, double setpoint) {
        super(name, timeOut);
        this.setpoint = setpoint;
    }

    @Override
    public void run() {
        super.run();
    }

    @Override
    public boolean isDone() {
        return drivetrain.isDistanceDone() && drivetrain.getStraightSetpoint()==setpoint;
    }

    @Override
    public void stop() {
        super.stop();
        drivetrain.setDriveSpeeds(0.0, 0.0);
    }

    @Override
    public void start() {
        super.start();
        drivetrain.setDistanceSetpoint(setpoint);
    }

}
