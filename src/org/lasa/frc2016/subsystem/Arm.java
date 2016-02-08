package org.lasa.frc2016.subsystem;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.lasa.frc2016.statics.Constants;
import org.lasa.frc2016.statics.Ports;
import org.lasa.lib.controlloop.HazyPID;
import org.lasa.lib.controlloop.TorquePV;
import org.lasa.lib.controlloop.TorqueTMP;

public class Arm extends HazySubsystem {

    private static Arm instance;

    private final VictorSP leftArmTilter, rightArmTifter, leftArmElevator, rightArmElevator;
    private final HazyPID ElevatorPID, TiltPID;
    private final TorqueTMP TiltProfile, ElevatorProfile;
    private final TorquePV TiltProfileFollower, ElevatorProfileFollower;
    private double leftArmSpeed, rightArmSpeed, distance, angle;

    private Arm() {
        leftArmTilter = new VictorSP(Ports.LEFT_ARM_LIFTER);
        rightArmTifter = new VictorSP(Ports.RIGHT_ARM_LIFTER);
        leftArmElevator = new VictorSP(Ports.LEFT_ARM_EXTENDER);
        rightArmElevator = new VictorSP(Ports.RIGHT_ARM_EXTENDER);
        TiltPID = new HazyPID();
        ElevatorPID = new HazyPID();
        TiltProfile = new TorqueTMP(Constants.TILT_MP_MAX_VELOCITY.getDouble(), Constants.TILT_MP_MAX_ACCELERATION.getDouble());
        ElevatorProfile = new TorqueTMP(Constants.ELEVATOR_MP_MAX_VELOCITY.getDouble(), Constants.ELEVATOR_MP_MAX_ACCELERATION.getDouble());
        TiltProfileFollower = new TorquePV();
        ElevatorProfileFollower = new TorquePV();
        
    }

    public static Arm getInstance() {
        return (instance == null) ? instance = new Arm() : instance;
    }

    @Override
    public void run() {
        distance = ElevatorPID.calculate(sensorInput.getDistanceVal());
        angle = TiltPID.calculate(sensorInput.getStringPot());
        leftArmTilter.set(angle);
        rightArmTifter.set(angle);
        leftArmElevator.set(distance);
        rightArmElevator.set(distance);
    }

    @Override
    public void updateConstants() {
        TiltPID.updatePID(Constants.TILT_PID_KP.getDouble(), Constants.TILT_PID_KI.getDouble(), Constants.TILT_PID_KD.getDouble(), Constants.TILT_PID_KF.getDouble(), Constants.TILT_PID_DONE_BOUND.getDouble());
        TiltPID.updateMaxMin(Constants.TILT_PID_MAXU.getDouble(), Constants.TILT_PID_MINU.getDouble());
        ElevatorPID.updatePID(Constants.ELEVATOR_PID_KP.getDouble(), Constants.ELEVATOR_PID_KI.getDouble(), Constants.ELEVATOR_PID_KD.getDouble(), Constants.ELEVATOR_PID_KF.getDouble(), Constants.ELEVATOR_PID_DONE_BOUND.getDouble());
        ElevatorPID.updateMaxMin(Constants.ELEVATOR_PID_MAXU.getDouble(), Constants.ELEVATOR_PID_MINU.getDouble());
        TiltProfileFollower.setGains(Constants.ELEVATOR_MPF_KP.getDouble(), Constants.ELEVATOR_MPF_KV.getDouble(),
                Constants.TILT_MPF_KFFV.getDouble(), Constants.TILT_MPF_KFFA.getDouble());
        TiltProfileFollower.setTunedVoltage(Constants.TILT_MPF_TUNED_VOLTAGE.getDouble());
        TiltProfileFollower.setDoneCycles(Constants.TILT_MPF_DONE_CYCLES.getInt());
        TiltProfileFollower.setDoneRange(Constants.TILT_MPF_DONE_RANGE.getDouble());
        TiltProfileFollower.setPositionDoneRange(Constants.TILT_MPF_POSITION_DONE_RANGE.getDouble());
        ElevatorProfileFollower.setGains(Constants.ELEVATOR_MPF_KP.getDouble(), Constants.ELEVATOR_MPF_KV.getDouble(),
                Constants.ELEVATOR_MPF_KFFV.getDouble(), Constants.ELEVATOR_MPF_KFFA.getDouble());
        ElevatorProfileFollower.setTunedVoltage(Constants.ELEVATOR_MPF_TUNED_VOLTAGE.getDouble());
        ElevatorProfileFollower.setDoneCycles(Constants.ELEVATOR_MPF_DONE_CYCLES.getInt());
        ElevatorProfileFollower.setDoneRange(Constants.ELEVATOR_MPF_DONE_RANGE.getDouble());
        ElevatorProfileFollower.setPositionDoneRange(Constants.ELEVATOR_MPF_POSITION_DONE_RANGE.getDouble());
    }

    @Override
    public void pushToDashboard() {
    }
    
    public void setControlPoint(double angle, double extension) {
        TiltProfile.generateTrapezoid(angle, extension, leftArmSpeed);
        ElevatorProfile.generateTrapezoid(extension, extension, leftArmSpeed);
    }
    
    public HazyPID getArmAnglePID(){
        return TiltPID;
    }
    
    public HazyPID getArmDistancePID(){
        return ElevatorPID;
    }
}
