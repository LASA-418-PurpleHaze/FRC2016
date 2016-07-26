package org.lasarobotics.frc2016.subsystem;

import org.lasarobotics.frc2016.hardware.Hardware;

public abstract class HazySubsystem {

    protected Hardware hardware;

    protected String errorMsg;
    protected final String fileName = "logger.csv";

    protected HazySubsystem() {
        hardware = Hardware.getInstance();
    }

    //Aaron's first commit
    public abstract void run();

    public abstract void initSubsystem();

    public abstract void pushToDashboard();
}
