/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lasa.frc2016.subsystem;

import edu.wpi.first.wpilibj.Victor;
import org.lasa.frc2016.controlloop.HazyPID;
import org.lasa.frc2016.statics.HazyConstant;
import org.lasa.frc2016.statics.Ports;

/**
 *
 * @author LASA Robotics
 */
public class DriveTrain extends HazySubsystem{
    
    private static DriveTrain instance;

    private Victor LEFT_FRONT_MOTOR, LEFT_BACK_MOTOR, RIGHT_FRONT_MOTOR, RIGHT_BACK_MOTOR;
    private double leftFrontSpeed, leftBackSpeed, rightFrontSpeed, rightBackSpeed;
    private HazyPID drivePID;
    
    private DriveTrain() {
        LEFT_FRONT_MOTOR = new Victor(Ports.LEFT_FRONT_MOTOR);
        LEFT_BACK_MOTOR = new Victor(Ports.LEFT_BACK_MOTOR);
        RIGHT_FRONT_MOTOR = new Victor(Ports.RIGHT_FRONT_MOTOR);
        RIGHT_BACK_MOTOR = new Victor(Ports.RIGHT_BACK_MOTOR);
        drivePID = new HazyPID();
        drivePID.updatePID(HazyConstant.DRIVE_TRAIN_PID_KP, HazyConstant.DRIVE_TRAIN_PID_KI, HazyConstant.DRIVE_TRAIN_PID_KD, HazyConstant.DRIVE_TRAIN_PID_KF, HazyConstant.DRIVE_TRAIN_PID_DONE_BOUND);
        drivePID.updateMaxMin(HazyConstant.DRIVE_TRAIN_PID_MAXU, HazyConstant.DRIVE_TRAIN_PID_MINU);
    }

    public static DriveTrain getInstance() {
        return (instance == null) ? instance = new DriveTrain() : instance;
    }

    @Override
    public void run() {
        LEFT_FRONT_MOTOR.set(leftFrontSpeed);
        LEFT_BACK_MOTOR.set(leftBackSpeed);
        RIGHT_FRONT_MOTOR.set(rightFrontSpeed);
        RIGHT_BACK_MOTOR.set(rightBackSpeed);
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
