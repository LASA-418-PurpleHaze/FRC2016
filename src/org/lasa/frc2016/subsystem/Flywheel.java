package org.lasa.frc2016.subsystem;

import edu.wpi.first.wpilibj.CANTalon;
import org.lasa.frc2016.statics.Constants;
import org.lasa.frc2016.statics.Ports;

public class Flywheel extends HazySubsystem {

    private static Flywheel instance;

    private final CANTalon flywheelMotorMaster, flywheelMotorSlave;
    private double flyWheelSpeed;

    private Flywheel() {
        flywheelMotorMaster = new CANTalon(Ports.FLYWHEEL_MASTER_MOTOR);
        flywheelMotorMaster.setPID(Constants.FLYWHEEL_PID_KP.getDouble(),
                Constants.FLYWHEEL_PID_KI.getDouble(), Constants.FLYWHEEL_PID_KD.getDouble(),
                Constants.FLYWHEEL_PID_KF.getDouble(), Constants.FLYWHEEL_PID_IZONE.getInt(),
                Constants.FLYWHEEL_PID_RAMPRATE.getDouble(), Constants.FLYWHEEL_PID_PROFILE.getInt());
        flywheelMotorSlave = new CANTalon(Ports.FLYWHEEL_SLAVE_MOTOR);
        flywheelMotorSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
        flywheelMotorSlave.set(flywheelMotorMaster.getDeviceID());
    }

    public static Flywheel getInstance() {
        return (instance == null) ? instance
                = new Flywheel() : instance;
    }

    @Override
    public void run() {
        flywheelMotorMaster.set(flyWheelSpeed);
    }

    @Override
    public void pushToDashboard() {
    
    }
    public void setFlywheelSpeed(double flyWheel) {
        flyWheelSpeed = flyWheel;
    }

    public double getFlywheelSpeed() {
        return flywheelMotorMaster.getSpeed();
    }

    @Override
    public void updateConstants() {

    }
}