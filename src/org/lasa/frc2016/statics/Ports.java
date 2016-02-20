package org.lasa.frc2016.statics;

public class Ports {

    public static String AXIS_CAMERA_IP = "10.4.18.10";

    // PWM Ports
    public static final int INTAKE_MOTOR = 0;
    public static final int RIGHT_FRONT_MOTOR = 2;
    public static final int RIGHT_BACK_MOTOR = 1;
    public static final int LEFT_FRONT_MOTOR = 4;
    public static final int LEFT_BACK_MOTOR = 3;
    public static final int SHOOTER_MASTER_MOTOR = 0;
    public static final int SHOOTER_SLAVE_MOTOR = 1;
    public static final int LEFT_ARM_TILTER = 18;
    public static final int RIGHT_ARM_TILTER = 13;
    public static final int LEFT_ARM_EXTENDER = 19;
    public static final int RIGHT_ARM_EXTENDER = 14;
    public static final int LEFT_SHOOTER_SERVO = 5;
    public static final int RIGHT_SHOOTER_SERVO = 6;

    // Encoder Ports
    public static final int LEFT_SIDE_A_ENCODER = 6;
    public static final int LEFT_SIDE_B_ENCODER = 7;
    public static final int RIGHT_SIDE_A_ENCODER = 8;
    public static final int RIGHT_SIDE_B_ENCODER = 9;
    public static final int ARM_TILT_A_ENCODER = 0;
    public static final int ARM_TILT_B_ENCODER = 1;
    public static final int ARM_EXTENSION_A_ENCODER = 4;
    public static final int ARM_EXTENSION_B_ENCODER = 5;

    // Analog IO Ports
    public static final int ARM_TILT_POTENTIOMETER = 0;
    public static final int ARM_EXTENSION_POTENTIOMETER = 1;
    
    // Digital IO Ports
    public static final int INTAKE_BUMP_SWITCH = 3;
}
