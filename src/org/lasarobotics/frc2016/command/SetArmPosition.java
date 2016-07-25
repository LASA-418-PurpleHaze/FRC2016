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
        arm.setControlPoint(setpointAngle);
    }

    @Override
    public void stop() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
