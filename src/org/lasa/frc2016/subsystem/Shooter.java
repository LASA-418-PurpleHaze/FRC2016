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
    private double targetRPM, actualRPM;
    private double doneBound, doneCycles;
    private double motorOutput;
    private double encoderOutput;
    
    private Shooter() {
        shooterMotorMaster = new CANTalon(Ports.SHOOTER_MASTER_MOTOR);
        shooterMotorSlave = new CANTalon(Ports.SHOOTER_SLAVE_MOTOR);
        shooterMotorMaster.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
        shooterMotorSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
        shooterMotorSlave.set(shooterMotorMaster.getDeviceID());
        shooterMotorSlave.reverseOutput(true);
        shooterMotorMaster.setFeedbackDevice(CANTalon.FeedbackDevice.EncRising);
        shooterMotorMaster.configEncoderCodesPerRev(1);
        leftShooterServo = new Servo(Ports.LEFT_SHOOTER_SERVO);
        rightShooterServo = new Servo(Ports.RIGHT_SHOOTER_SERVO);
        mode = Mode.CONTROLLED;
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
    }

    @Override
    public void run() {
        if (null != mode) {
            switch (mode) {
                case OVERRIDE:
                    shooterMotorMaster.set(motorOutput);
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
        SmartDashboard.putNumber("S_MotorOutput", targetRPM);
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

    public void setHoodAngle(int angle){
        leftShooterServo.setRaw(angle);
        rightShooterServo.setRaw(255-angle);
    }
    
    @Override
    public void updateConstants() {
        doneBound = Constants.SHOOTER_PID_DONE_BOUND.getDouble();
        shooterMotorMaster.setPID(Constants.SHOOTER_PID_KP.getDouble(),
                Constants.SHOOTER_PID_KI.getDouble(), Constants.SHOOTER_PID_KD.getDouble(),
                Constants.SHOOTER_PID_KFF.getDouble(), Constants.SHOOTER_PID_IZONE.getInt(),
                Constants.SHOOTER_PID_RAMPRATE.getDouble(), Constants.SHOOTER_PID_PROFILE.getInt());
    }
}
