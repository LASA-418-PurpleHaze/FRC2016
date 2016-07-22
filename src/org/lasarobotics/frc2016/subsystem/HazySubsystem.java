package org.lasarobotics.frc2016.subsystem;

import org.lasarobotics.frc2016.input.SensorInput;

public abstract class HazySubsystem {

    protected SensorInput sensorInput;

    protected String errorMsg;
    protected final String fileName = "logger.csv";

    protected HazySubsystem() {
        sensorInput = SensorInput.getInstance();
    }

    //Aaron's first commit
    public abstract void run();

    public abstract void initSubsystem();

    public abstract void pushToDashboard();
}
