package org.lasa.frc2016.subsystem;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.lasa.lib.controlloop.HazyPID;
import org.lasa.frc2016.statics.Constants;
import org.lasa.frc2016.statics.Ports;

public class Drivetrain extends HazySubsystem {

    private static Drivetrain instance;

    private final VictorSP leftFrontMotor, leftBackMotor, rightFrontMotor, rightBackMotor;
    private double leftSpeed, rightSpeed;

    private final HazyPID straightPID, turnPID;
    private double straightSetpoint, turnSetpoint;

    private Drivetrain() {
        leftFrontMotor = new VictorSP(Ports.LEFT_FRONT_MOTOR);
        leftBackMotor = new VictorSP(Ports.LEFT_BACK_MOTOR);
        rightFrontMotor = new VictorSP(Ports.RIGHT_FRONT_MOTOR);
        rightBackMotor = new VictorSP(Ports.RIGHT_BACK_MOTOR);
        rightFrontMotor.setInverted(true);
        rightBackMotor.setInverted(true);
        straightPID = new HazyPID();
        turnPID = new HazyPID();
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
                    leftSpeed = rightSpeed = straightPID.calculate((sensorInput.getLeftSideValue() + sensorInput.getRightSideValue()) / 2);
                    break;
                case TURN_CONTROLLED:
                    double power = turnPID.calculate(sensorInput.getNavXCompassHeading());
                    leftSpeed = -power;
                    rightSpeed = power;
                    break;
                case OVERRIDE:
                    break;
            }
        }
        leftFrontMotor.set(leftSpeed);
        leftBackMotor.set(leftSpeed);
        rightFrontMotor.set(rightSpeed);
        rightBackMotor.set(rightSpeed);
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
    
    @Override
    public void updateConstants() {
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
        SmartDashboard.putNumber("D_NavXHeading", sensorInput.getNavXCompassHeading());
        SmartDashboard.putNumber("TESTConstants", Constants.DRIVE_SENSITIVITY.getDouble());
    }
}
