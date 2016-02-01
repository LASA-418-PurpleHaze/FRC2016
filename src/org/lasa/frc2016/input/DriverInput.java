package org.lasa.frc2016.input;

import org.lasa.frc2016.command.CheesyDrive;
import org.lasa.frc2016.command.InfeedBall;
import org.lasa.frc2016.command.LiftArm;
import org.lasa.frc2016.command.OutfeedBall;
import org.lasa.frc2016.statics.Constants;
import org.lasa.frc2016.command.CommandManager;
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
    
    public double getWheel() {
        return driver.getRightX();
    }
    
    public boolean getQuickTurn() {
        return driver.getButton(Constants.QUICKTURN_BUTTON);
    }
    
    public boolean getIntake() {
        return operator.getButton(Constants.INTAKE_BUTTON);
    }
    
    public boolean getOuttake() {
        return operator.getButton(Constants.OUTTAKE_BUTTON);
    }

    @Override
    public void run() {
        CommandManager.addContinuous(new CheesyDrive("CheesyDrive", 1000));
        
        if(getIntake()) {
            CommandManager.addCommand(new InfeedBall("Infeed", 10));
        } else if(getOuttake()) {
            CommandManager.addCommand(new OutfeedBall("Outfeed", 10));
        }
    }
}
