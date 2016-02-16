package org.lasa.frc2016.subsystem;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.lasa.frc2016.statics.Constants;
import org.lasa.frc2016.statics.Ports;

public class Shooter extends HazySubsystem {

    private static Shooter instance;

    private final CANTalon shooterMotorMaster, shooterMotorSlave;
    private final Servo leftShooterServo, rightShooterServo;
    private double targetRPM;
    private double actualRPM;
    private double doneBound;
    private double doneCycles;
    private double motorOutput;

    private Shooter() {
        shooterMotorMaster = new CANTalon(Ports.SHOOTER_MASTER_MOTOR);
        shooterMotorSlave = new CANTalon(Ports.SHOOTER_SLAVE_MOTOR);
        shooterMotorMaster.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
        shooterMotorSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
        shooterMotorSlave.set(shooterMotorMaster.getDeviceID());
        leftShooterServo = new Servo(Ports.LEFT_SHOOTER_SERVO);
        rightShooterServo = new Servo(Ports.RIGHT_SHOOTER_SERVO);
    }

    public static Shooter getInstance() {
        return (instance == null) ? instance = new Shooter() : instance;
    }

    public static enum Mode {
        OVERRIDE, CONTROLLED
    }

    Mode mode;

    public void setMode(Mode m) {
        mode = m;
    }

    @Override
    public void run() {
        switch(mode) {
            case OVERRIDE:
                shooterMotorMaster.set(motorOutput);
                break;
            case CONTROLLED:
                actualRPM = shooterMotorMaster.getSpeed();
                break;
        }
        
    }

    @Override
    public void pushToDashboard() {
        SmartDashboard.putNumber("F_TargetRPM", targetRPM);
        SmartDashboard.putNumber("F_ActualRPM", actualRPM);
    }

    public void setControlPoint(double RPM) {
        if (mode == Mode.CONTROLLED) {
            if (RPM == 0) {
                shooterMotorMaster.disableControl();
            }
            targetRPM = RPM;
            shooterMotorMaster.set(targetRPM);
        }
    }

    public void setMotorOutput(double motorOutput) {
        if (mode == Mode.OVERRIDE) {
            shooterMotorMaster.set(motorOutput);
        }
    }

    public double getShooterSpeed() {
        return actualRPM;
    }

    public boolean isSpunUp() {
        if (targetRPM == 0) {
            return false;
        }
        if (Math.abs(targetRPM - actualRPM) < doneBound) {
            ++doneCycles;
        } else {
            doneCycles = 0;
        }

        return doneCycles > 10;
    }

    @Override
    public void updateConstants() {
        doneBound = Constants.SHOOTER_PID_DONE_BOUND.getDouble();
        shooterMotorMaster.setPID(Constants.SHOOTER_PID_KP.getDouble(),
                Constants.SHOOTER_PID_KI.getDouble(), Constants.SHOOTER_PID_KD.getDouble(),
                Constants.SHOOTER_PID_KF.getDouble(), Constants.SHOOTER_PID_IZONE.getInt(),
                Constants.SHOOTER_PID_RAMPRATE.getDouble(), Constants.SHOOTER_PID_PROFILE.getInt());
    }
}
