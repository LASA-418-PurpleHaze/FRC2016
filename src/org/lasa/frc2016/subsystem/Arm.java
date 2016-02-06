/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lasa.frc2016.subsystem;

import edu.wpi.first.wpilibj.VictorSP;
import org.lasa.lib.controlloop.HazyPID;

/**
 *
 * @author LASA Robotics
 */
public class Arm extends HazySubsystem {
    
    private static Arm instance;
    
    private Arm() {
        VictorSP leftArmChannel = new VictorSP(0);
        VictorSP rightArmChannel = new VictorSP(1);
    }
    
    public static Arm getInstance() {
        return (instance == null) ? instance = new Arm() : instance;
        HazyPID armPID = new HazyPID
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateConstants() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void pushToDashboard() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
