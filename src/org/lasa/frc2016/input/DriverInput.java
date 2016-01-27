package org.lasa.frc2016.input;

import org.lasa.frc2016.command.LiftArm;
import org.lasa.lib.CommandManager;

public class DriverInput implements Runnable {

    public void run() {
        //example
        CommandManager.addCommand(new LiftArm("name", 10));
    }
}
