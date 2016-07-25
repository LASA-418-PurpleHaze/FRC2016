package org.lasarobotics.frc2016.subsystem;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.lasarobotics.frc2016.statics.Constants;
import org.lasarobotics.frc2016.statics.Ports;
import org.lasarobotics.lib.controlloop.HazyPVI;
import org.lasarobotics.lib.controlloop.HazyTMP;

public class Arm extends HazySubsystem {

    private static Arm instance;

    private final HazyTMP tiltProfile;
    private final HazyPVI tiltProfileFollower;
    private double targetAngle;
    private double actualAngle, actualAngleRate;
    private double tiltMotorOutput;
    private double dt, time;
    private double targetX, targetY;
    private double actualX, actualY;

    private Arm() {
        tiltProfile = new HazyTMP(Constants.TILT_MP_MAX_VELOCITY.getDouble(), Constants.TILT_MP_MAX_ACCELERATION.getDouble());

        tiltProfileFollower = new HazyPVI();

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
        return isTiltDone();
    }

    @Override
    public void run() {
        actualAngle = sensorInput.getArmEncoderPosition() * 360 / 4096 / 3;
        actualAngleRate = sensorInput.getArmEncoderVelocity();
        dt = Timer.getFPGATimestamp() - time;
        time = Timer.getFPGATimestamp();
        if (null != mode) {
            switch (mode) {
                case CONTROLLED:
//                    tiltProfile.calculateNextSituation(dt);
//                    tiltMotorOutput = tiltProfileFollower.calculate(tiltProfile, actualAngle, actualAngleRate);
                    break;
                case OVERRIDE:
                    break;
            }
        }
        if (sensorInput.getArmOutputCurrent() >= 30) {
            tiltMotorOutput = 0;
        }
        if (sensorInput.getArmTopLimitSwitch()) {
            tiltMotorOutput = Math.max(tiltMotorOutput, 0);
        }
        if (sensorInput.getArmBottomLimitSwitch()) {
            tiltMotorOutput = Math.min(tiltMotorOutput, 0);
        }
        
        sensorInput.setArmMotorSpeed(.3 * tiltMotorOutput);
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

        tiltProfile.generateTrapezoid(actualAngle, actualAngle, actualAngleRate);
        time = Timer.getFPGATimestamp();
    }

    @Override
    public void pushToDashboard() {
        SmartDashboard.putString("A_Mode", mode.toString());
        SmartDashboard.putNumber("T_TargetAngle", targetAngle);
        SmartDashboard.putNumber("T_ActualAngle", actualAngle);
        SmartDashboard.putNumber("T_ActualAngleRate", actualAngleRate);
        SmartDashboard.putNumber("T_MotorOutput", tiltMotorOutput);
        SmartDashboard.putNumber("T_TMPPosition", tiltProfile.getCurrentPosition());
        SmartDashboard.putBoolean("T_BottomSwitch", sensorInput.getArmBottomLimitSwitch());
        SmartDashboard.putBoolean("T_TopSwitch", sensorInput.getArmTopLimitSwitch());
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
            }
        }
    }

    public void setMotorSpeeds(double tiltSpeed) {
        if (mode == Mode.OVERRIDE) {
            tiltMotorOutput = tiltSpeed;
        }
    }

    public boolean isTiltDone() {
        return tiltProfileFollower.isDone();
    }

}
