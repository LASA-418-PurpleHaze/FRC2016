package org.lasa.frc2016;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.lasa.lib.HazyIterative;
import org.lasa.frc2016.input.DriverInput;
import org.lasa.frc2016.input.SensorInput;
import org.lasa.frc2016.vision.HazyVision;
import org.lasa.frc2016.subsystem.Drivetrain;
import org.lasa.frc2016.subsystem.Flywheel;
import org.lasa.frc2016.subsystem.Intake;
import org.lasa.lib.CheesyDriveHelper;
import org.lasa.lib.HazyJoystick;

public class Robot extends HazyIterative {

    Thread vision;
    Drivetrain driveTrain;
    Flywheel flyWheel;
    Intake intake;

    DriverInput driverInput;
    SensorInput sensorInput;
    CheesyDriveHelper cheesyDrive;

    @Override
    public void robotInit() {
        //ScheduledExectorService 5hz/200ms (make a Constant)
        new Thread(HazyVision.getInstance()).start();
        driveTrain = Drivetrain.getInstance();
        flyWheel = Flywheel.getInstance();
        intake = Intake.getInstance();

        driverInput = DriverInput.getInstance();
        sensorInput = SensorInput.getInstance();
    }

    @Override
    public void teleopInit() {
        driveTrain.updateConstants();
    }

    @Override
    public void teleopPeriodic() {
        driverInput.run();
        driveTrain.pushToDashboard();
    }

    @Override
    public void teleopContinuous() {
        sensorInput.run();
        driveTrain.run();
    }

    @Override
    public void autonomousInit() {
        super.autonomousInit(); //To change body of generated methods, choose Tools | Templates.
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
