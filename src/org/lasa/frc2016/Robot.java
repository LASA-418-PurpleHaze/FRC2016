package org.lasa.frc2016;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.lasa.lib.HazyIterative;
import org.lasa.frc2016.input.DriverInput;
import org.lasa.frc2016.input.SensorInput;
import org.lasa.frc2016.statics.Constants;
import org.lasa.frc2016.subsystem.Drivetrain;
import org.lasa.frc2016.subsystem.Intake;
import org.lasa.frc2016.command.CommandManager;
import org.lasa.frc2016.subsystem.Arm;
import org.lasa.frc2016.subsystem.Shooter;
import org.lasa.frc2016.vision.HazyVision;

public class Robot extends HazyIterative {

    Drivetrain drivetrain;
    Shooter shooter;
    Intake intake;
    Arm arm;
    DriverInput driverInput;
    SensorInput sensorInput;
    Constants constants;
    Auton auto;
    
    private static int time;

    private void pushToDashboard() {
        time++;
        SmartDashboard.putNumber("Time", time);
        drivetrain.pushToDashboard();
        shooter.pushToDashboard();
        intake.pushToDashboard();
        arm.pushToDashboard();
    }

    private void initSubsystems() {
        constants.loadFromFile();
        drivetrain.initSubsystem();
        shooter.initSubsystem();
        intake.initSubsystem();
        arm.initSubsystem();
        sensorInput.start();
    }

    public static int getTime() {
        return time;
    }

    @Override
    public void robotInit() {
        SmartDashboard.putBoolean("doAuton", false);
        constants = new Constants();
//        hazyVision = HazyVision.getInstance();
        drivetrain = Drivetrain.getInstance();
        shooter = Shooter.getInstance();
        intake = Intake.getInstance();
        arm = Arm.getInstance();
        driverInput = DriverInput.getInstance();
        sensorInput = SensorInput.getInstance();
    }

    @Override
    public void teleopInit() {
        CommandManager.cancelAll();
        initSubsystems();
        time = 0;
    }

    @Override
    public void teleopPeriodic() {
//        hazyVision.run();
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

    double autonStartTime;

    @Override
    public void autonomousInit() {
        CommandManager.cancelAll();
        auto.start();
    }

    @Override
    public void autonomousPeriodic() {
        pushToDashboard();
        auto.pushToDashboard();
        auto.run();
    }

    @Override
    public void autonomousContinuous() {
        sensorInput.run();
        drivetrain.run();
        arm.run();
    }

    @Override
    public void disabledInit() {
        CommandManager.cancelAll();
        initSubsystems();
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
