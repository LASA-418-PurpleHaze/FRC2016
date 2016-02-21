package org.lasa.frc2016.command;

import org.lasa.frc2016.subsystem.Intake.Mode;

public class SetIntakeMode extends HazyCommand {

    Mode mode;

    public SetIntakeMode(String nm, double t, Mode intakeMode) {
        super(nm, t);
        this.mode = intakeMode;
    }

    @Override
    public boolean isDone() {
        return true;
    }

    @Override
    public void run() {
    }

    @Override
    public void start() {
        super.start();
        intake.setMode(mode);
    }

    @Override
    public void stop() {
        super.stop();
    }

}
