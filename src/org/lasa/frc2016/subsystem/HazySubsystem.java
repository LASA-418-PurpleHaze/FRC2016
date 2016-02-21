package org.lasa.frc2016.subsystem;

import org.lasa.frc2016.Robot;
import org.lasa.frc2016.input.SensorInput;

public abstract class HazySubsystem {

    protected SensorInput sensorInput;

    protected String errorMsg;
    protected final String fileName = "logger.csv";
    protected double time;

    protected HazySubsystem() {
        sensorInput = SensorInput.getInstance();
        time = Robot.getTime();
    }

    public abstract void run();

    public abstract void updateConstants();

    public abstract void pushToDashboard();
}
