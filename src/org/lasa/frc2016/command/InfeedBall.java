package org.lasa.frc2016.command;

import edu.wpi.first.wpilibj.DriverStation;
import org.lasa.frc2016.subsystem.Intake;

public class InfeedBall extends HazyCommand {

    public InfeedBall(String nm, double t) {
        super(nm, t);
    }

    @Override
    public void start() {
        intake.setState(Intake.INTAKING);
        super.start();
    }

    @Override
    public void run() {
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public boolean isDone() {
        return intake.hasBall();
    }

}
