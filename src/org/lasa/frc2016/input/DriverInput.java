package org.lasa.frc2016.input;

import org.lasa.frc2016.command.CommandManager;
import org.lasa.frc2016.command.SetArmPosition;
import org.lasa.frc2016.command.SetIntakeMode;
import org.lasa.frc2016.command.SetShooterHoodAngle;
import org.lasa.frc2016.command.SetShooterOverridePower;
import org.lasa.frc2016.command.SetShooterRPM;
import org.lasa.frc2016.statics.Constants;
import org.lasa.frc2016.subsystem.Arm;
import org.lasa.frc2016.subsystem.Intake;
import org.lasa.frc2016.subsystem.Shooter;
import org.lasa.lib.HazyJoystick;

public class DriverInput implements Runnable {

    HazyJoystick driver = new HazyJoystick(0, 0.15);
    HazyJoystick operator = new HazyJoystick(1, 0.15);

    private static DriverInput instance;

    private static Shooter shooter;
    private static Arm arm;

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
        shooter = Shooter.getInstance();
        arm = Arm.getInstance();
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
        throttle = -driver.getLeftY();
        wheel = driver.getRightX();
        quickTurn = driver.getRightBumper();
        shoot = driver.getRightTrigger() > .1;

        intake = operator.getRightBumper();
        outtake = operator.getLeftBumper();
        resetArm = operator.getA();
        portcullis = false;
        sallyPort = operator.getB();
        drawBridge = operator.getX();
        seeSaw = operator.getY();
        prepVisionShooter = operator.getNorth();
        longShot = operator.getEast();
        shortShot = operator.getWest();

        tiltOverride = operator.getLeftY();
        elevatorOverride = operator.getRightY();
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
            } else if (longShot && !lastLongShot) {
                CommandManager.addCommand(new SetShooterRPM("LongRPM", 10, Constants.SHOOTER_LONG_RPM.getDouble()));
                CommandManager.addParallel(new SetShooterHoodAngle("LongHood", 10, Constants.SHOOTER_LONG_ANGLE.getInt()));
            } else if (shortShot && !lastShortShot) {
                CommandManager.addCommand(new SetShooterRPM("ShortRPM", 10, Constants.SHOOTER_SHORT_RPM.getDouble()));
                CommandManager.addParallel(new SetShooterHoodAngle("ShortHood", 10, Constants.SHOOTER_SHORT_ANGLE.getInt()));
            } else if (!prepVisionShooter && lastPrepVisionShooter) {
            } else if (!longShot && lastLongShot) {
                CommandManager.addCommand(new SetShooterRPM("StopShooter", 10, Constants.SHOOTER_STOP_RPM.getDouble()));
                CommandManager.addParallel(new SetShooterHoodAngle("LowerHood", 10, Constants.SHOOTER_MIN_ANGLE.getDouble()));
            } else if (!shortShot && lastShortShot) {
                CommandManager.addCommand(new SetShooterRPM("StopShooter", 10, Constants.SHOOTER_STOP_RPM.getDouble()));
                CommandManager.addParallel(new SetShooterHoodAngle("LowerHood", 10, Constants.SHOOTER_MIN_ANGLE.getDouble()));
            }
            if (shoot && !lastShoot && shooter.isSpunUp()) {
                CommandManager.addCommand(new SetIntakeMode("Shoot", 10, Intake.Mode.LOADINGSHOOTER));
            }
        } else {
            shooter.setMode(Shooter.Mode.OVERRIDE);
            if (overrideShot && !lastOverrideShot) {
                CommandManager.addCommand(new SetShooterOverridePower("OverrideShooter", wheel, throttle));
            } else if (!overrideShot && lastOverrideShot) {
                CommandManager.addCommand(new SetShooterRPM("StopShooter", 10, Constants.SHOOTER_STOP_RPM.getDouble()));
            }
            if (shoot && !lastShoot) {
                CommandManager.addCommand(new SetIntakeMode("Shoot", 10, Intake.Mode.LOADINGSHOOTER));
            }
        }
    }

    private void armControl() {
        if (!overrideMode) {
            arm.setMode(Arm.Mode.CONTROLLED);
            if (portcullis && !lastPortcullis) {
                CommandManager.addCommand(new SetArmPosition("PrepPortcullis", 10, 10, 10));
                CommandManager.addSequential(new SetArmPosition("Portcullis", 10, 10, 24));
            } else if (sallyPort && !lastSallyPort) {
                CommandManager.addCommand(new SetArmPosition("SallyPort", 10, 8, 12));
            } else if (drawBridge && !lastDrawBridge) {
                CommandManager.addCommand(new SetArmPosition("DrawBridge", 10, 15, 16));
            } else if (seeSaw && !lastSeeSaw) {
                CommandManager.addCommand(new SetArmPosition("PrepSeeSaw", 10, 15, 1));
                CommandManager.addSequential(new SetArmPosition("SeeSaw", 10, 15, 0));
            } else if (resetArm && !lastResetArm) {
                CommandManager.addCommand(new SetArmPosition("ResetArm", 10, 0, 0));
            }
        } else {
            arm.setMode(Arm.Mode.OVERRIDE);
            arm.setMotorSpeeds(tiltOverride, elevatorOverride);;
        }
    }

    @Override
    public void run() {
        input();
        intakeControl();
        shooterControl();
        armControl();
        latch();
    }
}
