package org.lasa.frc2016.command;

import org.lasa.frc2016.lib.HazyCommand;

public class DriveForTime extends HazyCommand {

    public DriveForTime(HazyCommand up, HazyCommand left, String name, double timeOut) {
        super(up, left, name, timeOut);
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
