package org.lasarobotics.lib;

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

    public double getLeftTrigger() {
        return controller.getRawAxis(2);
    }

    public double getRightTrigger() {
        return controller.getRawAxis(3);
    }

    public double getRightX() {
        return handleDeadBand(controller.getRawAxis(4));
    }

    public double getRightY() {
        return handleDeadBand(controller.getRawAxis(5));
    }

    public boolean getA() {
        return controller.getRawButton(1);
    }

    public boolean getB() {
        return controller.getRawButton(2);
    }

    public boolean getX() {
        return controller.getRawButton(3);
    }

    public boolean getY() {
        return controller.getRawButton(4);
    }

    public boolean getLeftBumper() {
        return controller.getRawButton(5);
    }

    public boolean getRightBumper() {
        return controller.getRawButton(6);
    }

    public boolean getBack() {
        return controller.getRawButton(7);
    }

    public boolean getStart() {
        return controller.getRawButton(8);
    }

    public boolean getNorth() {
        return controller.getPOV(0) == 0;
    }

    public boolean getEast() {
        return controller.getPOV(0) == 90;
    }

    public boolean getSouth() {
        return controller.getPOV(0) == 180;
    }

    public boolean getWest() {
        return controller.getPOV(0) == 270;
    }

    private double handleDeadBand(double input) {
        if (Math.abs(input) > deadband) {
            if (input > 0) {
                return (input - deadband) / (1 - deadband);
            } else {
                return (input + deadband) / (1 - deadband);
            }
        } else {
            return 0.0;
        }

    }
}
