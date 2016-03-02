package org.lasa.frc2016.subsystem;

import edu.wpi.first.wpilibj.CANTalon;
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

    private final CANTalon armTilterMaster, armTilterSlave;
    private final VictorSP leftArmElevator, rightArmElevator;
    private final HazyTMP tiltProfile, elevatorProfile;
    private final HazyPVI tiltProfileFollower, elevatorProfileFollower;
    private double targetAngle, targetExtension;
    private double actualAngle, actualAngleRate, actualAnglePot;
    private double tiltMotorOutput, elevatorMotorOutput;
    private double dt, time;
    private double targetX, targetY;
    private double actualX, actualY;
    private int cycles;

    private Arm() {
        armTilterMaster = new CANTalon(Ports.ARM_TILTER_MASTER);
        armTilterSlave = new CANTalon(Ports.ARM_TILTER_SLAVE);
        armTilterMaster.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
        armTilterSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
        armTilterSlave.set(armTilterMaster.getDeviceID());
        armTilterSlave.reverseOutput(true);
        armTilterMaster.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
        
        leftArmElevator = new VictorSP(Ports.LEFT_ARM_EXTENDER);
        rightArmElevator = new VictorSP(Ports.RIGHT_ARM_EXTENDER);
        rightArmElevator.setInverted(true);
        
        tiltProfile = new HazyTMP(Constants.TILT_MP_MAX_VELOCITY.getDouble(), Constants.TILT_MP_MAX_ACCELERATION.getDouble());
        elevatorProfile = new HazyTMP(Constants.ELEVATOR_MP_MAX_VELOCITY.getDouble(), Constants.ELEVATOR_MP_MAX_ACCELERATION.getDouble());
        
        tiltProfileFollower = new HazyPVI();
        elevatorProfileFollower = new HazyPVI();
        
        this.setMode(Mode.OVERRIDE);
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

    public boolean isArmHere(double Xpos, double Ypos) {
        return ((Xpos == targetX && Ypos == targetY) && (isTiltDone() && isElevatorDone()));
    }

    @Override
    public void run() {
        actualAngle = armTilterMaster.getEncPosition() * 360 /4096 / 3;
        actualAngleRate = armTilterMaster.getEncVelocity();
        dt = Timer.getFPGATimestamp() - time;
        time = Timer.getFPGATimestamp();
        if (null != mode) {
            switch (mode) {
                case CONTROLLED:
                    tiltProfile.calculateNextSituation(dt);
                    elevatorProfile.calculateNextSituation(dt);
                    tiltMotorOutput = tiltProfileFollower.calculate(tiltProfile, actualAngle, actualAngleRate);
                    elevatorMotorOutput = elevatorProfileFollower.calculate(elevatorProfile, sensorInput.getArmExtensionPosition(), sensorInput.getArmExtensionRate());
                    break;
                case OVERRIDE:
                    break;
            }
        }
        if (sensorInput.getArmTopLimitSwitch()) {
            tiltMotorOutput = Math.min(tiltMotorOutput, 0);
        }
        
        else if (sensorInput.getArmBottomLimitSwitch()) {
            tiltMotorOutput = Math.max(tiltMotorOutput, 0);
        }
        
        if (!sensorInput.getArmBottomLimitSwitch() && actualAngle == 0){
            tiltMotorOutput = 0;
            this.setMode(Mode.OVERRIDE);
        }
        
        armTilterMaster.set(tiltMotorOutput);
        leftArmElevator.set(elevatorMotorOutput);
        rightArmElevator.set(elevatorMotorOutput);
    }

    @Override
    public void initSubsystem() {
        actualAnglePot = armTilterMaster.getAnalogInPosition();
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
        
        tiltProfile.generateTrapezoid(actualAngle, actualAngle, actualAngleRate);
        elevatorProfile.generateTrapezoid(sensorInput.getArmExtensionPosition(), sensorInput.getArmExtensionPosition(), sensorInput.getArmExtensionRate());
        cycles = 0;
        time = Timer.getFPGATimestamp();
    }

    @Override
    public void pushToDashboard() {
        SmartDashboard.putString("A_Mode", mode.toString());
        SmartDashboard.putNumber("A_TargetX", targetX);
        SmartDashboard.putNumber("A_ActualX", actualX);
        SmartDashboard.putNumber("A_TargetY", targetY);
        SmartDashboard.putNumber("A_ActualY", actualY);
        SmartDashboard.putNumber("A_ActualX", sensorInput.getArmExtensionPosition() * Math.cos(Math.toRadians(actualAngle)));
        SmartDashboard.putNumber("A_ActualY", sensorInput.getArmExtensionPosition() * Math.sin(Math.toRadians(actualAngle)));
        SmartDashboard.putNumber("T_TargetAngle", targetAngle);
        SmartDashboard.putNumber("T_ActualAngle", actualAngle);
        SmartDashboard.putNumber("T_ActualAngleRate", actualAngleRate);
        SmartDashboard.putNumber("T_PotOutput", armTilterMaster.getAnalogInRaw());
        SmartDashboard.putNumber("T_MotorOutput", tiltMotorOutput);
        SmartDashboard.putNumber("T_TMPPosition", tiltProfile.getCurrentPosition());
        SmartDashboard.putNumber("E_TargetExtension", targetExtension);
        SmartDashboard.putNumber("E_ActualExtension", sensorInput.getArmExtensionPosition());
        SmartDashboard.putNumber("E_ActualExtensionRate", sensorInput.getArmExtensionRate());
        SmartDashboard.putNumber("E_MotorOutput", elevatorMotorOutput);
        SmartDashboard.putNumber("E_TMPPosition", elevatorProfile.getCurrentPosition());
    }

    public void setControlPoint(double x, double y) {
        if (mode == Mode.CONTROLLED) {
            if (x < 0) {
                x = 0;
            } else if (y < 0) {
                y = 0;
            }
            if (DriverStation.getInstance().isFMSAttached() && (DriverStation.getInstance().getMatchTime() > 20)) {;;
                y = Math.min(y, 40);
            }
            x = Math.min(x, Constants.ELEVATOR_MAX_EXTENSION.getDouble());
            targetAngle = Math.min(Math.toDegrees(Math.atan2(y, x)), Constants.TILT_MAX_ANGLE.getDouble());
            targetExtension = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
            if (targetExtension > Constants.ELEVATOR_MAX_EXTENSION.getDouble()) {
                targetExtension = Constants.ELEVATOR_MAX_EXTENSION.getDouble();
            } else if (targetAngle > Constants.TILT_MAX_ANGLE.getDouble()) {
                targetAngle = Constants.TILT_MAX_ANGLE.getDouble();
            }
            targetY = y;
            targetX = x;
            if (CANTalon.FeedbackDeviceStatus.FeedbackStatusPresent == armTilterMaster.isSensorPresent(CANTalon.FeedbackDevice.CtreMagEncoder_Absolute)) {
                tiltProfile.generateTrapezoid(targetAngle, actualAngle, actualAngleRate);
                elevatorProfile.generateTrapezoid(targetExtension, sensorInput.getArmExtensionPosition(), sensorInput.getArmExtensionRate());
            }
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
