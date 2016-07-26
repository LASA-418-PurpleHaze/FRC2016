package org.lasarobotics.frc2016.subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.lasarobotics.frc2016.hardware.Hardware;
import org.lasarobotics.lib.controlloop.HazyPID;
import org.lasarobotics.frc2016.statics.Constants;

public final class Drivetrain extends HazySubsystem {

    private static Drivetrain instance;

    
    private double leftSpeed, rightSpeed;

    private final HazyPID straightPID, turnPID;
    private double straightSetpoint, turnSetpoint;

    private Drivetrain() {

        straightPID = new HazyPID();
        turnPID = new HazyPID();

        this.setMode(Mode.OVERRIDE);
    }

    public static Drivetrain getInstance() {
        return (instance == null) ? instance = new Drivetrain() : instance;
    }

    public static enum Mode {
        OVERRIDE, STRAIGHT_CONTROLLED, TURN_CONTROLLED;
    }

    static Mode mode;

    public void setMode(Mode m) {
        mode = m;
    }

    @Override
    public void run() {
        if (null != mode) {
            switch (mode) {
                case STRAIGHT_CONTROLLED:
                    leftSpeed = rightSpeed = straightPID.calculate((hardware.getLeftDriveDistance() + hardware.getRightDriveDistance()) / 2);
                    break;
                case TURN_CONTROLLED:
                    double power = turnPID.calculate(hardware.getNavXAngle());
                    leftSpeed = -power;
                    rightSpeed = power;
                    break;
                case OVERRIDE:
                    break;
            }
        }
        
        hardware.setDriveSpeeds(leftSpeed, rightSpeed);
    }

    public void setDriveSpeeds(double left, double right) {
        mode = Mode.OVERRIDE;

        leftSpeed = left;
        rightSpeed = right;
    }

    public boolean isDistanceDone() {
        return mode == Mode.STRAIGHT_CONTROLLED && straightPID.onTarget();
    }

    public boolean isTurnDone() {
        return mode == Mode.TURN_CONTROLLED && turnPID.onTarget();
    }

    public void setDistanceSetpoint(double distance) {
        mode = Mode.STRAIGHT_CONTROLLED;

        turnSetpoint = 0.0;
        turnPID.setTarget(0.0);
        turnPID.reset();

        straightSetpoint = distance;
        straightPID.setTarget(straightSetpoint);
        straightPID.reset();
    }

    public void setTurnSetpoint(double angle) {
        mode = Mode.TURN_CONTROLLED;

        straightSetpoint = 0.0;
        straightPID.setTarget(0.0);
        straightPID.reset();

        turnSetpoint = angle;
        turnPID.setTarget(turnSetpoint);
        turnPID.reset();
    }

    public double getTurnSetpoint() {
        return turnSetpoint;
    }

    public double getStraightSetpoint() {
        return straightSetpoint;
    }

    @Override
    public void initSubsystem() {
        straightPID.updatePID(Constants.DRIVETRAIN_PID_KP.getDouble(), Constants.DRIVETRAIN_PID_KI.getDouble(), Constants.DRIVETRAIN_PID_KD.getDouble(), Constants.DRIVETRAIN_PID_KFF.getDouble(), Constants.DRIVETRAIN_PID_DONE_BOUND.getDouble());
        straightPID.updateMaxMin(Constants.DRIVETRAIN_PID_MAXU.getDouble(), Constants.DRIVETRAIN_PID_MINU.getDouble());

        turnPID.updatePID(Constants.GYRO_PID_KP.getDouble(), Constants.GYRO_PID_KI.getDouble(), Constants.GYRO_PID_KD.getDouble(), Constants.GYRO_PID_KFF.getDouble(), Constants.GYRO_PID_DONE_BOUND.getDouble());
        turnPID.updateMaxMin(Constants.GYRO_PID_MAXU.getDouble(), Constants.GYRO_PID_MINU.getDouble());
    }

    @Override
    public void pushToDashboard() {
        SmartDashboard.putNumber("D_LeftSpeed", leftSpeed);
        SmartDashboard.putNumber("D_RightSpeed", rightSpeed);
        SmartDashboard.putString("D_Mode", mode.toString());
        SmartDashboard.putNumber("D_NavXAngle", hardware.getNavXAngle());
        SmartDashboard.putNumber("D_LeftEnc", hardware.getLeftDriveDistance());
        SmartDashboard.putNumber("D_RightEnc", hardware.getRightDriveDistance());
    }
}
