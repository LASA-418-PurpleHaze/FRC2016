package org.lasarobotics.frc2016.command;

public class SetArmPosition extends Command {

    private final double setpointX, setpointY;

    public SetArmPosition(String nm, double t, double setpointX, double setpointY) {
        super(nm, t);
        this.setpointX = setpointX;
        this.setpointY = setpointY;
    }

    @Override
    public boolean isDone() {
        return arm.isArmHere(setpointX, setpointY);
    }

    @Override
    public void run() {
    }

    @Override
    public void start() {
        arm.setControlPoint(setpointX, setpointY);
    }

    @Override
    public void stop() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
