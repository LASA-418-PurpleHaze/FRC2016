package org.lasa.frc2016.command;

import org.lasa.frc2016.subsystem.Intake;
import org.lasa.lib.HazyCommand;

public class InfeedBall extends HazyCommand {

    public InfeedBall(String nm, double t) {
        super(nm, t);
    }

    @Override
    public void start() {
        intake.setState(Intake.INTAKING);
    }

    @Override
    public void run() {

    }

    @Override
    public void stop() {
    }

    @Override
    public boolean isDone() {
        return intake.hasBall();
    }

}
