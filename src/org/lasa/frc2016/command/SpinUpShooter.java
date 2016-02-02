package org.lasa.frc2016.command;

import org.lasa.frc2016.statics.Constants;
import org.lasa.frc2016.command.HazyCommand;

public class SpinUpShooter extends HazyCommand {

    public SpinUpShooter(String nm, double t) {
        super(nm, t);
    }

   @Override
    public void start() {
        flywheel.setFlywheelSpeed(Constants.FLYWHEEL_SPINUP_SPEED);
    }

    @Override
    public void run() {
    }

    @Override
    public void stop() {
    }

    @Override
    public boolean isDone() {
        return flywheel.getFlywheelSpeed() > Constants.FLYWHEEL_SPINUP_SPEED;
    }

}
