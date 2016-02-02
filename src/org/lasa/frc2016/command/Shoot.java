package org.lasa.frc2016.command;

import org.lasa.frc2016.subsystem.Intake;
import org.lasa.frc2016.command.HazyCommand;

public class Shoot extends HazyCommand {

    public Shoot(String nm, double t) {
        super(nm, t);
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
