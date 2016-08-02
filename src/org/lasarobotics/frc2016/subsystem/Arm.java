package org.lasarobotics.frc2016.subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.lasarobotics.frc2016.statics.Constants;
import org.lasarobotics.lib.controlloop.HazyPID;

public class Arm extends HazySubsystem {

    private static Arm instance;
    
    public static Arm getInstance() {
        return (instance == null) ? instance = new Arm() : instance;
    }

    private final HazyPID tiltPID;
    private double targetAngle;
    private double actualAngle, actualAngleRate;

    private double motorSpeed;

    private Arm() {
        tiltPID = new HazyPID();
        mode = Mode.CONTROLLED;
    }

    public static enum Mode {
        OVERRIDE, CONTROLLED;
    }

    static Mode mode;

    public void setMode(Mode m) {
        mode = m;
        //motorSpeed = 0.0;
    }

    @Override
    public void run() {
        actualAngle = hardware.getArmEncoderPosition() * 360 / 4096 / 3;
        actualAngleRate = hardware.getArmEncoderVelocity() * 360 / 4096 / 3;

        if (null != mode) {
            switch (mode) {
                case CONTROLLED:
                    motorSpeed = tiltPID.calculate(actualAngle);
                    SmartDashboard.putNumber("pid", motorSpeed);
                    break;
                case OVERRIDE:
                    break;
            }
        }

        if (hardware.getArmOutputCurrent() >= 12) {
            motorSpeed = 0;
        }

        if (hardware.topArmLimitPressed()) {
            motorSpeed = Math.min(motorSpeed, 0);
        } else if (hardware.bottomArmLimitPressed()) {
            motorSpeed = Math.max(motorSpeed, 0);
        }

        SmartDashboard.putNumber("pid", tiltPID.getSetpoint());
        hardware.setArmMotorSpeed(motorSpeed);
    }

    @Override
    public void initSubsystem() {
        tiltPID.updatePID(Constants.TILT_KP.getDouble(), Constants.TILT_KI.getDouble(), Constants.TILT_KD.getDouble(), 0.0, Constants.TILT_DONE_RANGE.getDouble());
        tiltPID.updateMaxMin(Constants.TILT_MAX_POWER.getDouble(), Constants.TILT_MIN_POWER.getDouble());
        tiltPID.reset();
    }

    @Override
    public void pushToDashboard() {
        SmartDashboard.putString("A_Mode", mode.toString());
        SmartDashboard.putNumber("T_TargetAngle", targetAngle);
        SmartDashboard.putNumber("T_ActualAngle", actualAngle);
        SmartDashboard.putNumber("T_ActualAngleRate", actualAngleRate);
        SmartDashboard.putNumber("T_MotorSpeed", motorSpeed);
        SmartDashboard.putBoolean("T_BottomSwitchPressed", hardware.bottomArmLimitPressed());
        SmartDashboard.putBoolean("T_TopSwitchPressed", hardware.topArmLimitPressed());
        SmartDashboard.putNumber("T_motorAmps", hardware.getArmOutputCurrent());
    }

    public void setAngle(double angle) {
        targetAngle = angle;
        if (mode == Mode.CONTROLLED) {
            tiltPID.setSetpoint(targetAngle);
        }
    }

    public void setArmMotorSpeed(double tiltSpeed) {
        if (mode == Mode.OVERRIDE) {
            motorSpeed = tiltSpeed;
        }
    }

    public boolean isTiltDone() {
        return tiltPID.onTarget();
    }

}
