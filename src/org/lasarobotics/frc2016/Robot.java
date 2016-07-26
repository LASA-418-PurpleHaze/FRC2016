package org.lasarobotics.frc2016;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.lasarobotics.lib.HazyIterative;
import org.lasarobotics.frc2016.input.DriverInput;
import org.lasarobotics.frc2016.hardware.Hardware;
import org.lasarobotics.frc2016.statics.Constants;
import org.lasarobotics.frc2016.subsystem.Drivetrain;
import org.lasarobotics.frc2016.subsystem.Intake;
import org.lasarobotics.frc2016.command.CommandManager;
import org.lasarobotics.frc2016.subsystem.Arm;
import org.lasarobotics.frc2016.subsystem.Shooter;
import org.lasarobotics.frc2016.vision.HazyVision;

public class Robot extends HazyIterative {

    Drivetrain drivetrain;
    Shooter shooter;
    Intake intake;
    Arm arm;
    DriverInput driverInput;
    Hardware sensorInput;
    Constants constants;
//<<<<<<< HEAD
//
//=======
//    Auton auton;
//    
//>>>>>>> test
    private static int time;

    private void pushToDashboard() {
        time++;
        SmartDashboard.putNumber("Time", time);
        SmartDashboard.putBoolean("doAuton", false);
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
//<<<<<<< HEAD
//        SmartDashboard.putBoolean("doAuton", true);
//=======
//>>>>>>> test
        constants = new Constants();
        drivetrain = Drivetrain.getInstance();
        shooter = Shooter.getInstance();
        intake = Intake.getInstance();
        arm = Arm.getInstance();
        driverInput = DriverInput.getInstance();
        sensorInput = Hardware.getInstance();
//        auton = Auton.getInstance();
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
        CommandManager.cancelAll();
//        auton.start();
    }

    @Override
    public void autonomousPeriodic() {
//<<<<<<< HEAD
//        if (SmartDashboard.getBoolean("doAuton", true)) {
//            arm.setMotorSpeeds(0.15, 0);
//            if ((sensorInput.getLeftDistance() + sensorInput.getRightDistance()) / 2 > -150.0) {
//
//                drivetrain.setDriveSpeeds(1, 1);
//            } else {
//                drivetrain.setDriveSpeeds(0, 0);
//            }
//        }
////        hazyVision.run();
////        CommandManager.run();
////        shooter.run();
//=======
//>>>>>>> test
        pushToDashboard();
//        auton.run();
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
