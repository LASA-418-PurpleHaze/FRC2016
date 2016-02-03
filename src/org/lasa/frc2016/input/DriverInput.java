package org.lasa.frc2016.input;

import org.lasa.frc2016.command.InfeedBall;
import org.lasa.frc2016.command.OutfeedBall;
import org.lasa.frc2016.command.StopIntake;
import org.lasa.frc2016.statics.Ports;
import org.lasa.frc2016.command.CommandManager;
import org.lasa.lib.HazyJoystick;

public class DriverInput implements Runnable {

    HazyJoystick driver = new HazyJoystick(0);
    HazyJoystick operator = new HazyJoystick(1);

    private static DriverInput instance;
    
    private boolean lastGetIntake = false;

    private double leftSide, rightSide;
    public static DriverInput getInstance() {
        return (instance == null) ? instance = new DriverInput() : instance;
    }

    public double getLeftSide() {
        return leftSide;
    }

    public double getRightSide() {
        return rightSide;
    }

    public boolean getQuickTurn() {
        return driver.getButton(Ports.QUICKTURN_BUTTON);
    }

    public boolean getIntake() {
        return operator.getButton(Ports.INTAKE_BUTTON);
    }

    public boolean getOuttake() {
        return operator.getButton(Ports.OUTTAKE_BUTTON);
    }

    @Override
    public void run() {
        leftSide = driver.getLeftY();
        rightSide = driver.getRightY();
        
        if (getIntake()) {
            CommandManager.addCommand(new InfeedBall("Infeed", 10));
        } else if (getOuttake()) {
            CommandManager.addCommand(new OutfeedBall("Outfeed", 10));
        } else {
            CommandManager.addCommand(new StopIntake("StopIntake", 10));
        }
        lastGetIntake = getIntake();
    }
}
  