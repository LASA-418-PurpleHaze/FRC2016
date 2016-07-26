package org.lasarobotics.frc2016.subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
        motorSpeed = 0.0;
    }

    @Override
    public void run() {
        actualAngle = hardware.getArmEncoderPosition() * 360 / 4096 / 3;
        actualAngleRate = hardware.getArmEncoderVelocity() * 360 / 4096 / 3;

        if (null != mode) {
            switch (mode) {
                case CONTROLLED:
                    motorSpeed = tiltPID.calculate(actualAngle);
                    break;
                case OVERRIDE:
                    break;
            }
        }

        if (hardware.getArmOutputCurrent() >= 30) {
            motorSpeed = 0;
        }

        if (hardware.topArmLimitPressed()) {
            motorSpeed = Math.max(motorSpeed, 0);
        } else if (hardware.bottomArmLimitPressed()) {
            motorSpeed = Math.min(motorSpeed, 0);
        }

        hardware.setArmMotorSpeed(.3 * motorSpeed);
    }

    @Override
    public void initSubsystem() {
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
    }

    public void setControlPoint(double angle) {
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
        return tiltPID.isDone();
    }

}
