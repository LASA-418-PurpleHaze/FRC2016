package org.lasa.frc2016.command;

import org.lasa.lib.HazyCommand;
import org.lasa.frc2016.subsystem.Intake;

public class StopIntake extends HazyCommand{

    private HazyCommand Meow;
    private HazyCommand Meow2;

    public StopIntake(String nm, double t) {
        super(nm, t);
        
        
    }
    
    
    @Override
    public void run() {
        Intake.getInstance().setDirection(0);
                
    }
    
}
