/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lasa.frc2016.subsystem;

import edu.wpi.first.wpilibj.DigitalInput;
import org.lasa.frc2016.statics.Ports;

/**
 *
 * @author 418
 */
public class Sensor {

    private static Sensor instance;

    public static DigitalInput intakeSwitch;

    private Sensor() {
        intakeSwitch = new DigitalInput(Ports.INTAKE_BUMP_SWITCH);
    }

    public static Sensor getInstance() {
        return (instance == null) ? instance = new Sensor() : instance;
    }
}
