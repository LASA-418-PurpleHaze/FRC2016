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
    private final HazyPID straighPID, turnPID;

    @Override
    public void updateConstants() {
        straighPID.updatePID(Constants.DRIVETRAIN_PID_KP.getDouble(), Constants.DRIVETRAIN_PID_KI.getDouble(), Constants.DRIVETRAIN_PID_KD.getDouble(), Constants.DRIVETRAIN_PID_KF.getDouble(), Constants.DRIVETRAIN_PID_DONE_BOUND.getDouble());
        straighPID.updateMaxMin(Constants.DRIVETRAIN_PID_MAXU.getDouble(), Constants.DRIVETRAIN_PID_MINU.getDouble());
        turnPID.updatePID(Constants.GYRO_PID_KP.getDouble(), Constants.GYRO_PID_KI.getDouble(), Constants.GYRO_PID_KD.getDouble(), Constants.GYRO_PID_KF.getDouble(), Constants.GYRO_PID_DONE_BOUND.getDouble());
        turnPID.updateMaxMin(Constants.GYRO_PID_MAXU.getDouble(), Constants.GYRO_PID_MINU.getDouble());
    }

    public enum Mode {

        RAW, CONTROLLED;
    }

    Mode mode;

    public void setMode(Mode m) {
        mode = m;
    }

    private Drivetrain() {
        leftFrontMotor = new VictorSP(Ports.LEFT_FRONT_MOTOR);
        leftBackMotor = new VictorSP(Ports.LEFT_BACK_MOTOR);
        rightFrontMotor = new VictorSP(Ports.RIGHT_FRONT_MOTOR);
        rightBackMotor = new VictorSP(Ports.RIGHT_BACK_MOTOR);
        leftFrontMotor.setInverted(true);
        leftBackMotor.setInverted(true);
        straighPID = new HazyPID();
        turnPID = new HazyPID();

        updateConstants();
    }

    public static Drivetrain getInstance() {
        return (instance == null) ? instance = new Drivetrain() : instance;
    }

    @Override
    public void run() {
        //control loop stuff
        if (mode == Mode.CONTROLLED) {
        }
        leftFrontMotor.set(leftSpeed);
        leftBackMotor.set(leftSpeed);
        rightFrontMotor.set(rightSpeed);
        rightBackMotor.set(rightSpeed);
    }

    @Override
    public void pushToDashboard() {
        SmartDashboard.putNumber("leftSpeed", leftSpeed);
        SmartDashboard.putNumber("rightSpeed", rightSpeed);
        SmartDashboard.putString("Mode", mode.toString());
    }

    public void setDriveSpeeds(double left, double right) {
        //if (mode == Mode.RAW) {
        leftSpeed = left;
        rightSpeed = right;
        //}
    }

    public void setControlSetpoint(double distance, double angle) {
        if (mode == Mode.CONTROLLED) {
        }
    }
}
