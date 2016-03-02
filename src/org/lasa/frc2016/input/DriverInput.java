package org.lasa.frc2016.input;

import org.lasa.frc2016.command.CommandManager;
import org.lasa.frc2016.command.SetArmPosition;
import org.lasa.frc2016.command.SetIntakeMode;
import org.lasa.frc2016.command.SetShooterHoodAngle;
import org.lasa.frc2016.command.SetShooterOverridePower;
import org.lasa.frc2016.command.SetShooterRPM;
import org.lasa.frc2016.statics.Constants;
import org.lasa.frc2016.subsystem.Arm;
import org.lasa.frc2016.subsystem.Drivetrain;
import org.lasa.frc2016.subsystem.Intake;
import org.lasa.frc2016.subsystem.Shooter;
import org.lasa.lib.CheesyDriveHelper;
import org.lasa.lib.HazyJoystick;

public class DriverInput implements Runnable {

    HazyJoystick driver = new HazyJoystick(0, 0.15);
    HazyJoystick operator = new HazyJoystick(1, 0.15);

    private static DriverInput instance;

    private static Drivetrain drivetrain;
    private static Shooter shooter;
    private static Arm arm;
    private final CheesyDriveHelper cheesyDrive;

    private double throttle, wheel, tiltOverride, elevatorOverride;
    private boolean lastIntake, lastOuttake,
            lastPortcullis, lastSallyPort, lastDrawBridge, lastSeeSaw, lastResetArm,
            lastPrepVisionShooter, lastShoot, lastLongShot, lastShortShot, lastOverrideShot = false;
    private boolean quickTurn;
    private boolean overrideMode = false;
    private boolean intake, outtake;
    private boolean portcullis, sallyPort, drawBridge, seeSaw, resetArm;
    private boolean prepVisionShooter, shoot, longShot, shortShot, overrideShot;

    private DriverInput() {
        drivetrain = Drivetrain.getInstance();
        shooter = Shooter.getInstance();
        arm = Arm.getInstance();
        cheesyDrive = new CheesyDriveHelper();
    }

    public static DriverInput getInstance() {
        return (instance == null) ? instance = new DriverInput() : instance;
    }

    public double getThrottle() {
        return throttle;
    }

    public double getWheel() {
        return wheel;
    }

    public double getTiltOverride() {
        return tiltOverride;
    }

    public double getElevatorOverride() {
        return elevatorOverride;
    }

    public boolean getPrepShooterOverride() {
        return overrideShot;
    }

    public boolean getQuickTurn() {
        return quickTurn;
    }

    private void input() {
        throttle = driver.getLeftY();
        wheel = -driver.getRightX();
        quickTurn = driver.getRightBumper();
        shoot = driver.getRightTrigger() > .1;

        intake = operator.getRightBumper();
        outtake = operator.getLeftBumper();
        resetArm = operator.getLeftTrigger() > .1;
        portcullis = operator.getA();
        sallyPort = operator.getB();
        drawBridge = operator.getX();
        seeSaw = operator.getY();
        prepVisionShooter = operator.getNorth();
        longShot = operator.getEast();
        shortShot = operator.getWest();

        tiltOverride = -operator.getLeftY();
        elevatorOverride = -operator.getRightY();
        overrideShot = operator.getSouth();

        if (operator.getStart()) {
            overrideMode = false;
        } else if (operator.getSelect()) {
            overrideMode = true;
        }
    }

    private void latch() {
        lastIntake = intake;
        lastOuttake = outtake;

        lastPortcullis = portcullis;
        lastSallyPort = sallyPort;
        lastDrawBridge = drawBridge;
        lastSeeSaw = seeSaw;
        lastResetArm = resetArm;

        lastPrepVisionShooter = prepVisionShooter;
        lastShoot = shoot;
        lastLongShot = longShot;
        lastShortShot = shortShot;
        lastOverrideShot = overrideShot;
    }

    private void drivetrainControl() {
        cheesyDrive.cheesyDrive(throttle, wheel, quickTurn);
        drivetrain.setDriveSpeeds(cheesyDrive.getLeftPWM(), cheesyDrive.getRightPWM());
        //drivetrain.setDriveSpeeds(throttle + wheel, throttle - wheel);
    }

    private void intakeControl() {
        if (intake && !lastIntake) {
            CommandManager.addCommand(new SetIntakeMode("Infeed", 10, Intake.Mode.INTAKING));
        } else if (outtake && !lastOuttake) {
            CommandManager.addCommand(new SetIntakeMode("Outfeed", 10, Intake.Mode.OUTTAKING));
        } else if (!intake && lastIntake) {
            CommandManager.addCommand(new SetIntakeMode("Off", 10, Intake.Mode.OFF));
        } else if (!outtake && lastOuttake) {
            CommandManager.addCommand(new SetIntakeMode("Off", 10, Intake.Mode.OFF));
        } else if (intake && !lastIntake && outtake && !lastOuttake) {
            CommandManager.addCommand(new SetIntakeMode("Off", 10, Intake.Mode.OFF));
        }
    }

    private void shooterControl() {
        if (!overrideMode) {
            shooter.setMode(Shooter.Mode.CONTROLLED);
            if (prepVisionShooter && !lastPrepVisionShooter) {

                CommandManager.addParallel(new SetArmPosition("ArmDown", 10, 15, 0));
            } else if (longShot && !lastLongShot) {
                CommandManager.addCommand(new SetShooterRPM("LongRPM", 10, Constants.SHOOTER_LONG_RPM.getDouble()));
                CommandManager.addParallel(new SetShooterHoodAngle("LongHood", 10, Constants.SHOOTER_LONG_VALUE.getDouble()));
                CommandManager.addParallel(new SetArmPosition("ArmDown", 10, 0, 0));
            } else if (shortShot && !lastShortShot) {
                CommandManager.addCommand(new SetShooterRPM("ShortRPM", 10, Constants.SHOOTER_SHORT_RPM.getDouble()));
                CommandManager.addParallel(new SetShooterHoodAngle("ShortHood", 10, Constants.SHOOTER_SHORT_VALUE.getDouble()));
                CommandManager.addParallel(new SetArmPosition("ArmDown", 10, 0, 0));
            } else if (!prepVisionShooter && lastPrepVisionShooter) {
            } else if (!longShot && lastLongShot) {
                CommandManager.addCommand(new SetShooterRPM("StopShooter", 10, Constants.SHOOTER_STOP.getDouble()));
                CommandManager.addParallel(new SetShooterHoodAngle("LowerHood", 10, Constants.SHOOTER_HOOD_MINVALUE.getDouble()));
                CommandManager.addParallel(new SetArmPosition("ArmDown", 10, 0, 0));
            } else if (!shortShot && lastShortShot) {
                CommandManager.addCommand(new SetShooterRPM("StopShooter", 10, Constants.SHOOTER_STOP.getDouble()));
                CommandManager.addParallel(new SetShooterHoodAngle("LowerHood", 10, Constants.SHOOTER_HOOD_MINVALUE.getDouble()));
                CommandManager.addParallel(new SetArmPosition("ArmDown", 10, 0, 0));
            }
            if (shoot && !lastShoot && shooter.isSpunUp()) {
                CommandManager.addCommand(new SetIntakeMode("Shoot", 10, Intake.Mode.LOADINGSHOOTER));
            } else if (!shoot && lastShoot) {
                CommandManager.addCommand(new SetIntakeMode("Shoot", 10, Intake.Mode.OFF));
            }
        } else {
            shooter.setMode(Shooter.Mode.OVERRIDE);
            if (overrideShot && !lastOverrideShot) {
                CommandManager.addCommand(new SetShooterOverridePower("OverrideShooter", 2000, Constants.SHOOTER_OVERRIDE_POWER.getDouble()));
            } else if (longShot && !lastLongShot) {
                CommandManager.addCommand(new SetShooterOverridePower("OverrideShooter", 2000, Constants.SHOOTER_OVERRIDE_POWER.getDouble()));
                CommandManager.addParallel(new SetShooterHoodAngle("LongHood", 10, Constants.SHOOTER_LONG_VALUE.getDouble()));
            } else if (shortShot && !lastShortShot) {
                CommandManager.addCommand(new SetShooterOverridePower("OverrideShooter", 2000, Constants.SHOOTER_OVERRIDE_POWER.getDouble()));
                CommandManager.addParallel(new SetShooterHoodAngle("ShortHood", 10, Constants.SHOOTER_SHORT_VALUE.getDouble()));
            } else if (!overrideShot && lastOverrideShot) {
                CommandManager.addCommand(new SetShooterOverridePower("StopShooter", 100, Constants.SHOOTER_STOP.getDouble()));
            } else if (!longShot && lastLongShot) {
                CommandManager.addCommand(new SetShooterOverridePower("OverrideShooter", 2000, Constants.SHOOTER_STOP.getDouble()));
                CommandManager.addParallel(new SetShooterHoodAngle("LowerHood", 10, Constants.SHOOTER_HOOD_MINVALUE.getDouble()));
            } else if (!shortShot && lastShortShot) {
                CommandManager.addCommand(new SetShooterOverridePower("OverrideShooter", 2000, Constants.SHOOTER_STOP.getDouble()));
                CommandManager.addParallel(new SetShooterHoodAngle("LowerHood", 10, Constants.SHOOTER_HOOD_MINVALUE.getDouble()));
            }
            if (shoot && !lastShoot) {
                CommandManager.addCommand(new SetIntakeMode("Shoot", 10, Intake.Mode.LOADINGSHOOTER));
            } else if (!shoot && lastShoot) {
                CommandManager.addCommand(new SetIntakeMode("Shoot", 10, Intake.Mode.OFF));
            }
        }
    }

    private void armControl() {
        if (!overrideMode) {
            arm.setMode(Arm.Mode.CONTROLLED);
            if (portcullis && !lastPortcullis) {
                CommandManager.addCommand(new SetArmPosition("PrepPortcullis", 10, Constants.ARM_PORTCULLIS_PREP_X.getDouble(), Constants.ARM_PORTCULLIS_PREP_Y.getDouble()));
                CommandManager.addSequential(new SetArmPosition("Portcullis", 10, Constants.ARM_PORTCULLIS_X.getDouble(), Constants.ARM_PORTCULLIS_Y.getDouble()));
            } else if (sallyPort && !lastSallyPort) {
                CommandManager.addCommand(new SetArmPosition("SallyPort", 10, Constants.ARM_SALLYPORT_X.getDouble(), Constants.ARM_SALLYPORT_Y.getDouble()));
            } else if (drawBridge && !lastDrawBridge) {
                CommandManager.addCommand(new SetArmPosition("DrawBridge", 10, Constants.ARM_DRAWBRIDGE_X.getDouble(), Constants.ARM_DRAWBRIDGE_Y.getDouble()));
            } else if (seeSaw && !lastSeeSaw) {
                CommandManager.addCommand(new SetArmPosition("PrepSeeSaw", 10, Constants.ARM_SEESAW_PREP_X.getDouble(), Constants.ARM_SEESAW_PREP_Y.getDouble()));
                CommandManager.addSequential(new SetArmPosition("SeeSaw", 10, Constants.ARM_SEESAW_X.getDouble(), Constants.ARM_SEESAW_Y.getDouble()));
            } else if (resetArm && !lastResetArm) {
                CommandManager.addCommand(new SetArmPosition("ResetArm", 10, Constants.ARM_MIN_X.getDouble(), Constants.ARM_MIN_Y.getDouble()));
            }
        } else {
            arm.setMode(Arm.Mode.OVERRIDE);
            arm.setMotorSpeeds(tiltOverride, elevatorOverride);;
        }
    }

    @Override
    public void run() {
        input();
        drivetrainControl();
        intakeControl();
        shooterControl();
        armControl();
        latch();
    }
}
