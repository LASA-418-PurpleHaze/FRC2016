package org.lasa.frc2016.command;

public class SetArmPosition extends HazyCommand {

    private final double setpointX, setpointY;

    public SetArmPosition(String nm, double t, double setpointX, double setpointY) {
        super(nm, t);
        this.setpointX = setpointX;
        this.setpointY = setpointY;
    }

    @Override
    public boolean isDone() {
        return arm.isTiltDone() && arm.isElevatorDone();
    }

    @Override
    public void run() {
    }

    @Override
    public void start() {
        super.start();
        arm.setControlPoint(setpointX, setpointY);
    }
}
