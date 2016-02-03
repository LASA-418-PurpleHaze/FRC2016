package org.lasa.frc2016.input;

import edu.wpi.first.wpilibj.DriverStation;
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

    private double throttle, wheel;
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
        return driver.getButton(Ports.QUICKTURN_BUTTON);
    }

    public boolean getIntake() {
        return driver.getButton(Ports.INTAKE_BUTTON);
    }

    public boolean getOuttake() {
        return driver.getButton(Ports.OUTTAKE_BUTTON);
    }

    @Override
    public void run() {
        throttle = driver.getLeftY();
        wheel = driver.getRightX();
        
        /**
        if (getIntake()) {
            CommandManager.addCommand(new InfeedBall("Infeed", 10));
        } else if (getOuttake()) {
            CommandManager.addCommand(new OutfeedBall("Outfeed", 10));
        } else {
            CommandManager.addCommand(new StopIntake("StopIntake", 10));
        }
        lastGetIntake = getIntake();
        **/
    }
}
  