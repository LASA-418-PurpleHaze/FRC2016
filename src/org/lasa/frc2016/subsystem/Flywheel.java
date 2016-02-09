package org.lasa.frc2016.subsystem;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.lasa.frc2016.statics.Constants;
import org.lasa.frc2016.statics.Ports;

public class Flywheel extends HazySubsystem {

    private static Flywheel instance;

    private final CANTalon flywheelMotorMaster, flywheelMotorSlave;
    private double targetRPM;
    private double actualRPM;
    private double doneBound;
    private double doneCycles;

    private Flywheel() {
        flywheelMotorMaster = new CANTalon(Ports.FLYWHEEL_MASTER_MOTOR);
        flywheelMotorSlave = new CANTalon(Ports.FLYWHEEL_SLAVE_MOTOR);
        flywheelMotorSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
        flywheelMotorSlave.set(flywheelMotorMaster.getDeviceID());
    }

    public static Flywheel getInstance() {
        return (instance == null) ? instance = new Flywheel() : instance;
    }

    @Override
    public void run() {
        actualRPM = flywheelMotorMaster.getSpeed();
    }

    @Override
    public void pushToDashboard() {
        SmartDashboard.putNumber("F_TargetRPM", targetRPM);
        SmartDashboard.putNumber("F_ActualRPM", actualRPM);
    }

    public void setFlywheelSpeed(double RPM) {
        if (RPM == 0) {
           flywheelMotorMaster.disableControl();
        }
        flywheelMotorMaster.set(targetRPM);
        targetRPM = RPM;
    }

    public double getFlywheelSpeed() {
        return actualRPM;
    }

    public boolean isSpunUp() {
        if (Math.abs(targetRPM - actualRPM) < doneBound) {
            ++doneCycles;
        } else {
            doneCycles = 0;
        }

        return doneCycles > 10;
    }

    @Override
    public void updateConstants() {
        doneBound = Constants.FLYWHEEL_PID_DONE_BOUND.getDouble();
        flywheelMotorMaster.setPID(Constants.FLYWHEEL_PID_KP.getDouble(),
                Constants.FLYWHEEL_PID_KI.getDouble(), Constants.FLYWHEEL_PID_KD.getDouble(),
                Constants.FLYWHEEL_PID_KF.getDouble(), Constants.FLYWHEEL_PID_IZONE.getInt(),
                Constants.FLYWHEEL_PID_RAMPRATE.getDouble(), Constants.FLYWHEEL_PID_PROFILE.getInt());
    }
}
