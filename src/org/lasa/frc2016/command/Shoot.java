package org.lasa.frc2016.command;

import org.lasa.frc2016.subsystem.Intake;

public class Shoot extends HazyCommand {

    public Shoot(String nm, double t) {
        super(nm, t);
    }

    @Override
    public void start() {
        super.start();
        intake.setMode(Intake.Mode.LOADINGSHOOTER);
    }

    @Override
    public void stop() {
        super.stop();
        intake.setMode(Intake.Mode.OFF);
    }

    @Override
    public void run() {
    }

    @Override
    public boolean isDone() {
        return !intake.hasBall();
    }

}
