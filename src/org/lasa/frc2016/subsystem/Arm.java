package org.lasa.frc2016.subsystem;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.VictorSP;
import org.lasa.frc2016.statics.Constants;
import org.lasa.frc2016.statics.Ports;
import org.lasa.lib.controlloop.HazyPID;

public class Arm extends HazySubsystem {
    
    private static Arm instance;
    
    private final VictorSP leftArmLifter, rightArmLifter, leftArmExtender, rightArmExtender;
    private final HazyPID armDistancePID, armAnglePID;
    private double leftArmSpeed, rightArmSpeed, distance, angle;
    private AnalogInput stringPot, distanceVal;
    
    private Arm() {
        leftArmLifter = new VictorSP(Ports.LEFT_ARM_LIFTER);
        rightArmLifter = new VictorSP(Ports.RIGHT_ARM_LIFTER);
        leftArmExtender = new VictorSP(Ports.LEFT_ARM_EXTENDER);
        rightArmExtender = new VictorSP(Ports.RIGHT_ARM_EXTENDER);
        stringPot = new AnalogInput(Ports.ANGLE_SENSOR);
        distanceVal = new AnalogInput(Ports.DISTANCE_SENSOR);
        armDistancePID = new HazyPID();
        armAnglePID = new HazyPID();
    }
    
    public static Arm getInstance() {
        return (instance == null) ? instance = new Arm() : instance;
    }

    @Override
    public void run() {
        distance = armDistancePID.calculate(distanceVal.getValue());
        angle = armAnglePID.calculate(stringPot.getValue());
        leftArmLifter.set(angle);
        rightArmLifter.set(angle);
        leftArmExtender.set(distance);
        rightArmExtender.set(distance);
    }

    @Override
    public void updateConstants() {
        armDistancePID.updatePID(Constants.ARM_PID_KP, Constants.ARM_PID_KI, Constants.ARM_PID_KD, Constants.ARM_PID_KF, Constants.ARM_PID_DONE_BOUND);
        armDistancePID.updateMaxMin(Constants.ARM_PID_MAXU, Constants.ARM_PID_MINU);
        armAnglePID.updatePID(Constants.ARM_PID_KP, Constants.ARM_PID_KI, Constants.ARM_PID_KD, Constants.ARM_PID_KF, Constants.ARM_PID_DONE_BOUND);
        armAnglePID.updateMaxMin(Constants.ARM_PID_MAXU, Constants.ARM_PID_MINU);
    }

    @Override
    public void pushToDashboard() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void setControlPoint(double distance, double angle) {
        armDistancePID.setTarget(distance);
        armAnglePID.setTarget(angle);
    }
    
    
}
