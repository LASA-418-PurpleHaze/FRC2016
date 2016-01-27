/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor
 */
package org.lasa.frc2016.subsystem;

import org.lasa.lib.HazySubsystem;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CANTalon;
import org.lasa.frc2016.controlloop.HazyPID;
import org.lasa.frc2016.statics.Constants;
import org.lasa.frc2016.statics.Ports;

/**
 *
 * @author 418
 */
public class Flywheel extends HazySubsystem {

    private static Flywheel instance;

    private CANTalon flywheelMotorMaster, flywheelMotorSlave;
    private double flyWheelSpeed;

    private Flywheel() {
        flywheelMotorMaster = new CANTalon(Ports.FLY_WHEEL_MOTOR);
        flywheelMotorMaster.setPID(Constants.FLYWHEEL_PID_KP, Constants.FLYWHEEL_PID_KI, Constants.FLYWHEEL_PID_KD, Constants.FLYWHEEL_PID_KF, Constants.FLYWHEEL_PID_IZONE, Constants.FLYWHEEL_PID_RAMPRATE, Constants.FLYWHEEL_PID_PROFILE);
    }

    public static Flywheel getInstance() {
        return (instance == null) ? instance = new Flywheel() : instance;
    }

    @Override
    public void run() {
        flywheelMotorMaster.set(flyWheelSpeed);
    }

    @Override
    public void putStatus() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setFlyWheelSpeed(double flyWheel) {
        flyWheelSpeed = flyWheel;
    }

}
