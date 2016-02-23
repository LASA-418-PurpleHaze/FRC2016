package org.lasa.frc2016.subsystem;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.lasa.frc2016.statics.Constants;
import org.lasa.frc2016.statics.Ports;
import org.lasa.lib.controlloop.HazyPVI;
import org.lasa.lib.controlloop.HazyTMP;

public class Arm extends HazySubsystem {

    private static Arm instance;

    private final VictorSP leftArmTilter, rightArmTilter, leftArmElevator, rightArmElevator;
    private final HazyTMP tiltProfile, elevatorProfile;
    private final HazyPVI tiltProfileFollower, elevatorProfileFollower;
    private double targetAngle, targetExtension;
    private double tiltMotorOutput, elevatorMotorOutput;
    private double dt, time;
    private double targetX, targetY;
    private double actualX, actualY;

    private Arm() {
        leftArmTilter = new VictorSP(Ports.LEFT_ARM_TILTER);
        rightArmTilter = new VictorSP(Ports.RIGHT_ARM_TILTER);
        leftArmElevator = new VictorSP(Ports.LEFT_ARM_EXTENDER);
        rightArmElevator = new VictorSP(Ports.RIGHT_ARM_EXTENDER);
        leftArmTilter.setInverted(true);
        rightArmElevator.setInverted(true);
        tiltProfile = new HazyTMP(Constants.TILT_MP_MAX_VELOCITY.getDouble(), Constants.TILT_MP_MAX_ACCELERATION.getDouble());
        elevatorProfile = new HazyTMP(Constants.ELEVATOR_MP_MAX_VELOCITY.getDouble(), Constants.ELEVATOR_MP_MAX_ACCELERATION.getDouble());
        tiltProfileFollower = new HazyPVI();
        elevatorProfileFollower = new HazyPVI();
        this.setMode(Mode.CONTROLLED);
    }

    public static Arm getInstance() {
        return (instance == null) ? instance = new Arm() : instance;
    }

    public boolean isArmHere(double Xpos, double Ypos)
    {
        return (Xpos==targetX&&Ypos==targetY&&isTiltDone()&&isElevatorDone());
    }
    
    public static enum Mode {
        OVERRIDE, CONTROLLED;
    }

    static Mode mode;

    public void setMode(Mode m) {
        mode = m;
    }
    
    @Override
    public void run() {
        dt = Timer.getFPGATimestamp() - time;
        time = Timer.getFPGATimestamp();
        if (null != mode) {
            switch (mode) {
                case CONTROLLED:
                    tiltProfile.calculateNextSituation(dt);
                    elevatorProfile.calculateNextSituation(dt);
                    tiltMotorOutput = tiltProfileFollower.calculate(tiltProfile, sensorInput.getArmTiltPosition(), sensorInput.getArmTiltRate());
                    elevatorMotorOutput = elevatorProfileFollower.calculate(elevatorProfile, sensorInput.getArmExtensionPosition(), sensorInput.getArmExtensionRate());
                    break;
                case OVERRIDE:
                    break;
            }
        }
        leftArmTilter.set(tiltMotorOutput);
        rightArmTilter.set(tiltMotorOutput);
        leftArmElevator.set(elevatorMotorOutput);
        rightArmElevator.set(elevatorMotorOutput);
    }

    @Override
    public void initSubsystem() {
        tiltProfile.setMaxVAndA(Constants.TILT_MP_MAX_VELOCITY.getDouble(), Constants.TILT_MP_MAX_ACCELERATION.getDouble());
        tiltProfileFollower.updateGains(Constants.TILT_MPF_KP.getDouble(), Constants.TILT_MPF_KV.getDouble(),
                Constants.TILT_MPF_KI.getDouble(), Constants.TILT_MPF_KFFV.getDouble(), Constants.TILT_MPF_KFFA.getDouble());
        tiltProfileFollower.setTunedVoltage(Constants.TILT_MPF_TUNED_VOLTAGE.getDouble());
        tiltProfileFollower.setDoneCycles(Constants.TILT_MPF_DONE_CYCLES.getInt());
        tiltProfileFollower.setDoneRange(Constants.TILT_MPF_DONE_RANGE.getDouble());
        tiltProfileFollower.setPositionDoneRange(Constants.TILT_MPF_POSITION_DONE_RANGE.getDouble());
        tiltProfileFollower.updateMaxMin(Constants.TILT_MPF_MAXU.getDouble(), Constants.TILT_MPF_MINU.getDouble());
        elevatorProfile.setMaxVAndA(Constants.ELEVATOR_MP_MAX_VELOCITY.getDouble(), Constants.ELEVATOR_MP_MAX_ACCELERATION.getDouble());
        elevatorProfileFollower.updateGains(Constants.ELEVATOR_MPF_KP.getDouble(), Constants.ELEVATOR_MPF_KV.getDouble(),
                Constants.ELEVATOR_MPF_KI.getDouble(), Constants.ELEVATOR_MPF_KFFV.getDouble(), Constants.ELEVATOR_MPF_KFFA.getDouble());
        elevatorProfileFollower.setTunedVoltage(Constants.ELEVATOR_MPF_TUNED_VOLTAGE.getDouble());
        elevatorProfileFollower.setDoneCycles(Constants.ELEVATOR_MPF_DONE_CYCLES.getInt());
        elevatorProfileFollower.setDoneRange(Constants.ELEVATOR_MPF_DONE_RANGE.getDouble());
        elevatorProfileFollower.setPositionDoneRange(Constants.ELEVATOR_MPF_POSITION_DONE_RANGE.getDouble());
        elevatorProfileFollower.updateMaxMin(Constants.ELEVATOR_MPF_MAXU.getDouble(), Constants.ELEVATOR_MPF_MINU.getDouble());
        tiltProfile.generateTrapezoid(sensorInput.getArmTiltPosition(), sensorInput.getArmTiltPosition(), sensorInput.getArmTiltRate());
        elevatorProfile.generateTrapezoid(sensorInput.getArmExtensionPosition(), sensorInput.getArmExtensionPosition(), sensorInput.getArmExtensionRate());
        time = Timer.getFPGATimestamp();
    }

    @Override
    public void pushToDashboard() {
        SmartDashboard.putString("A_Mode", mode.toString());
        SmartDashboard.putNumber("A_TargetX", targetX);
        SmartDashboard.putNumber("A_TargetY", targetY);
        SmartDashboard.putNumber("A_ActualX", sensorInput.getArmExtensionPosition() * Math.cos(sensorInput.getArmTiltPosition()));
        SmartDashboard.putNumber("A_ActualY", sensorInput.getArmExtensionPosition() * Math.sin(sensorInput.getArmTiltPosition()));
        SmartDashboard.putNumber("T_TargetAngle", targetAngle);
        SmartDashboard.putNumber("T_ActualAngle", sensorInput.getArmTiltPosition());
        SmartDashboard.putNumber("T_ActualAngleRate", sensorInput.getArmTiltRate());
        SmartDashboard.putNumber("T_PotOutput", sensorInput.getArmTiltPot());
        SmartDashboard.putNumber("T_MotorOutput", tiltMotorOutput);
        SmartDashboard.putNumber("T_TMPPosition", tiltProfile.getCurrentPosition());
        SmartDashboard.putNumber("E_TargetExtension", targetExtension);
        SmartDashboard.putNumber("E_ActualExtension", sensorInput.getArmExtensionPosition());
        SmartDashboard.putNumber("E_PotOutput", sensorInput.getArmExtensionPot());
        SmartDashboard.putNumber("E_ActualExtensionRate", sensorInput.getArmExtensionRate());
        SmartDashboard.putNumber("E_MotorOutput", elevatorMotorOutput);
        SmartDashboard.putNumber("E_TMPPosition", elevatorProfile.getCurrentPosition());
    }

    public void setControlPoint(double x, double y) {
        if (mode == Mode.CONTROLLED) {
            if (DriverStation.getInstance().isFMSAttached() || (DriverStation.getInstance().getMatchTime() > 20)) {
                // Only time this wouldn't run is if it IS attached and the matchtime IS less than 20 seconds
                y = Math.min(y, 40);
            }
            x = Math.min(x, Constants.ELEVATOR_MAX_EXTENSION.getDouble());
            targetAngle = Math.min(Math.toDegrees(Math.atan2(y, x)), Constants.TILT_MAX_ANGLE.getDouble());
            targetExtension = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
            targetY = y;
            targetX = x;
            tiltProfile.generateTrapezoid(targetAngle, sensorInput.getArmTiltPosition(), sensorInput.getArmTiltRate());
            elevatorProfile.generateTrapezoid(targetExtension, sensorInput.getArmExtensionPosition(), sensorInput.getArmExtensionRate());
        }
    }

    public void setMotorSpeeds(double tiltSpeed, double elevatorSpeed) {
        if (mode == Mode.OVERRIDE) {
            tiltMotorOutput = tiltSpeed;
            elevatorMotorOutput = elevatorSpeed;
        }
    }

    public boolean isTiltDone() {
        return tiltProfileFollower.isDone();
    }

    public boolean isElevatorDone() {
        return elevatorProfileFollower.isDone();
    }
}
