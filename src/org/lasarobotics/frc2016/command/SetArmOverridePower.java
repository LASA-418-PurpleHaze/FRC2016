package org.lasarobotics.frc2016.command;

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

    @Override
    public void stop() {
        super.stop();
        arm.setMotorSpeeds(0, 0);
    }
    
    
}
