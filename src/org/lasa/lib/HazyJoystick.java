/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lasa.lib;

import edu.wpi.first.wpilibj.Joystick;
import org.lasa.frc2016.statics.Constants;
/**
 *
 * @author LASA Robotics
 */
public class HazyJoystick {

    Joystick controller;

    public HazyJoystick(int joystickPort) {
        controller = new Joystick(joystickPort);
    }

    public double getLeftX() {
        return handleDeadBand(controller.getRawAxis(0));
    }

    public double getLeftY() {
        return handleDeadBand(controller.getRawAxis(1));
    }

    public double getRightX() {
        return handleDeadBand(controller.getRawAxis(2));
    }

    public double getRightY() {
        return handleDeadBand(controller.getRawAxis(3));
    }

    public boolean getButton(int button) {
        return controller.getRawButton(button);
    }

    private double handleDeadBand(double input) {
        if (Math.abs(input) > Constants.DEADBAND) {
            if (input > 0) {
                return (input - Constants.DEADBAND) / (1 - Constants.DEADBAND);
            } else {
                return (input + Constants.DEADBAND) / (1 - Constants.DEADBAND);
            }
        } else {
            return 0.0;
        }

    }
}
