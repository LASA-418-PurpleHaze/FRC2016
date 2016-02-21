package org.lasa.frc2016;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.lasa.frc2016.command.CheesyDrive;
import org.lasa.lib.HazyIterative;
import org.lasa.frc2016.input.DriverInput;
import org.lasa.frc2016.input.SensorInput;
import org.lasa.frc2016.statics.Constants;
import org.lasa.frc2016.subsystem.Drivetrain;
import org.lasa.frc2016.subsystem.Intake;
import org.lasa.frc2016.command.CommandManager;
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

    int time = 0;

    private void pushToDashboard() {
        time++;
        SmartDashboard.putNumber("time", time);
        drivetrain.pushToDashboard();
        shooter.pushToDashboard();
        intake.pushToDashboard();
        arm.pushToDashboard();
    }

    private void updateConstants() {
        constants.loadFromFile();
        drivetrain.updateConstants();
        shooter.updateConstants();
        intake.updateConstants();
        arm.updateConstants();
        //hazyvision.updateConstants();
    }

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
        CommandManager.addCommand(new CheesyDrive("CheesyDrive", 10));
        updateConstants();
        sensorInput.start();
        time = 0;
    }

    @Override
    public void teleopPeriodic() {
        CommandManager.run();
        driverInput.run();
        drivetrain.run();
        shooter.run();
        pushToDashboard();
    }

    @Override
    public void teleopContinuous() {
        sensorInput.run();
        arm.run();
        intake.run();
    }

    @Override
    public void autonomousInit() {
        updateConstants();
        sensorInput.start();
        time = 0;
    }

    @Override
    public void autonomousPeriodic() {
        CommandManager.run();
        shooter.run();
        pushToDashboard();
    }

    @Override
    public void autonomousContinuous() {
        sensorInput.run();
        drivetrain.run();
        arm.run();
        intake.run();
    }

    @Override
    public void disabledInit() {
        CommandManager.cancelAll();
        updateConstants();
        sensorInput.start();
        time = 0;
    }

    @Override
    public void disabledPeriodic() {
        pushToDashboard();
    }

    @Override
    public void disabledContinuous() {
        sensorInput.run();
    }
}
