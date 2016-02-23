package org.lasa.frc2016.subsystem;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.lasa.frc2016.statics.Constants;
import org.lasa.frc2016.statics.Ports;

public final class Shooter extends HazySubsystem {

    private static Shooter instance;

    private final CANTalon shooterMotorMaster, shooterMotorSlave;
    private final Servo leftShooterServo, rightShooterServo;
    private double targetRPM, actualRPM;
    private double doneBound, doneCycles;
    private double motorOutput;
    private double encoderOutput;

    private Shooter() {
        shooterMotorMaster = new CANTalon(Ports.SHOOTER_MASTER_MOTOR);
        shooterMotorSlave = new CANTalon(Ports.SHOOTER_SLAVE_MOTOR);
        shooterMotorSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
        shooterMotorSlave.set(shooterMotorMaster.getDeviceID());
        shooterMotorSlave.reverseOutput(true);
        shooterMotorMaster.setFeedbackDevice(CANTalon.FeedbackDevice.EncRising);
        shooterMotorMaster.configEncoderCodesPerRev(1);
        leftShooterServo = new Servo(Ports.LEFT_SHOOTER_SERVO);
        rightShooterServo = new Servo(Ports.RIGHT_SHOOTER_SERVO);
        this.setMode(Mode.CONTROLLED);
    }

    public static Shooter getInstance() {
        return (instance == null) ? instance = new Shooter() : instance;
    }

    public static enum Mode {
        OVERRIDE, CONTROLLED
    }

    static Mode mode;

    public void setMode(Mode m) {
        mode = m;
        if (null != mode) {
            switch (mode) {
                case CONTROLLED:
                    shooterMotorMaster.changeControlMode(CANTalon.TalonControlMode.Speed);
                    break;
                case OVERRIDE:
                    shooterMotorMaster.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
                    break;
            }
        }
    }

    @Override
    public void run() {
        if (null != mode) {
            switch (mode) {
                case OVERRIDE:
                    shooterMotorMaster.set(motorOutput);
                    actualRPM = shooterMotorMaster.getSpeed();
                    break;
                case CONTROLLED:
                    actualRPM = shooterMotorMaster.getSpeed();
                    break;
            }
        }
        encoderOutput = shooterMotorMaster.getEncVelocity();
    }

    @Override
    public void pushToDashboard() {
        SmartDashboard.putNumber("S_TargetRPM", targetRPM);
        SmartDashboard.putNumber("S_ActualRPM", actualRPM);
        SmartDashboard.putNumber("S_MotorOutput", motorOutput);
        SmartDashboard.putNumber("S_EncOutput", encoderOutput);
        SmartDashboard.putString("S_Mode", mode.toString());
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
            this.motorOutput = motorOutput;
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

    public void setHoodValue(double value) {
        if (value > Constants.SHOOTER_HOOD_MAXVALUE.getDouble()) {
            value = Constants.SHOOTER_HOOD_MAXVALUE.getDouble();
        } else if (value < Constants.SHOOTER_HOOD_MINVALUE.getDouble()) {
            value = Constants.SHOOTER_HOOD_MINVALUE.getDouble();
        }
        leftShooterServo.set(.5 + value);
        rightShooterServo.set(.5 - value);
    }

    @Override
    public void initSubsystem() {
        doneBound = Constants.SHOOTER_PID_DONE_BOUND.getDouble();
        shooterMotorMaster.setPID(Constants.SHOOTER_PID_KP.getDouble(),
                Constants.SHOOTER_PID_KI.getDouble(), Constants.SHOOTER_PID_KD.getDouble(),
                Constants.SHOOTER_PID_KFF.getDouble(), Constants.SHOOTER_PID_IZONE.getInt(),
                Constants.SHOOTER_PID_RAMPRATE.getDouble(), Constants.SHOOTER_PID_PROFILE.getInt());
    }
}
