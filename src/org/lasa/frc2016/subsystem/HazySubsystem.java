package org.lasa.frc2016.subsystem;

import org.lasa.frc2016.input.DriveTeamInput;
import org.lasa.frc2016.input.SensorInput;

public abstract class HazySubsystem {

    protected DriveTeamInput driverInput;
    protected SensorInput sensorInput;

    protected String errorMsg;
    protected final String fileName = "logger.csv";

    protected HazySubsystem() {
        sensorInput = SensorInput.getInstance();
        driverInput = DriveTeamInput.getInstance();
    }

    public abstract void run();

    public abstract void updateConstants();

    public abstract void pushToDashboard();
}
