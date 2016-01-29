package org.lasa.lib;

import org.lasa.frc2016.input.DriverInput;
import org.lasa.frc2016.input.SensorInput;

public abstract class HazySubsystem {
    
    protected DriverInput driverInput = DriverInput.getInstance();
    protected SensorInput sensorInput = SensorInput.getInstance();

    protected String errorMsg;
    protected final String fileName = "logger.csv";

    public abstract void run();

    public abstract void updateConstants();

    public abstract void pushToDashboard();
}
