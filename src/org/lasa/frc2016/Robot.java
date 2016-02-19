package org.lasa.frc2016;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.lasa.frc2016.command.ArcadeDrive;
import org.lasa.frc2016.command.CheesyDrive;
import org.lasa.lib.HazyIterative;
import org.lasa.frc2016.input.DriverInput;
import org.lasa.frc2016.input.SensorInput;
import org.lasa.frc2016.statics.Constants;
import org.lasa.frc2016.vision.HazyVision;
import org.lasa.frc2016.subsystem.Drivetrain;
import org.lasa.frc2016.subsystem.Intake;
import org.lasa.frc2016.command.CommandManager;
import org.lasa.frc2016.command.OverridePrepShooter;
import org.lasa.frc2016.command.OverrideArmPosition;
import org.lasa.frc2016.subsystem.Arm;
import org.lasa.frc2016.subsystem.Shooter;

public class Robot extends HazyIterative {

//    ScheduledExecutorService scheduler;
    Drivetrain drivetrain;
    Shooter shooter;
    Intake intake;
    Arm arm;
    DriverInput driverInput;
    SensorInput sensorInput;
    Constants constants;
    
    @Override 
    public void robotInit() {
        constants = new Constants();
//        scheduler = Executors.newScheduledThreadPool(1);
//        final ScheduledFuture<?> visionHandler = scheduler.scheduleAtFixedRate(HazyVision.getInstance(), (long)Constants.VISIONHANDLER_INITIAL_DELAY.getDouble(), (long)Constants.VISIONHANDLER_PERIOD.getDouble(), TimeUnit.MILLISECONDS);
        drivetrain = Drivetrain.getInstance();
        shooter = Shooter.getInstance();
        intake = Intake.getInstance();
        arm = Arm.getInstance();
        driverInput = DriverInput.getInstance();
        sensorInput = SensorInput.getInstance();
    }

    @Override
    public void teleopInit() {
        constants.loadFromFile();
        new Thread(Manual.getInstance()).start();
        /**
        CommandManager.addCommand(new CheesyDrive("CheesyDrive", 10));
        CommandManager.addCommand(new OverrideArmPosition("OverrideArm", 10));
        CommandManager.addCommand(new OverridePrepShooter("OverrideShooter", 10));
//        CommandManager.addCommand(new ArcadeDrive("ArcadeDrive", 10));
        **/
        drivetrain.updateConstants();
        shooter.updateConstants();
        intake.updateConstants();
        arm.updateConstants();
    }

    @Override
    public void teleopPeriodic() {
        CommandManager.run();
        driverInput.run();
        drivetrain.run();
        shooter.run();
        intake.run();
        drivetrain.pushToDashboard();
        shooter.pushToDashboard();
        intake.pushToDashboard();
        arm.pushToDashboard();
    }

    @Override
    public void teleopContinuous() {
        sensorInput.run();
        arm.run();
    }

    @Override
    public void autonomousInit() {
        constants.loadFromFile();
        drivetrain.updateConstants();
        shooter.updateConstants();
        intake.updateConstants();
        arm.updateConstants();
    }

    @Override
    public void autonomousPeriodic() {
        CommandManager.run();
        shooter.run();
    }

    @Override
    public void autonomousContinuous() {
        sensorInput.run();
        drivetrain.run();
        intake.run();
        arm.run();
    }

    @Override
    public void disabledInit() {
        if (!CommandManager.empty()) {
            CommandManager.cancelAll();
        }
        super.disabledInit(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void testInit() {
        super.testInit(); //To change body of generated methods, choose Tools | Templates.
    }
}
