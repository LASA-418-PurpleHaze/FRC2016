package org.lasa.frc2016.statics;

import org.lasa.lib.HazyConstant;

public class Constants extends HazyConstant {

    // Vision Constants
    public static final Constant VISIONHANDLER_INITIAL_DELAY = new Constant("V_InitialDelay", 0);
    public static final Constant VISIONHANDLER_PERIOD = new Constant("V_Period", 200);

    public static final Constant USBCAMERA_DIAGONAL_FOV = new Constant("V_CameraDiagonalFOV", 68.5);
    public static final Constant USBCAMERA_IMAGE_WIDTH = new Constant("V_CameraWidth", 480);
    public static final Constant USBCAMERA_IMAGE_HEIGHT = new Constant("V_CameraHeight", 640);

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
    public static final Constant DRIVETRAIN_PID_KFF = new Constant("D_kFF", 0);
    public static final Constant DRIVETRAIN_PID_DONE_BOUND = new Constant("D_DoneBound", 0);
    public static final Constant DRIVETRAIN_PID_MAXU = new Constant("D_maxU", 1);
    public static final Constant DRIVETRAIN_PID_MINU = new Constant("D_minU", -1);

    public static final Constant GYRO_PID_KP = new Constant("G_kP", 0);
    public static final Constant GYRO_PID_KI = new Constant("G_kI", 0);
    public static final Constant GYRO_PID_KD = new Constant("G_kD", 0);
    public static final Constant GYRO_PID_KFF = new Constant("G_kFF", 0);
    public static final Constant GYRO_PID_DONE_BOUND = new Constant("G_DoneBound", 0);
    public static final Constant GYRO_PID_MAXU = new Constant("G_maxU", 1);
    public static final Constant GYRO_PID_MINU = new Constant("G_minU", -1);

    public static final Constant SHOOTER_PID_KP = new Constant("S_kP", 0);
    public static final Constant SHOOTER_PID_KI = new Constant("S_kI", 0);
    public static final Constant SHOOTER_PID_KD = new Constant("S_kD", 0);
    public static final Constant SHOOTER_PID_KFF = new Constant("S_kFF", 0);
    public static final Constant SHOOTER_PID_DONE_BOUND = new Constant("S_DoneBound", 1000);
    public static final Constant SHOOTER_PID_MAXU = new Constant("S_maxU", 1);
    public static final Constant SHOOTER_PID_MINU = new Constant("S_minU", -1);
    public static final Constant SHOOTER_PID_IZONE = new Constant("S_IZone", 0);
    public static final Constant SHOOTER_PID_RAMPRATE = new Constant("S_RampRate", 48);
    public static final Constant SHOOTER_PID_PROFILE = new Constant("S_Profile", 0);
    public static final Constant SHOOTER_SHORT_VALUE = new Constant("S_ShortShotValue", -.306);
    public static final Constant SHOOTER_LONG_VALUE = new Constant("S_LongShotValue", 0.072);
    public static final Constant SHOOTER_SHORT_RPM = new Constant("S_ShortShotRPM", 90);
    public static final Constant SHOOTER_LONG_RPM = new Constant("S_LongShotRPM", 90);
    public static final Constant SHOOTER_HOOD_MINVALUE = new Constant("S_MinValue", -0.306);
    public static final Constant SHOOTER_HOOD_MAXVALUE = new Constant("S_MaxValue", 0.072);
    public static final Constant SHOOTER_STOP = new Constant("S_StopRPM", 0);
    public static final Constant SHOOTER_OVERRIDE_POWER = new Constant("S_OverridePower", 0.8);;

    public static final Constant ELEVATOR_MAX_EXTENSION = new Constant("E_maxExtension", 15);
    public static final Constant ELEVATOR_POT_CONVERSION = new Constant("E_potConversion", 0);
    public static final Constant ELEVATOR_MP_MAX_VELOCITY = new Constant("EMP_maxV", 15);
    public static final Constant ELEVATOR_MP_MAX_ACCELERATION = new Constant("EMP_maxA", 30);
    public static final Constant ELEVATOR_MPF_KP = new Constant("EMPF_kP", 0);
    public static final Constant ELEVATOR_MPF_KV = new Constant("EMPF_kV", 0);
    public static final Constant ELEVATOR_MPF_KI = new Constant("EMPF_kI", 0);
    public static final Constant ELEVATOR_MPF_KFFV = new Constant("EMPF_kFFV", 0);
    public static final Constant ELEVATOR_MPF_KFFA = new Constant("EMPF_kFFA", 0);
    public static final Constant ELEVATOR_MPF_TUNED_VOLTAGE = new Constant("EMPF_TunedVoltage", 15);
    public static final Constant ELEVATOR_MPF_DONE_CYCLES = new Constant("EMPF_DoneCycles", 5);
    public static final Constant ELEVATOR_MPF_DONE_RANGE = new Constant("EMPF_DoneRange", 5);
    public static final Constant ELEVATOR_MPF_POSITION_DONE_RANGE = new Constant("EMPF_PositonDoneRange", 5);
    public static final Constant ELEVATOR_MPF_MAXU = new Constant("EMPF_maxU", 1);
    public static final Constant ELEVATOR_MPF_MINU = new Constant("EMPF_minU", -1);

    public static final Constant TILT_MAX_ANGLE = new Constant("T_maxAngle", 90);
    public static final Constant TILT_MIN_ANGLE = new Constant("T_minAngle", 0);
    public static final Constant TILT_MAX_VALUE = new Constant("T_maxPot", 5);
    public static final Constant TILT_MIN_VALUE = new Constant("T_minPot", 1);
    public static final Constant TILT_POT_CONVERSION = new Constant("T_potConversion", 0);
    public static final Constant TILT_MP_MAX_VELOCITY = new Constant("TMP_maxV", 15);
    public static final Constant TILT_MP_MAX_ACCELERATION = new Constant("TMP_maxA", 30);
    public static final Constant TILT_MPF_KP = new Constant("TMPF_kP", 0);
    public static final Constant TILT_MPF_KV = new Constant("TMPF_kV", 0);
    public static final Constant TILT_MPF_KI = new Constant("TMPF_kI", 0);
    public static final Constant TILT_MPF_KFFV = new Constant("TMPF_kFFV", 0);
    public static final Constant TILT_MPF_KFFA = new Constant("TMPF_kFFA", 0);
    public static final Constant TILT_MPF_TUNED_VOLTAGE = new Constant("TMPF_TunedVoltage", 15);
    public static final Constant TILT_MPF_DONE_CYCLES = new Constant("TMPF_DoneCycles", 5);
    public static final Constant TILT_MPF_DONE_RANGE = new Constant("TMPF_DoneRange", 5);
    public static final Constant TILT_MPF_POSITION_DONE_RANGE = new Constant("TMPF_PositonDoneRange", 5);
    public static final Constant TILT_MPF_MAXU = new Constant("TMPF_maxU", 1);
    public static final Constant TILT_MPF_MINU = new Constant("TMPF_minU", -1);

    public static final Constant ARM_MIN_X = new Constant("A_minX", 0);
    public static final Constant ARM_MIN_Y = new Constant("A_minY", 0);
    public static final Constant ARM_MAX_X = new Constant("A_minX", 15);
    public static final Constant ARM_MAX_Y = new Constant("A_minY", 20);
    public static final Constant ARM_PORTCULLIS_X = new Constant("A_portX", 2);
    public static final Constant ARM_PORTCULLIS_Y = new Constant("A_portY", 2);
    public static final Constant ARM_PORTCULLIS_PREP_X = new Constant("A_portPrepX", 2);
    public static final Constant ARM_PORTCULLIS_PREP_Y = new Constant("A_portPrepY", 2);
    public static final Constant ARM_DRAWBRIDGE_X = new Constant("A_drawX", 2);
    public static final Constant ARM_DRAWBRIDGE_Y = new Constant("A_drawY", 2);
    public static final Constant ARM_DRAWBRIDGE_PREP_X = new Constant("A_drawPrepX", 2);
    public static final Constant ARM_DRAWBRIDGE_PREP_Y = new Constant("A_drawPrepY", 2);
    public static final Constant ARM_SALLYPORT_X = new Constant("A_sallyX", 2);
    public static final Constant ARM_SALLYPORT_Y = new Constant("A_sallyY", 2);
    public static final Constant ARM_SEESAW_X = new Constant("A_seeSawX", 2);
    public static final Constant ARM_SEESAW_Y = new Constant("A_seeSawX", 2);
    public static final Constant ARM_SEESAW_PREP_X = new Constant("A_seeSawPrepX", 2);
    public static final Constant ARM_SEESAW_PREP_Y = new Constant("A_seeSawPrepY", 2);

    public Constants() {
    }

    @Override
    public String getFileLocation() {
        return "home/admin/constants.txt";
    }

}
