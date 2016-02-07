package org.lasa.lib;

import edu.wpi.first.wpilibj.Joystick;

public class HazyJoystick {

    Joystick controller;
    double deadband;

    public HazyJoystick(int joystickPort, double deadband) {
        controller = new Joystick(joystickPort);
        this.deadband = deadband;
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
        if (Math.abs(input) > deadband) {
            if (input > 0) {
                return Math.pow((input - deadband) / (1 - deadband), 2);
            } else {
                return -Math.pow((input + deadband) / (1 - deadband), 2);
            }
        } else {
            return 0.0;
        }

    }
}
