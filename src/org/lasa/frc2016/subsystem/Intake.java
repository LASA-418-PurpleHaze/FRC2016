package org.lasa.frc2016.subsystem;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.lasa.frc2016.statics.Ports;

public final class Intake extends HazySubsystem {

    private static Intake instance;

    private final VictorSP intakeMotor, loaderMotor;
    private double intakeSpeed, loaderSpeed;
    private boolean hasBall;

    private Intake() {
        intakeMotor = new VictorSP(Ports.INTAKE_MOTOR);
        loaderMotor = new VictorSP(Ports.LOADER_MOTOR);
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

    @Override
    public void run() {
        Mode newMode = mode;
        if (null != mode) {
            switch (mode) {
                case OFF:
                    intakeSpeed = 0.0;
                    loaderSpeed = 0.0;
                    break;
                case INTAKING:
                    if (!sensorInput.getIntakeSwitchValue()) {
                        hasBall = true;
                        newMode = Mode.OFF;
                    } else {
                        intakeSpeed = 1.0;
                    }
                    break;
                case OUTTAKING:
                    hasBall = false;
                    intakeSpeed = -1.0;
                    break;
                case LOADINGSHOOTER:
                    hasBall = false;
                    intakeSpeed = 1.0;
                    loaderSpeed = 1.0;
                    break;
            }
            if (newMode != mode) {
                mode = newMode;
                run();
            }
        }
        intakeMotor.set(intakeSpeed);
        loaderMotor.set(loaderSpeed);
    }

    @Override
    public void pushToDashboard() {
        SmartDashboard.putString("I_State", mode.toString());
    }

    public boolean hasBall() {
        return hasBall;
    }

    @Override
    public void initSubsystem() {
    }
}
