package org.lasa.frc2016.command;

public class SetArmPosition extends HazyCommand {
    
    private double x, y;

    public SetArmPosition(String nm, double t, double x, double y) {
        super(nm, t);
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean isDone() {
        return arm.isDone();
    }

    @Override
    public void run() {
    }

    @Override
    public void start() {
        super.start();
        arm.setControlPoint(x, y);
    }
}
