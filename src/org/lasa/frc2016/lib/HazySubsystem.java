package org.lasa.frc2016.lib;

import java.io.PrintWriter;

public abstract class HazySubsystem {

    protected String errorMsg;
    protected final String fileName = "logger.csv";

    public abstract void run();

    public abstract void putStatus();
}
