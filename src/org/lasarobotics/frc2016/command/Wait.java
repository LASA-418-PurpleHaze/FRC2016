package org.lasarobotics.frc2016.command;

public class Wait extends Command {

    public Wait(String name, double timeOut) {
        super(name, timeOut);
    }

    @Override
    public void start() {
        System.out.println("Start waiting for " + timeOut + " seconds.");
    }

    @Override
    public boolean isDone() {
        return isTimedOut();
    }

    @Override
    public void run() {
        hardware.resetAngle();
        hardware.resetDriveDistance();
    }

    @Override
    public void stop() {
        System.out.println("Finished waiting for " + timeOut + " seconds.");
    }
    
}
