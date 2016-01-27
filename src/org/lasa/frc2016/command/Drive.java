package org.lasa.frc2016.command;

import org.lasa.lib.HazyCommand;
import org.lasa.frc2016.subsystem.Drivetrain;

public class Drive extends HazyCommand {

    public Drive(HazyCommand up, HazyCommand left, String name, double timeOut) {
        super(up, left, name, timeOut);
    }

    @Override
    public void run() {
        Drivetrain.getInstance().setDriveSpeeds(1, 1, 1, 1);
    }
}
