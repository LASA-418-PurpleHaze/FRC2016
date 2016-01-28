package org.lasa.frc2016.subsystem;

import org.lasa.lib.HazySubsystem;
import edu.wpi.first.wpilibj.VictorSP;
import org.lasa.frc2016.input.SensorInput;
import org.lasa.frc2016.statics.Ports;

public class Intake extends HazySubsystem {

    private static Intake instance;

    private VictorSP intakeMotor;
    private double intakeSpeed;

    private byte state;

    public static final byte OFF = 0;
    public static final byte INTAKING = 1;

    private boolean hasBall;

    private Intake() {
        intakeMotor = new VictorSP(Ports.INTAKE_MOTOR);
    }

    public static Intake getInstance() {
        return (instance == null) ? instance = new Intake() : instance;
    }

    public void setState(byte state) {
        this.state = state;
    }

    public void run() {
        byte newState = state;

        switch (state) {
            case OFF:
                intakeSpeed = 0.0;
                break;
            case INTAKING:
                if (SensorInput.getInstance().getIntakeBumpSwitch()) {
                    hasBall = true;
                    newState = OFF;

                } else {
                    intakeSpeed = 1.0;
                }
                break;
        }

        if (newState != state) {
            state = newState;
            run();
        }

        //output
        intakeMotor.set(intakeSpeed);
    }

    public void pushToDashboard() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setDirection(double speed) {
        intakeSpeed = speed;
    }

    public boolean hasBall() {
        return hasBall;
    }
}
