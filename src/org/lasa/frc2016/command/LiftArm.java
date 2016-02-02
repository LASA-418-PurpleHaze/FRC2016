    package org.lasa.frc2016.command;

import org.lasa.frc2016.subsystem.Intake;

public class LiftArm extends HazyCommand {

    public LiftArm(String name, double timeOut) {
        super(name, timeOut);
    }

   @Override
    public void start() {
        intake.setState(Intake.LOADSHOOTER);
    }

    @Override
    public void run() {
    }

    @Override
    public void stop() {
    }

    @Override
    public boolean isDone() {
        return !intake.hasBall();
    }

}
