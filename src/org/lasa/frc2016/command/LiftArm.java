package org.lasa.frc2016.command;

import org.lasa.lib.controlloop.HazyPID;


public class LiftArm extends HazyCommand {

    public LiftArm(String name, double timeOut) {
        super(name, timeOut);
        HazyPID liftArmPID = new HazyPID();
    }

   @Override
    public void start() {
        
    }

    @Override
    public void run() {
        
    }

    @Override
    public void stop() {
    }

    @Override
    public boolean isDone() {
    }

}
