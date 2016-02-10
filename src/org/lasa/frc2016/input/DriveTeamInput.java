package org.lasa.frc2016.input;

import org.lasa.frc2016.command.InfeedBall;
import org.lasa.frc2016.command.OutfeedBall;
import org.lasa.frc2016.command.StopIntake;
import org.lasa.frc2016.command.CommandManager;
import org.lasa.frc2016.command.DriveStraight;
import org.lasa.frc2016.command.DriveTurn;
import org.lasa.frc2016.command.SetArmPosition;
import org.lasa.frc2016.command.SpinUpShooter;
import org.lasa.frc2016.command.StopShooter;
import org.lasa.lib.HazyJoystick;

public class DriveTeamInput implements Runnable {

    HazyJoystick driver = new HazyJoystick(0, 0.15);
    HazyJoystick operator = new HazyJoystick(1, 0.15);

    private static DriveTeamInput instance;

    private double throttle, wheel, tiltRaw, elevatorRaw;
    private boolean lastIntake, lastOuttake, lastSpinUpShooterOverride,
            lastPortcullis, lastSallyPort, lastDrawBridge, lastSeeSaw, lastResetArm = false;
    private boolean quickTurn, overrideMode;
    private boolean potatoeMode = true;
    private boolean intake, outtake;
    private boolean portcullis, sallyPort, drawBridge, seeSaw, resetArm;
    private boolean spinUpShooterOverride;

    public static DriveTeamInput getInstance() {
        return (instance == null) ? instance = new DriveTeamInput() : instance;
    }

    public double getThrottle() {
        return throttle;
    }

    public double getWheel() {
        return wheel;
    }

    public boolean getQuickTurn() {
        return quickTurn;
    }

    public boolean getIntake() {
        return intake;
    }

    public boolean getOuttake() {
        return outtake;
    }

    public boolean getSpinUpShooterRaw() {
        return spinUpShooterOverride;
    }
    
    private void DriveTeamInput() {
        throttle = -driver.getLeftY();
        wheel = driver.getRightX();
        quickTurn = driver.getRightBumper();
        resetArm = driver.getA();

        tiltRaw = operator.getLeftY();
        elevatorRaw = operator.getRightY();
        intake = operator.getRightBumper();
        outtake = operator.getLeftBumper();
        portcullis = operator.getA();
        sallyPort = operator.getB();
        drawBridge = operator.getX();
        seeSaw = operator.getY();

        potatoMode = operator.getStart();
        overrideMode = operator.getSelect();

        spinUpShooterOverride = operator.getNorth();
    }
    
    private void latch() {
        lastIntake = intake;
        lastOuttake = outtake;

        lastPortcullis = portcullis;
        lastSallyPort = sallyPort;
        lastDrawBridge = drawBridge;
        lastSeeSaw = seeSaw;
        lastResetArm = resetArm;

        lastSpinUpShooterOverride = spinUpShooterOverride;
    }

    @Override
    public void run() {
        DriveTeamInput();

        if (intake && !lastIntake) {
            CommandManager.addCommand(new InfeedBall("Infeed", 10));
        } else if (outtake && !lastOuttake) {
            CommandManager.addCommand(new OutfeedBall("Outfeed", 10));
        } else if (!intake && lastIntake) {
            CommandManager.addCommand(new StopIntake("StopIntake", 10));
        } else if (!outtake && lastOuttake) {
            CommandManager.addCommand(new StopIntake("StopIntake", 10));
        }

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

        latch();
    }
}
