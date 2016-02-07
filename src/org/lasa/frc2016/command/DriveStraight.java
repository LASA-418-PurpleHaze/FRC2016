package org.lasa.frc2016.command;

public class DriveStraight extends HazyCommand {
    
    private double distance;

    public DriveStraight(String name, double timeOut, double distance) {
        super(name, timeOut);
        this.distance = distance;
        drivetrain.setControlSetpoint(distance, 0);
    }

    @Override
    public void run() {
    }

    @Override
    public boolean isDone() {
        return super.isDone(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void stop() {
        super.stop(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
