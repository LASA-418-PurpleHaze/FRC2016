package org.lasa.frc2016.command;

public class DriveTurn extends HazyCommand {
    
    private double angle;

    public DriveTurn(String name, double timeOut, double angle) {
        super(name, timeOut);
        this.angle = angle;
    }

    @Override
    public void run() {
        //lol
    }

    @Override
    public boolean isDone() {
        return drivetrain.isTurnDone();
    }

    @Override
    public void stop() {
        super.stop();
        drivetrain.setDriveSpeeds(0.0, 0.0);
    }

    @Override
    public void start() {
        super.start();
        drivetrain.setTurnSetpoint(angle);
    }
}
