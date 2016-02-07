package org.lasa.frc2016.subsystem;

import edu.wpi.first.wpilibj.CANTalon;
import org.lasa.frc2016.statics.Constants;
import org.lasa.frc2016.statics.Ports;

/**
 * public class Flywheel extends HazySubsystem {
 *
 * private static Flywheel instance;
 *
 * private final CANTalon flywheelMotorMaster, flywheelMotorSlave; private
 * double flyWheelSpeed;
 *
 * private Flywheel() { flywheelMotorMaster = new
 * CANTalon(Ports.FLYWHEEL_MASTER_MOTOR);
 * flywheelMotorMaster.setPID(Constants.FLYWHEEL_PID_KP,
 * Constants.FLYWHEEL_PID_KI, Constants.FLYWHEEL_PID_KD,
 * Constants.FLYWHEEL_PID_KF, Constants.FLYWHEEL_PID_IZONE,
 * Constants.FLYWHEEL_PID_RAMPRATE, Constants.FLYWHEEL_PID_PROFILE);
 * flywheelMotorSlave = new CANTalon(Ports.FLYWHEEL_SLAVE_MOTOR);
 * //flywheelMotorSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
 * //flywheelMotorSlave.set(flywheelMotorMaster.getDeviceID()); }
 *
 * public static Flywheel getInstance() { return (instance == null) ? instance =
 * new Flywheel() : instance; }
 *
 * @Override public void run() { flywheelMotorMaster.set(flyWheelSpeed); }
 *
 * @Override public void pushToDashboard() { throw new
 * UnsupportedOperationException("Not supported yet."); //To change body of
 * generated methods, choose Tools | Templates. }
 *
 * public void setFlywheelSpeed(double flyWheel) { flyWheelSpeed = flyWheel; }
 *
 * public double getFlywheelSpeed() { return flywheelMotorMaster.getSpeed(); }
 *
 * @Override public void updateConstants() { throw new
 * UnsupportedOperationException("Not supported yet."); //To change body of
 * generated methods, choose Tools | Templates. }
 *
 * }
 *
 */
