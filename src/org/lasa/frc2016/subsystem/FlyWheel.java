/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lasa.frc2016.subsystem;

import org.lasa.frc2016.lib.HazySubsystem;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import org.lasa.frc2016.controlloop.HazyPID;
import org.lasa.frc2016.statics.Constant;
import org.lasa.frc2016.statics.Ports;

/**
 *
 * @author 418
 */
public class FlyWheel extends HazySubsystem {

    private static FlyWheel instance;

    private Encoder FLY_WHEEL_ENCODER;
    private Talon FLY_WHEEL_MOTOR_MASTER, FLY_WHEEL_MOTOR_SLAVE;
    private double flyWheelSpeed;
    private HazyPID flywheelPID;

    private FlyWheel() {
        FLY_WHEEL_MOTOR_MASTER = new Talon(Ports.FLY_WHEEL_MOTOR);
        flywheelPID = new HazyPID();
        flywheelPID.updatePID(Constant.FLYWHEEL_PID_KP, Constant.FLYWHEEL_PID_KI, Constant.FLYWHEEL_PID_KD, Constant.FLYWHEEL_PID_KF, Constant.FLYWHEEL_PID_DONE_BOUND);
        flywheelPID.updateMaxMin(Constant.FLYWHEEL_PID_MAXU, Constant.FLYWHEEL_PID_MINU);
        FLY_WHEEL_ENCODER = new Encoder(Ports.FLY_WHEEL_ENCODER_FRONT, Ports.FLY_WHEEL_ENCODER_BACK);
    }

    public static FlyWheel getInstance() {
        return (instance == null) ? instance = new FlyWheel() : instance;
    }

    @Override
    public void run() {
        flyWheelSpeed = flywheelPID.calculate(FLY_WHEEL_ENCODER.getRate());
        FLY_WHEEL_MOTOR_MASTER.set(flyWheelSpeed);

    }

    @Override
    public void putStatus() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setFlyWheelSpeed(double flyWheel) {
        flywheelPID.setTarget(flyWheel);
    }

}
