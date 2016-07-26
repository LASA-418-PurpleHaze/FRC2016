package org.lasarobotics.frc2016.statics;

import org.lasarobotics.lib.HazyConstant;

public class Constants extends HazyConstant {

    //Drivetrain PID
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

    //Arm PID
    public static final Constant TILT_MAX_POWER = new Constant ("T_maxPower", 0.3);
    public static final Constant TILT_MIN_POWER = new Constant ("T_minPower", -0.3);
    public static final Constant TILT_MAX_ANGLE = new Constant("T_maxAngle", 90);
    public static final Constant TILT_MIN_ANGLE = new Constant("T_minAngle", 0);
    public static final Constant TILT_DONE_CYCLES = new Constant("TMPF_DoneCycles", 5);
    public static final Constant TILT_DONE_RANGE = new Constant("TMPF_DoneRange", 5);
    
    //Arm setpoints
    public static final Constant TILT_UP_ANGLE = new Constant("T_UpAngle", 0.0);
    public static final Constant TILT_MIDDLE_ANGLE = new Constant("T_MiddleAngle", 0.0);
    public static final Constant TILT_DOWN_ANGLE = new Constant("T_DownAngle", 0.0);
   
    @Override
    public String getFileLocation() {
        return "home/admin/constants.txt";
    }

}
