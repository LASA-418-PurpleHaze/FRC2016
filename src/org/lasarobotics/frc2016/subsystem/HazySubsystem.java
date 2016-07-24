package org.lasarobotics.frc2016.subsystem;

import org.lasarobotics.frc2016.input.Input;

public abstract class HazySubsystem {

    protected Input sensorInput;

    protected String errorMsg;
    protected final String fileName = "logger.csv";

    protected HazySubsystem() {
        sensorInput = Input.getInstance();
    }

    //Aaron's first commit
    public abstract void run();

    public abstract void initSubsystem();

    public abstract void pushToDashboard();
}
