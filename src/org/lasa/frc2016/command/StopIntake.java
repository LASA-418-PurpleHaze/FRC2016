package org.lasa.frc2016.command;

import org.lasa.frc2016.subsystem.Intake;

public class StopIntake extends HazyCommand {

    public StopIntake(String nm, double t) {
        super(nm, t);
    }

    @Override
    public void start() {
        intake.setMode(Intake.Mode.OFF);
    }

    @Override
    public void run() {
    }

    @Override
    public void stop() {
    }

    @Override
    public boolean isDone() {
        return true;
    }
}
