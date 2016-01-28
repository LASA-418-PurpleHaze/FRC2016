package org.lasa.frc2016.command;

import org.lasa.lib.HazyCommand;
import org.lasa.frc2016.subsystem.Intake;

public class StopIntake extends HazyCommand {

    public StopIntake(String nm, double t) {
        super(nm, t);
    }

    @Override
    public void start() {
        intake.setState(Intake.OFF);
    }

    @Override
    public void run() {
    }

    @Override
    public void stop() {
    }

    // Check this
    @Override
    public boolean isDone() {
        return Math.abs(intake.getIntakeSpeed()) < .1;
    }
    
    

}
