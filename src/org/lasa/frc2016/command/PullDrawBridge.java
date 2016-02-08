package org.lasa.frc2016.command;

public class PullDrawBridge extends HazyCommand{

    public PullDrawBridge(String nm, double t) {
        super(nm, t);
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void start() {
        arm.setControlPoint(15, 10);
    }
    
    @Override
    public void stop(){
        
    }
    
    @Override
    public boolean isDone(){
        return arm.getArmDistancePID().onTarget() && arm.getArmAnglePID().onTarget();
    }
}
