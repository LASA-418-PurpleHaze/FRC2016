package org.lasa.frc2016.subsystem;

import edu.wpi.first.wpilibj.DigitalInput;
import org.lasa.frc2016.statics.Ports;

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
