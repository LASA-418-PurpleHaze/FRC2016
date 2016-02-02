package org.lasa.frc2016.command;

<<<<<<< HEAD
import org.lasa.frc2016.subsystem.Intake;
import org.lasa.lib.HazyCommand;

=======
>>>>>>> 14b79104e3584b3f2388bb2768dd5b3cdb7fbd83
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
