/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lasa.frc2016.command;

import org.lasa.frc2016.subsystem.Drivetrain;
import org.lasa.lib.HazyCommand;

/**
 *
 * @author gijs
 */
public class CheesyDrive extends HazyCommand {

    Drivetrain drivetrain;

    public CheesyDrive(String nm, double t) {
        super(nm, t);
    }

    @Override
    public void run() {
        double throttle = driverInput.getThrottle();
        //do cheesy drive stuff
        drivetrain.setDriveSpeeds(420, 420);
    }
}
