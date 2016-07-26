package org.lasarobotics.frc2016.input;

import org.lasarobotics.frc2016.command.CommandManager;
import org.lasarobotics.frc2016.command.SetArmPosition;
import org.lasarobotics.frc2016.command.SetIntakeMode;
import org.lasarobotics.frc2016.statics.Constants;
import org.lasarobotics.frc2016.subsystem.Arm;
import org.lasarobotics.frc2016.subsystem.Drivetrain;
import org.lasarobotics.frc2016.subsystem.Intake;
import org.lasarobotics.lib.CheesyDriveHelper;
import org.lasarobotics.lib.HazyJoystick;

public class DriverInput implements Runnable {

    HazyJoystick driver = new HazyJoystick(0, 0.15);
    HazyJoystick operator = new HazyJoystick(1, 0.15);

    private static DriverInput instance;

    private static Drivetrain drivetrain;
    private static Arm arm;
    private final CheesyDriveHelper cheesyDrive;

    private double throttle, wheel, tiltOverride, elevatorOverride;
    private boolean lastIntake, lastOuttake,
            lastPortcullis, lastSallyPort, lastDrawBridge, lastSeeSaw, lastResetArm = false;
    private boolean quickTurn;
    private boolean overrideMode = true;
    private boolean intake, outtake;
    private boolean portcullis, sallyPort, drawBridge, seeSaw, resetArm;

    private DriverInput() {
        drivetrain = Drivetrain.getInstance();
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

    public boolean getQuickTurn() {
        return quickTurn;
    }

    private void input() {
        throttle = -driver.getLeftY();
        wheel = -driver.getRightX();
        quickTurn = driver.getRightBumper();
        
        intake = operator.getRightBumper();
        outtake = operator.getLeftBumper();
        resetArm = operator.getLeftTrigger() > .1;
        portcullis = operator.getA();
        sallyPort = operator.getB();
        drawBridge = operator.getX();
        seeSaw = operator.getY();

        tiltOverride = -operator.getLeftY();
        elevatorOverride = operator.getRightY();

        if (operator.getStart()) {
            overrideMode = false;
        } else if (operator.getBack()) {
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

    private void armControl() {
        if (!overrideMode) {
            arm.setMode(Arm.Mode.CONTROLLED);
            if (portcullis && !lastPortcullis) {
                CommandManager.addCommand(new SetArmPosition("PrepPortcullis", 10, Constants.ARM_PORTCULLIS_PREP_X.getDouble()));
                CommandManager.addSequential(new SetArmPosition("Portcullis", 10, Constants.ARM_PORTCULLIS_X.getDouble()));
            } else if (sallyPort && !lastSallyPort) {
                CommandManager.addCommand(new SetArmPosition("SallyPort", 10, Constants.ARM_SALLYPORT_X.getDouble()));
            } else if (drawBridge && !lastDrawBridge) {
                CommandManager.addCommand(new SetArmPosition("DrawBridge", 10, Constants.ARM_DRAWBRIDGE_X.getDouble()));
            } else if (seeSaw && !lastSeeSaw) {
                CommandManager.addCommand(new SetArmPosition("PrepSeeSaw", 10, Constants.ARM_SEESAW_PREP_X.getDouble()));
                CommandManager.addSequential(new SetArmPosition("SeeSaw", 10, Constants.ARM_SEESAW_X.getDouble()));
            } else if (resetArm && !lastResetArm) {
                CommandManager.addCommand(new SetArmPosition("ResetArm", 10, Constants.ARM_MIN_X.getDouble()));
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
        armControl();
        latch();
    }
}
