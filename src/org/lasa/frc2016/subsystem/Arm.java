package org.lasa.frc2016.subsystem;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.lasa.frc2016.statics.Constants;
import org.lasa.frc2016.statics.Ports;
import org.lasa.lib.controlloop.TorquePV;
import org.lasa.lib.controlloop.TorqueTMP;

public class Arm extends HazySubsystem {

    private static Arm instance;

    private final VictorSP leftArmTilter, rightArmTilter, leftArmElevator, rightArmElevator;
    private TorqueTMP tiltProfile, elevatorProfile;
    private TorquePV tiltProfileFollower, elevatorProfileFollower;
    private double targetAngle, targetExtension;
    private double tiltMotorOutput, elevatorMotorOutput;
    private double dt, prevTime;

    private Arm() {
        prevTime = Timer.getFPGATimestamp(); // FIX
        leftArmTilter = new VictorSP(Ports.LEFT_ARM_TILTER);
        rightArmTilter = new VictorSP(Ports.RIGHT_ARM_TILTER);
        leftArmElevator = new VictorSP(Ports.LEFT_ARM_EXTENDER);
        rightArmElevator = new VictorSP(Ports.RIGHT_ARM_EXTENDER);
        leftArmTilter.setInverted(true);
        rightArmElevator.setInverted(true);
        tiltProfile = new TorqueTMP(Constants.TILT_MP_MAX_VELOCITY.getDouble(), Constants.TILT_MP_MAX_ACCELERATION.getDouble());
        elevatorProfile = new TorqueTMP(Constants.ELEVATOR_MP_MAX_VELOCITY.getDouble(), Constants.ELEVATOR_MP_MAX_ACCELERATION.getDouble());
        tiltProfileFollower = new TorquePV();
        elevatorProfileFollower = new TorquePV();
        mode = Mode.CONTROLLED;
    }

    public static Arm getInstance() {
        return (instance == null) ? instance = new Arm() : instance;
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
        dt = Timer.getFPGATimestamp() - prevTime;;
        prevTime = Timer.getFPGATimestamp();
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
    public void updateConstants() {
        tiltProfile = new TorqueTMP(Constants.TILT_MP_MAX_VELOCITY.getDouble(), Constants.TILT_MP_MAX_ACCELERATION.getDouble());
        elevatorProfile = new TorqueTMP(Constants.ELEVATOR_MP_MAX_VELOCITY.getDouble(), Constants.ELEVATOR_MP_MAX_ACCELERATION.getDouble());
        tiltProfileFollower.setGains(Constants.TILT_MPF_KP.getDouble(), Constants.TILT_MPF_KV.getDouble(),
                Constants.TILT_MPF_KI.getDouble(), Constants.TILT_MPF_KFFV.getDouble(), Constants.TILT_MPF_KFFA.getDouble());
        tiltProfileFollower.setTunedVoltage(Constants.TILT_MPF_TUNED_VOLTAGE.getDouble());
        tiltProfileFollower.setDoneCycles(Constants.TILT_MPF_DONE_CYCLES.getInt());
        tiltProfileFollower.setDoneRange(Constants.TILT_MPF_DONE_RANGE.getDouble());
        tiltProfileFollower.setPositionDoneRange(Constants.TILT_MPF_POSITION_DONE_RANGE.getDouble());
        elevatorProfileFollower.setGains(Constants.ELEVATOR_MPF_KP.getDouble(), Constants.ELEVATOR_MPF_KV.getDouble(),
                Constants.ELEVATOR_MPF_KI.getDouble(),Constants.ELEVATOR_MPF_KFFV.getDouble(), Constants.ELEVATOR_MPF_KFFA.getDouble());
        elevatorProfileFollower.setTunedVoltage(Constants.ELEVATOR_MPF_TUNED_VOLTAGE.getDouble());
        elevatorProfileFollower.setDoneCycles(Constants.ELEVATOR_MPF_DONE_CYCLES.getInt());
        elevatorProfileFollower.setDoneRange(Constants.ELEVATOR_MPF_DONE_RANGE.getDouble());
        elevatorProfileFollower.setPositionDoneRange(Constants.ELEVATOR_MPF_POSITION_DONE_RANGE.getDouble());
        tiltProfile.generateTrapezoid(sensorInput.getArmTiltPosition(), sensorInput.getArmTiltPosition(), sensorInput.getArmTiltRate());
        elevatorProfile.generateTrapezoid(sensorInput.getArmExtensionPosition(), sensorInput.getArmExtensionPosition(), sensorInput.getArmExtensionRate());
    }

    @Override
    public void pushToDashboard() {
        SmartDashboard.putString("A_Mode", mode.toString());
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
            if (!DriverStation.getInstance().isFMSAttached() || DriverStation.getInstance().getMatchTime() < 20) {
                y = Math.min(y, 40);
            }
            x = Math.min(x, Constants.ELEVATOR_MAX_EXTENSION.getDouble());
            targetAngle = Math.min(Math.toDegrees(Math.atan2(y, x)), Constants.TILT_MAX_ANGLE.getDouble());
            targetExtension = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
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

    public boolean isDone() {
        return tiltProfileFollower.isDone() && elevatorProfileFollower.isDone();
    }
}
