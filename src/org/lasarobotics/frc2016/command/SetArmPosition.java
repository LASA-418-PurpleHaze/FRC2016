package org.lasarobotics.frc2016.command;

public class SetArmPosition extends Command {

    private final double setpointAngle;

    public SetArmPosition(String nm, double t, double setpointAngle) {
        super(nm, t);
        this.setpointAngle = setpointAngle;
    }

    @Override
    public boolean isDone() {
        return arm.isTiltDone();
    }

    @Override
    public void run() {
    }

    @Override
    public void start() {
        arm.setAngle(setpointAngle);
    }

    @Override
    public void stop() {
    }
}
