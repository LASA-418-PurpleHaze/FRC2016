package org.lasa.frc2016.command;

import org.lasa.lib.HazyCommand;
import org.lasa.frc2016.subsystem.Intake;

public class OutfeedBall extends HazyCommand{
    
    public OutfeedBall(HazyCommand up, HazyCommand left, String name, double timeOut) {
        super(up, left, name, timeOut);
    }
    
    
    
    @Override
    public void run() {
        Intake.getInstance().setDirection(-1);
                
    }
    
}
