package org.lasa.lib.controlloop;

public class HazyMotionPlanning {

    private double maxV, maxA, motorSpeed;
    
    public HazyMotionPlanning(double maxV, double maxA) {
        this.maxV = maxV;
        this.maxA = maxA;
    }
    
    public double getMotorSpeed() {
        return motorSpeed;
    }
    
    public void createMotionPlan() {
        
    }
}
