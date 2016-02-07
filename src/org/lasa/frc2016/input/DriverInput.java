package org.lasa.frc2016.input;

import org.lasa.frc2016.command.InfeedBall;
import org.lasa.frc2016.command.OutfeedBall;
import org.lasa.frc2016.command.StopIntake;
import org.lasa.frc2016.statics.Ports;
import org.lasa.frc2016.command.CommandManager;
import org.lasa.lib.HazyJoystick;

public class DriverInput implements Runnable {

    HazyJoystick driver = new HazyJoystick(0, 0.15);
    HazyJoystick operator = new HazyJoystick(1, 0.15);

    private static DriverInput instance;
    
    private double throttle, wheel;
    private boolean lastGetIntake, lastGetOuttake = false;
    private boolean quickTurn, intake, outtake;

    public static DriverInput getInstance() {
        return (instance == null) ? instance = new DriverInput() : instance;
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

    @Override
    public void run() {
        throttle = driver.getLeftY();
        wheel = driver.getRightX();
        quickTurn = driver.getButton(Ports.QUICKTURN_BUTTON);
        intake = operator.getButton(Ports.INTAKE_BUTTON);
        outtake = operator.getButton(Ports.OUTTAKE_BUTTON);
        
        if (getIntake() && !lastGetIntake) {
            CommandManager.addCommand(new InfeedBall("Infeed", 10));
        } else if (getOuttake() && !lastGetOuttake) {
            CommandManager.addCommand(new OutfeedBall("Outfeed", 10));
        } else {
            CommandManager.addCommand(new StopIntake("StopIntake", 10));
        }
        lastGetIntake = getIntake();
        lastGetOuttake = getOuttake();
    }
}
  