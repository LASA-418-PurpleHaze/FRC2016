/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lasa.frc2016.subsystem;

import org.lasa.lib.HazySubsystem;
import edu.wpi.first.wpilibj.Victor;
import org.lasa.frc2016.controlloop.HazyPID;
import org.lasa.frc2016.statics.Constants;
import org.lasa.frc2016.statics.Ports;

/**
 *
 * @author LASA Robotics
 */
public class Drivetrain extends HazySubsystem {

    private static Drivetrain instance;

    private Victor leftFrontMotor, leftBackMotor, rightFrontMotor, rightBackMotor;
    private double leftFrontSpeed, leftBackSpeed, rightFrontSpeed, rightBackSpeed;
    private HazyPID drivePID;

    private Drivetrain() {
        leftFrontMotor = new Victor(Ports.LEFT_FRONT_MOTOR);
        leftBackMotor = new Victor(Ports.LEFT_BACK_MOTOR);
        rightFrontMotor = new Victor(Ports.RIGHT_FRONT_MOTOR);
        rightBackMotor = new Victor(Ports.RIGHT_BACK_MOTOR);
        drivePID = new HazyPID();
        drivePID.updatePID(Constants.DRIVE_TRAIN_PID_KP, Constants.DRIVE_TRAIN_PID_KI, Constants.DRIVE_TRAIN_PID_KD, Constants.DRIVE_TRAIN_PID_KF, Constants.DRIVE_TRAIN_PID_DONE_BOUND);
        drivePID.updateMaxMin(Constants.DRIVE_TRAIN_PID_MAXU, Constants.DRIVE_TRAIN_PID_MINU);
    }

    public static Drivetrain getInstance() {
        return (instance == null) ? instance = new Drivetrain() : instance;
    }

    @Override
    public void run() {
        leftFrontMotor.set(leftFrontSpeed);
        leftBackMotor.set(leftBackSpeed);
        rightFrontMotor.set(rightFrontSpeed);
        rightBackMotor.set(rightBackSpeed);
    }

    @Override
    public void putStatus() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setDriveSpeeds(double leftFront, double leftBack, double rightFront, double rightBack) {
        leftFrontSpeed = leftFront;
        leftBackSpeed = leftBack;
        rightFrontSpeed = rightFront;
        rightBackSpeed = rightBack;
    }

}
