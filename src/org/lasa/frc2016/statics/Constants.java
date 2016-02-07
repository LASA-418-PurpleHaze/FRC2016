package org.lasa.frc2016.statics;

import org.lasa.lib.HazyConstant;

public class Constants extends HazyConstant {

    // Vision Constants
    public static final Constant VISIONHANDLER_INITIAL_DELAY = new Constant("V_InitialDelay", 10);
    public static final Constant VISIONHANDLER_PERIOD = new Constant("V_Period", 200);

    public static final Constant USBCAMERA_IMAGE_WIDTH = new Constant("V_CameraWidth", 480);
    public static final Constant USBCAMERA_IMAGE_Height = new Constant("V_CameraHeight", 640);

    public static final Constant HAZYVISION_HUE_LOWER_BOUND = new Constant("V_HueLower", 44);
    public static final Constant HAZYVISION_HUE_UPPER_BOUND = new Constant("V_HueUpper", 88);
    public static final Constant HAZYVISION_SATURATION_LOWER_BOUND = new Constant("V_SatLower", 199);
    public static final Constant HAZYVISION_SATURATION_UPPER_BOUND = new Constant("V_SatUpper", 255);
    public static final Constant HAZYVISION_LUMINENCE_LOWER_BOUND = new Constant("V_LumLower", 37);
    public static final Constant HAZYVISION_LUMINENCE_UPPER_BOUND = new Constant("V_LumUpper", 135);

    public static final Constant DRIVE_SENSITIVITY = new Constant("D_Sensitivity", .65);
    public static final Constant DRIVETRAIN_PID_KP = new Constant("D_kP", 0);
    public static final Constant DRIVETRAIN_PID_KI = new Constant("D_kI", 0);
    public static final Constant DRIVETRAIN_PID_KD = new Constant("D_kD", 0);
    public static final Constant DRIVETRAIN_PID_KF = new Constant("D_kF", 0);
    public static final Constant DRIVETRAIN_PID_DONE_BOUND = new Constant("D_DoneBound", 0);
    public static final Constant DRIVETRAIN_PID_MAXU = new Constant("D_maxU", 1);
    public static final Constant DRIVETRAIN_PID_MINU = new Constant("D_minU", -1);

    public static final Constant GYRO_PID_KP = new Constant("G_kP", 0);
    public static final Constant GYRO_PID_KI = new Constant("G_kI", 0);
    public static final Constant GYRO_PID_KD = new Constant("G_kD", 0);
    public static final Constant GYRO_PID_KF = new Constant("G_kF", 0);
    public static final Constant GYRO_PID_DONE_BOUND = new Constant("G_DoneBound", 0);
    public static final Constant GYRO_PID_MAXU = new Constant("G_maxU", 1);
    public static final Constant GYRO_PID_MINU = new Constant("G_minU", -1);

    public static final Constant FLYWHEEL_PID_KP = new Constant("F_kP", 0);
    public static final Constant FLYWHEEL_PID_KI = new Constant("F_kI", 0);
    public static final Constant FLYWHEEL_PID_KD = new Constant("F_kD", 0);
    public static final Constant FLYWHEEL_PID_KF = new Constant("F_kF", 0);
    public static final Constant FLYWHEEL_PID_DONE_BOUND = new Constant("F_DoneBound", 1000);
    public static final Constant FLYWHEEL_PID_MAXU = new Constant("F_maxU", 1);
    public static final Constant FLYWHEEL_PID_MINU = new Constant("F_minU", -1);
    public static final Constant FLYWHEEL_PID_IZONE = new Constant("F_IZone", 0);
    public static final Constant FLYWHEEL_PID_RAMPRATE = new Constant("F_RampRate", 0);
    public static final Constant FLYWHEEL_PID_PROFILE = new Constant("F_Profile", 0);
    public static final Constant FLYWHEEL_SHORT_RPM = new Constant("F_ShortShotRPM", 0);
    public static final Constant FLYWHEEL_LONG_RPM = new Constant("F_LongShotRPM", 0);

    public static final Constant ELEVATOR_PID_KP = new Constant("E_kP", 0);
    public static final Constant ELEVATOR_PID_KI = new Constant("E_kI", 0);
    public static final Constant ELEVATOR_PID_KD = new Constant("E_kD", 0);
    public static final Constant ELEVATOR_PID_KF = new Constant("E_kF", 0);
    public static final Constant ELEVATOR_PID_DONE_BOUND = new Constant("E_DoneBound", 0);
    public static final Constant ELEVATOR_PID_MAXU = new Constant("E_maxU", 1);
    public static final Constant ELEVATOR_PID_MINU = new Constant("E_minU", -1);

    public static final Constant TILT_PID_KP = new Constant("T_kP", 0);
    public static final Constant TILT_PID_KI = new Constant("T_kI", 0);
    public static final Constant TILT_PID_KD = new Constant("T_kD", 0);
    public static final Constant TILT_PID_KF = new Constant("T_kF", 0);
    public static final Constant TILT_PID_DONE_BOUND = new Constant("T_DoneBound", 0);
    public static final Constant TILT_PID_MAXU = new Constant("T_maxU", 1);
    public static final Constant TILT_PID_MINU = new Constant("T_minU", -1);

    @Override
    public String getFileLocation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
