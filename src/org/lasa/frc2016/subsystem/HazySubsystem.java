package org.lasa.frc2016.subsystem;

import org.lasa.frc2016.Robot;
import org.lasa.frc2016.input.SensorInput;

public abstract class HazySubsystem {

    protected SensorInput sensorInput;

    protected String errorMsg;
    protected final String fileName = "logger.csv";

    protected HazySubsystem() {
        sensorInput = SensorInput.getInstance();
    }

    public abstract void run();

    public abstract void initSubsystem();

    public abstract void pushToDashboard();
}
