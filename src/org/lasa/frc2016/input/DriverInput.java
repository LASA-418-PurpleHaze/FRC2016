package org.lasa.frc2016.input;

import org.lasa.frc2016.command.LiftArm;
import org.lasa.lib.CommandManager;
import org.lasa.lib.HazyJoystick;

public class DriverInput implements Runnable {

    HazyJoystick driver = new HazyJoystick(0);
    HazyJoystick operator = new HazyJoystick(1);

    private static DriverInput instance;

    public static DriverInput getInstance() {
        return (instance == null) ? instance = new DriverInput() : instance;
    }

    public double getThrottle() {
        return driver.getLeftY();
    }

    public void run() {
        //example
        CommandManager.addCommand(new LiftArm("name", 10));
    }
}
