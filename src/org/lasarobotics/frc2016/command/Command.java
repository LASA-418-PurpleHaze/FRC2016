package org.lasarobotics.frc2016.command;

import edu.wpi.first.wpilibj.Timer;
import org.lasarobotics.frc2016.input.DriverInput;
import org.lasarobotics.frc2016.hardware.Hardware;
import org.lasarobotics.frc2016.subsystem.Arm;
import org.lasarobotics.frc2016.subsystem.Drivetrain;
import org.lasarobotics.frc2016.subsystem.Intake;

public abstract class Command {

    protected Drivetrain drivetrain;
    protected Intake intake;
    protected Arm arm;
    protected DriverInput driverInput;
    protected Hardware hardware;
//    protected HazyVision hazyVision;

    String name;

    protected double startTime;
    protected double currentTime;
    double timeOut;

    protected boolean isDone;
    protected boolean stopped;
    protected boolean shouldRun;
    protected boolean isLeftDone;

    public Command(String name, double timeOut) {
        drivetrain = Drivetrain.getInstance();
        intake = Intake.getInstance();
        arm = Arm.getInstance();
        driverInput = DriverInput.getInstance();
        hardware = Hardware.getInstance();
//        hazyVision = HazyVision.getInstance();;

        this.name = name;
        this.timeOut = timeOut;
        this.startTime = Double.MAX_VALUE;
    }

    public abstract void start();

    public boolean isStarted() {
        return startTime != Double.MAX_VALUE;
    }

    public abstract boolean isDone();

    public abstract void run();

    public abstract void stop();

    public boolean isTimedOut() {
        return (Timer.getFPGATimestamp() - startTime) > timeOut;
    }

    public void cancel() {
        stopped = true;
    }
}
