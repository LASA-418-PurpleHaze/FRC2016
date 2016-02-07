package org.lasa.frc2016.command;

public class LiftPortcullis extends HazyCommand {
    

    public LiftPortcullis(String name, double timeOut) {

        super(name, timeOut);
    }

    @Override
    public void start() {
        arm.setControlPoint(20, 45);
    }

    @Override
    public void run() {

    }

    @Override
    public void stop() {
    }

    @Override
    public boolean isDone() {
        return arm.getArmDistancePID().onTarget() && arm.getArmAnglePID().onTarget();
    }

}
