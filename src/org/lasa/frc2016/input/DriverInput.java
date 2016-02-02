package org.lasa.frc2016.input;

import org.lasa.frc2016.command.InfeedBall;
import org.lasa.frc2016.command.OutfeedBall;
<<<<<<< HEAD
import org.lasa.frc2016.command.StopIntake;
import org.lasa.frc2016.statics.Ports;
import org.lasa.lib.CommandManager;
=======
import org.lasa.frc2016.statics.Constants;
import org.lasa.frc2016.command.CommandManager;
>>>>>>> 14b79104e3584b3f2388bb2768dd5b3cdb7fbd83
import org.lasa.lib.HazyJoystick;

public class DriverInput implements Runnable {

    HazyJoystick driver = new HazyJoystick(0);
    HazyJoystick operator = new HazyJoystick(1);

    private static DriverInput instance;
    
    private boolean lastGetIntake = false;

    public static DriverInput getInstance() {
        return (instance == null) ? instance = new DriverInput() : instance;
    }

    public double getThrottle() {
        return driver.getLeftY();
    }

    public double getWheel() {
        return driver.getRightX();
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
<<<<<<< HEAD
        if (getIntake() && !lastGetIntake) {
=======
        CommandManager.addContinuous(new CheesyDrive("CheesyDrive", 1000));

        if (getIntake()) {
>>>>>>> 14b79104e3584b3f2388bb2768dd5b3cdb7fbd83
            CommandManager.addCommand(new InfeedBall("Infeed", 10));
        } else if (getOuttake()) {
            CommandManager.addCommand(new OutfeedBall("Outfeed", 10));
        } else {
            CommandManager.addCommand(new StopIntake("StopIntake", 10));
        }
        lastGetIntake = getIntake();
    }
}
  