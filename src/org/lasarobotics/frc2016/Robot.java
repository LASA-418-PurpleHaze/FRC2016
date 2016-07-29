package org.lasarobotics.frc2016;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.lasarobotics.lib.HazyIterative;
import org.lasarobotics.frc2016.input.DriverInput;
import org.lasarobotics.frc2016.hardware.Hardware;
import org.lasarobotics.frc2016.statics.Constants;
import org.lasarobotics.frc2016.subsystem.Drivetrain;
import org.lasarobotics.frc2016.subsystem.Intake;
import org.lasarobotics.frc2016.command.CommandManager;
import org.lasarobotics.frc2016.subsystem.Arm;
import org.omg.CORBA.COMM_FAILURE;

public class Robot extends HazyIterative {

    Autonomous autonomous;

    Drivetrain drivetrain;
    Intake intake;
    Arm arm;

    DriverInput driverInput;
    Hardware hardware;

    Constants constants;

    private static int time;

    private void pushToDashboard() {
        SmartDashboard.putNumber("Time", time++);

        drivetrain.pushToDashboard();
        intake.pushToDashboard();
        arm.pushToDashboard();
    }

    private void initSubsystems() {
        constants.loadFromFile();

        drivetrain.initSubsystem();
        intake.initSubsystem();
        arm.initSubsystem();

        hardware.start();
    }

    @Override
    public void robotInit() {
        drivetrain = Drivetrain.getInstance();
        intake = Intake.getInstance();
        arm = Arm.getInstance();

        driverInput = DriverInput.getInstance();
        hardware = Hardware.getInstance();

        autonomous = Autonomous.getInstance();
        autonomous.start();

        constants = new Constants();
    }

    @Override
    public void teleopInit() {
        CommandManager.cancelAll();
        initSubsystems();
        time = 0;
    }

    @Override
    public void teleopPeriodic() {
        CommandManager.run();
        
        driverInput.run();
        drivetrain.run();
        
        pushToDashboard();
    }

    @Override
    public void teleopContinuous() {
        hardware.run();
        arm.run();
        intake.run();
    }

    @Override
    public void autonomousInit() {
        initSubsystems();
        CommandManager.cancelAll();
        autonomous.run();
    }

    @Override
    public void autonomousPeriodic() {
        pushToDashboard();
    }

    @Override
    public void autonomousContinuous() {
        CommandManager.run();
        
        hardware.run();
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
        hardware.run();
    }
}
