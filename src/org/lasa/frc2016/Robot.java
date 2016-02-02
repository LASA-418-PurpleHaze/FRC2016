package org.lasa.frc2016;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.lasa.frc2016.command.CheesyDrive;
import org.lasa.lib.HazyIterative;
import org.lasa.frc2016.input.DriverInput;
import org.lasa.frc2016.input.SensorInput;
import org.lasa.frc2016.statics.Constants;
import org.lasa.frc2016.vision.HazyVision;
import org.lasa.frc2016.subsystem.Drivetrain;
import org.lasa.frc2016.subsystem.Flywheel;
import org.lasa.frc2016.subsystem.Intake;
import org.lasa.lib.CheesyDriveHelper;
import org.lasa.frc2016.command.CommandManager;

public class Robot extends HazyIterative {

    Runnable vision;
    ScheduledExecutorService scheduler;
    Drivetrain drivetrain;
    Flywheel flywheel;
    Intake intake;

    DriverInput driverInput;
    SensorInput sensorInput;
    CheesyDriveHelper cheesyDrive;

    @Override
    public void robotInit() {
        //scheduler = Executors.newScheduledThreadPool(1);
        //final ScheduledFuture<?> visionHandler = scheduler.scheduleAtFixedRate(HazyVision.getInstance(), Constants.VISIONHANDLER_INITIAL_DELAY, Constants.VISIONHANDLER_PERIOD, TimeUnit.MILLISECONDS);

        drivetrain = Drivetrain.getInstance();
        flywheel = Flywheel.getInstance();
        intake = Intake.getInstance();
        driverInput = DriverInput.getInstance();
        sensorInput = SensorInput.getInstance();
    }

    @Override
    public void teleopInit() {
        if (!CommandManager.empty()) {
            CommandManager.cancelAll();
        }
        CommandManager.addCommand(new CheesyDrive("CheesyDrive", 10));
        drivetrain.updateConstants();
        flywheel.updateConstants();
        intake.updateConstants();
    }

    @Override
    public void teleopPeriodic() {
        CommandManager.run();
        driverInput.run();
        drivetrain.pushToDashboard();
        flywheel.pushToDashboard();
        intake.pushToDashboard();
    }

    @Override
    public void teleopContinuous() {
        sensorInput.run();
        drivetrain.run();
        flywheel.run();
        intake.run();
        
    }

    @Override
    public void autonomousInit() {
        if (!CommandManager.empty()) {
            CommandManager.cancelAll();
        }
        drivetrain.updateConstants();
        flywheel.updateConstants();
        intake.updateConstants();
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void autonomousContinuous() {
        sensorInput.run();
        drivetrain.run();
        flywheel.run();
        intake.run();
        CommandManager.run();
    }

    @Override
    public void disabledInit() {
        super.disabledInit(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void testInit() {
        super.testInit(); //To change body of generated methods, choose Tools | Templates.
    }
}
