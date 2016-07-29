package org.lasarobotics.frc2016.subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.lasarobotics.frc2016.statics.Constants;
import org.lasarobotics.lib.HazyConstant;

public final class Intake extends HazySubsystem {

    private static Intake instance;

    private double intakeSpeed;
    private boolean hasBall;
    
    private double operatingSpeed;

    private Intake() {
        this.setMode(Mode.OFF);
    }

    public static Intake getInstance() {
        return (instance == null) ? instance = new Intake() : instance;
    }

    public static enum Mode {
        OFF, INTAKING, OUTTAKING, LOADINGSHOOTER
    }

    static Mode mode;

    public void setMode(Mode m) {
        mode = m;
    }

    Mode newMode;

    @Override
    public void run() {
        newMode = mode;
        if (null != mode) {
            switch (mode) {
                case OFF:
                    intakeSpeed = 0.0;
                    break;
                case INTAKING:
                    if (hardware.isBallInRobot()) {
                        hasBall = true;
                        newMode = Mode.OFF;
                    } else {
                        intakeSpeed = operatingSpeed;
                    }
                    break;
                case OUTTAKING:
                    hasBall = false;
                    intakeSpeed = -1.0;
//                    loaderSpeed = 1.0;
                    break;
                case LOADINGSHOOTER:
                    hasBall = false;
                    intakeSpeed = operatingSpeed;
                    break;
            }
            if (newMode != mode) {
                mode = newMode;
                this.run();
            }
        }
        if (newMode != mode) {
            mode = newMode;
            this.run();
        }
        
        hardware.setIntakeSpeed(intakeSpeed);
    }

    @Override
    public void pushToDashboard() {
        SmartDashboard.putString("I_State", mode.toString());
        SmartDashboard.putBoolean("I_Switch", hardware.isBallInRobot());
    }

    public boolean hasBall() {
        return hasBall;
    }

    @Override
    public void initSubsystem() {
        operatingSpeed = Constants.INTAKE_SPEED.getDouble();
    }
}
