package org.lasa.frc2016.command;

import org.lasa.lib.HazyCommand;
import org.lasa.frc2016.subsystem.Intake;

public class InfeedBall extends HazyCommand{

    private HazyCommand Meow;
    private HazyCommand Meow2;

    public InfeedBall(String nm, double t) {
        super(nm, t);
        setUp(Meow);
        setLeft(Meow2);
        
    }
    
    
    @Override
    public void run() {
        Intake.getInstance().setDirection(1);
                
    }
    
}
