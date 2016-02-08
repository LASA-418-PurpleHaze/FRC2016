package org.lasa.frc2016.subsystem;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.lasa.frc2016.statics.Ports;

public class Intake extends HazySubsystem {

    private static Intake instance;

    private final VictorSP intakeMotor;
    private double intakeSpeed;

    private byte state;

    public static final byte OFF = 0;
    public static final byte INTAKING = 1;
    public static final byte OUTTAKING = 2;
    public static final byte LOADINGSHOOTER = 3;
    private final static String[] stateNames = {"OFF", "INTAKING", "OUTTAKING", "LOADINGSHOOTER"};

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
                if (!sensorInput.getIntakeSwitchValue()) {
                    hasBall = true;
                    newState = OFF;
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
                break;
        }

        if (newState != state) {
            state = newState;
            run();
        }

        //output
        intakeMotor.set(intakeSpeed);
    }

    @Override
    public void pushToDashboard() {
        SmartDashboard.putString("state", stateNames[state]);
    }

    public boolean hasBall() {
        return hasBall;
    }

    @Override
    public void updateConstants() {
        System.out.print("meow");
    }

    public double getIntakeSpeed() {
        return intakeMotor.get();
    }
}
