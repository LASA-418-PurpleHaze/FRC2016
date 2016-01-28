package org.lasa.lib;

import java.io.PrintWriter;
import org.lasa.frc2016.input.DriverInput;
import org.lasa.frc2016.input.SensorInput;
import org.lasa.frc2016.subsystem.Drivetrain;
import org.lasa.frc2016.subsystem.Flywheel;
import org.lasa.frc2016.subsystem.Intake;

public abstract class HazySubsystem {
    
    protected Drivetrain drivetrain = Drivetrain.getInstance();
    protected Intake intake = Intake.getInstance();
    protected Flywheel flywheel = Flywheel.getInstance();
    protected DriverInput driverInput = DriverInput.getInstance();
    protected SensorInput sensorInput = SensorInput.getInstance();

    protected String errorMsg;
    protected final String fileName = "logger.csv";

    public abstract void run();

    public abstract void updateConstants();

    public abstract void pushToDashboard();
}
