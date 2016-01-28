package org.lasa.frc2016.input;

import edu.wpi.first.wpilibj.DigitalInput;
import org.lasa.frc2016.statics.Ports;

public class SensorInput {

    private static SensorInput instance;

    public static DigitalInput intakeSwitch;

    private SensorInput() {
<<<<<<< Updated upstream
=======
        navX = new AHRS(SPI.Port.kMXP);
        leftSide = new Encoder(Ports.LEFT_SIDE_A_ENCODER, Ports.LEFT_SIDE_B_ENCODER);
        rightSide = new Encoder(Ports.RIGHT_SIDE_A_ENCODER, Ports.RIGHT_SIDE_B_ENCODER);
>>>>>>> Stashed changes
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
