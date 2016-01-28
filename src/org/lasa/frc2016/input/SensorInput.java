package org.lasa.frc2016.input;

import edu.wpi.first.wpilibj.DigitalInput;
import org.lasa.frc2016.statics.Ports;

public class SensorInput {

    private static SensorInput instance;

    public static DigitalInput intakeSwitch;

    private SensorInput() {
        intakeSwitch = new DigitalInput(Ports.INTAKE_BUMP_SWITCH);
    }

    public static SensorInput getInstance() {
        return (instance == null) ? instance = new SensorInput() : instance;
    }

    public void run() {

    }

    public boolean getIntakeBumpSwitch() {
        return intakeSwitch.get();
    }
}
