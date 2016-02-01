package org.lasa.frc2016.command;

import org.lasa.lib.HazyCommand;

public class Drive extends HazyCommand {

    public Drive(HazyCommand up, HazyCommand left, String name, double timeOut) {
        super(up, left, name, timeOut);
    }

    @Override
    public void run() {
    }
}
