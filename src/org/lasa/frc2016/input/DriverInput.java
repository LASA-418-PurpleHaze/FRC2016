package org.lasa.frc2016.input;

import org.lasa.frc2016.command.AutoPrepShooter;
import org.lasa.frc2016.command.InfeedBall;
import org.lasa.frc2016.command.OutfeedBall;
import org.lasa.frc2016.command.StopIntake;
import org.lasa.frc2016.command.CommandManager;
import org.lasa.frc2016.command.SetArmPosition;
import org.lasa.frc2016.command.Shoot;
import org.lasa.frc2016.command.StopShooter;
import org.lasa.frc2016.subsystem.Arm;
import org.lasa.frc2016.subsystem.Shooter;
import org.lasa.lib.HazyJoystick;

public class DriverInput implements Runnable {

    HazyJoystick driver = new HazyJoystick(0, 0.15);
    HazyJoystick operator = new HazyJoystick(1, 0.15);

    private static DriverInput instance;
    
    private static Shooter shooter;
    private static Arm arm;

    private double throttle, wheel, tiltOverride, elevatorOverride, prepShooterOverride;
    private boolean lastIntake, lastOuttake,
            lastPortcullis, lastSallyPort, lastDrawBridge, lastSeeSaw, lastResetArm,
            lastPrepShooter, lastShoot = false;
    private boolean quickTurn;
    private boolean overrideMode = false;
    private boolean intake, outtake;
    private boolean portcullis, sallyPort, drawBridge, seeSaw, resetArm;
    private boolean prepShooter, shoot;

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
    
    public double getPrepElevatorOverride() {
        return prepShooterOverride;
    }
    
    public boolean getQuickTurn() {
        return quickTurn;
    }

    private void input() {
        throttle = -driver.getLeftY();
        wheel = driver.getRightX();
        quickTurn = driver.getRightBumper();
        resetArm = driver.getA();
        shoot = driver.getRightTrigger() > .1;

        intake = operator.getRightBumper();
        outtake = operator.getLeftBumper();
        portcullis = operator.getA();
        sallyPort = operator.getB();
        drawBridge = operator.getX();
        seeSaw = operator.getY();
        prepShooter = operator.getLeftTrigger() > .1;
        
        tiltOverride = operator.getLeftY();
        elevatorOverride = operator.getRightY();
        prepShooterOverride = operator.getLeftTrigger();

        if(operator.getStart()) {
            overrideMode = false;
        } else if(operator.getSelect()) {
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

        lastPrepShooter = prepShooter;
        lastShoot = shoot;
    }

    private void intakeControl() {
        if (intake && !lastIntake) {
            CommandManager.addCommand(new InfeedBall("Infeed", 10));
        } else if (outtake && !lastOuttake) {
            CommandManager.addCommand(new OutfeedBall("Outfeed", 10));
        } else if (!intake && lastIntake) {
            CommandManager.addCommand(new StopIntake("StopIntake", 10));
        } else if (!outtake && lastOuttake) {
            CommandManager.addCommand(new StopIntake("StopIntake", 10));
        }
    }
    
    private void shooterControl() {
        if (!overrideMode) {
            shooter.setMode(Shooter.Mode.CONTROLLED);
            if (prepShooter && !lastPrepShooter) {
                CommandManager.addCommand(new AutoPrepShooter("AutoPrepShooter", 10));
            } else if (!prepShooter && lastPrepShooter) {
                CommandManager.addCommand(new StopShooter("StopShooter", 10));
            }
            if (shoot && !lastShoot && shooter.isSpunUp()) {
                CommandManager.addCommand(new Shoot("Shoot", 10));
            }
        } else {
            shooter.setMode(Shooter.Mode.OVERRIDE);
            if (shoot && !lastShoot && shooter.isSpunUp()) {
                CommandManager.addCommand(new Shoot("Shoot", 10));
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
