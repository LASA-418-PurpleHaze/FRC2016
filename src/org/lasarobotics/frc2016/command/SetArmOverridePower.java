package org.lasarobotics.frc2016.command;

public class SetArmOverridePower extends Command {
    
    private double tiltPower;
    
    public SetArmOverridePower(String nm, double t, double tiltPower) {
        super(nm, t);
        this.tiltPower = tiltPower;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public void run() {
    }

    @Override
    public void start() {
        arm.setArmMotorSpeed(tiltPower);
    }

    @Override
    public void stop() {
        arm.setArmMotorSpeed(0);
    }
    
    
}
