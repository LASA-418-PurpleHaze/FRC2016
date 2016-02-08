package org.lasa.frc2016.command;

public class DriveStraight extends HazyCommand {

    private double distance;

    public DriveStraight(String name, double timeOut, double distance) {
        super(name, timeOut);
        this.distance = distance;
    }

    @Override
    public void run() {
        //lol
    }

    @Override
    public boolean isDone() {
        return drivetrain.isDistanceDone();
    }

    @Override
    public void stop() {
        super.stop();
        drivetrain.setDriveSpeeds(0.0, 0.0);
    }

    @Override
    public void start() {
        super.start();
        drivetrain.setDistanceSetpoint(distance);
    }

}
