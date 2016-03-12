package org.lasa.frc2016.command;

import org.lasa.frc2016.subsystem.Arm;

public class SetArmOverridePower extends HazyCommand {
    
    private double tiltPower, elevatorPower;
    
    public SetArmOverridePower(String nm, double t, double tiltPower, double elevatorPower) {
        super(nm, t);
        this.tiltPower = tiltPower;
        this.elevatorPower = elevatorPower;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public void run() {
        super.run();
    }

    @Override
    public void start() {
        super.start();
        arm.setMotorSpeeds(tiltPower, elevatorPower);
    }
}
