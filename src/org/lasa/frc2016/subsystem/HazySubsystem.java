package org.lasa.frc2016.subsystem;

import java.io.PrintWriter;

public abstract class HazySubsystem {
    protected String errorMsg;
    protected final String fileName = "logger.csv";
    public abstract void run();
    public abstract void putStatus();
    public abstract void start();
    public abstract void stop();
    
}
