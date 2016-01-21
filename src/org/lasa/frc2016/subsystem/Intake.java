/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lasa.frc2016.subsystem;

import edu.wpi.first.wpilibj.Victor;
import org.lasa.frc2016.statics.Ports;

/**
 *
 * @author 418
 */
public class Intake extends HazySubsystem {

    private static Intake instance;

    private Victor INTAKE_MOTOR;
    private double intakeSpeed;

    private Intake() {
        INTAKE_MOTOR = new Victor(Ports.INTAKE_MOTOR);
    }

    public static Intake getInstance() {
        return (instance == null) ? instance = new Intake() : instance;
    }

    public void run() {
        INTAKE_MOTOR.set(intakeSpeed);
    }

    public void putStatus() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setDirection(double speed) {
        intakeSpeed = speed;
    }
}
