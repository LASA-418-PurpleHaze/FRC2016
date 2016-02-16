package org.lasa.frc2016.command;

public class ManualPrepShooter extends HazyCommand {

    private final double output;

    public ManualPrepShooter(String nm, double t) {
        super(nm, t);
        this.output = 1;
    }

    @Override
    public void start() {
        super.start();
        shooter.setMotorOutput(output);
    }

    @Override
    public boolean isDone() {
        return true;
    }

    @Override
    public void run() {
        //lol
    }

}
