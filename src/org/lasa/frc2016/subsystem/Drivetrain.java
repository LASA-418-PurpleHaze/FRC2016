/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lasa.frc2016.subsystem;

import org.lasa.lib.HazySubsystem;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.lasa.frc2016.controlloop.HazyPID;
import org.lasa.frc2016.statics.Constants;
import org.lasa.frc2016.statics.Ports;

/**
 *
 * @author LASA Robotics
 */
public class Drivetrain extends HazySubsystem {

    private static Drivetrain instance;

    private VictorSP leftFrontMotor, leftBackMotor, rightFrontMotor, rightBackMotor;
    private double leftSpeed, rightSpeed;
    private HazyPID drivePID;

    @Override
    public void updateConstants() {
        drivePID.updatePID(Constants.DRIVE_TRAIN_PID_KP, Constants.DRIVE_TRAIN_PID_KI, Constants.DRIVE_TRAIN_PID_KD, Constants.DRIVE_TRAIN_PID_KF, Constants.DRIVE_TRAIN_PID_DONE_BOUND);
        drivePID.updateMaxMin(Constants.DRIVE_TRAIN_PID_MAXU, Constants.DRIVE_TRAIN_PID_MINU);
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
        drivePID = new HazyPID();
        updateConstants();
    }

    public static Drivetrain getInstance() {
        return (instance == null) ? instance = new Drivetrain() : instance;
    }

    @Override
    public void run() {
        //control loop stuff

        //output
        leftFrontMotor.set(leftSpeed);
        leftBackMotor.set(leftSpeed);
        rightFrontMotor.set(rightSpeed);
        rightBackMotor.set(rightSpeed);
    }

    @Override
    public void pushToDashboard() {
        SmartDashboard.putNumber("leftSpeed", leftSpeed);
    }

    public void setDriveSpeeds(double left, double right) {
        if (mode == Mode.RAW) {
            leftSpeed = left;
            rightSpeed = right;
        }
    }

    public void setControlSetpoint(double distance, double angle) {
        if (mode == Mode.CONTROLLED) {
            //set control loop stuff
        }
    }

}
