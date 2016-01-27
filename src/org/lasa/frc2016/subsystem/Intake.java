package org.lasa.frc2016.subsystem;

import org.lasa.lib.HazySubsystem;
import edu.wpi.first.wpilibj.Victor;
import org.lasa.frc2016.statics.Ports;

public class Intake extends HazySubsystem {

    private static Intake instance;

    private Victor intakeMotor;
    private double intakeSpeed;

    private Intake() {
        intakeMotor = new Victor(Ports.INTAKE_MOTOR);
    }

    public static Intake getInstance() {
        return (instance == null) ? instance = new Intake() : instance;
    }

    public void run() {
        intakeMotor.set(intakeSpeed);
    }

    public void putStatus() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setDirection(double speed) {
        intakeSpeed = speed;
    }
}
