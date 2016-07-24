package org.lasarobotics.frc2016;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.lasarobotics.frc2016.command.CommandManager;
import org.lasarobotics.frc2016.command.SetArmOverridePower;
import org.lasarobotics.frc2016.input.Input;
import org.lasarobotics.frc2016.subsystem.Arm;
import org.lasarobotics.frc2016.subsystem.Drivetrain;

public class Auton implements Runnable {

    private static Auton instance;

    private Arm arm;
    private Drivetrain drivetrain;
    private Input sensorInput;

    private boolean doAuton;
    private boolean firstRun;

    private Auton() {
        arm = Arm.getInstance();
        drivetrain = Drivetrain.getInstance();
        sensorInput = Input.getInstance();
    }

    public static Auton getInstance() {
        return (instance == null) ? instance = new Auton() : instance;
    }

    public void start() {
        doAuton = SmartDashboard.getBoolean("doAuton", false);
        firstRun = true;
        arm.setMode(Arm.Mode.OVERRIDE);
        drivetrain.setMode(Drivetrain.Mode.OVERRIDE);
    }

    @Override
    public void run() {
        if (doAuton) {
            if (firstRun) {
                CommandManager.addCommand(new SetArmOverridePower("LowerArm", 100, 0.15, 0));
                firstRun = false;
            }
            if ((sensorInput.getLeftDistance() + sensorInput.getRightDistance()) / 2 > -150.0) {
                drivetrain.setDriveSpeeds(1, 1);
            } else {
                drivetrain.setDriveSpeeds(0, 0);
            }
        }
    }
}
