package org.lasa.frc2016.command;

public class DriveTurn extends HazyCommand {
    
    private double angle;

    public DriveTurn(String nm, double t, double angle) {
        super(nm, t);
        this.angle = angle;
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
