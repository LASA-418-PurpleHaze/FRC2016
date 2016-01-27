package org.lasa.frc2016;

import com.ni.vision.NIVision;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.lasa.lib.HazyIterative;
import edu.wpi.first.wpilibj.vision.AxisCamera;
import org.lasa.frc2016.input.DriverInput;
import org.lasa.frc2016.statics.Ports;
import org.lasa.frc2016.vision.HazyVision;
import org.lasa.frc2016.statics.Constants;
import org.lasa.frc2016.subsystem.Drivetrain;
import org.lasa.frc2016.subsystem.Flywheel;
import org.lasa.frc2016.subsystem.Intake;
import org.lasa.lib.CheesyDriveHelper;
import org.lasa.lib.HazyJoystick;

public class Robot extends HazyIterative {

    Thread vision;
    SmartDashboard dash;
    Drivetrain driveTrain;
    Flywheel flyWheel;
    Intake intake;
    HazyJoystick driver, operator;

    DriverInput driverInput;
    CheesyDriveHelper cheesyDrive;

    @Override
    public void robotInit() {
        new Thread(HazyVision.getInstance()).start();
        dash = new SmartDashboard();
        driveTrain = Drivetrain.getInstance();
        flyWheel = Flywheel.getInstance();
        intake = Intake.getInstance();
        driver = new HazyJoystick(0);
        operator = new HazyJoystick(1);
        driverInput = DriverInput.getInstance();
        cheesyDrive = new CheesyDriveHelper(driveTrain);
        
    }

    @Override
    public void teleopPeriodic() {
        driverInput.run();
        cheesyDrive.cheesyDrive(driver.getLeftX(), driver.getRightY(), true, false);
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

    @Override
    public void teleopInit() {
        super.teleopInit(); //To change body of generated methods, choose Tools | Templates.
    }

}
