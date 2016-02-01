package org.lasa.frc2016.command;

import org.lasa.frc2016.subsystem.Intake;

public class OutfeedBall extends HazyCommand {

    public OutfeedBall(String name, double timeOut) {
        super(name, timeOut);
    }

    @Override
    public void start() {
        intake.setState(Intake.OUTTAKING);
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
