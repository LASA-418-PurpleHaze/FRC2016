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
    public static final Constant TILT_MAX_POWER = new Constant ("T_maxPower", 0.7);
    public static final Constant TILT_MIN_POWER = new Constant ("T_minPower", -0.7);
    public static final Constant TILT_MAX_ANGLE = new Constant("T_maxAngle", 90);
    public static final Constant TILT_MIN_ANGLE = new Constant("T_minAngle", 0);
    public static final Constant TILT_DONE_CYCLES = new Constant("TMPF_DoneCycles", 5);
    public static final Constant TILT_DONE_RANGE = new Constant("TMPF_DoneRange", 5);
    public static final Constant TILT_KP = new Constant("T_kP", 0.008);
    public static final Constant TILT_KI = new Constant("T_kI", 0.0);
    public static final Constant TILT_KD = new Constant("T_kD", 0.0);
    
    //Arm setpoints
    public static final Constant TILT_UP_ANGLE = new Constant("T_UpAngle", 0.0);
    public static final Constant TILT_MIDDLE_ANGLE = new Constant("T_MiddleAngle", -107.0);
    public static final Constant TILT_DOWN_ANGLE = new Constant("T_DownAngle", -228.0);
    
    //Autonomous
    public static final Constant DISTANCE_OVER_DEFENSE = new Constant("A_DistanceOverDefense", 0.0);
    public static final Constant DISTANCE_BACK_OVER_DEFENSE = new Constant("A_DistanceBackOverDefense", 0.0);
    public static final Constant DISTANCE_TO_SEESAW = new Constant("A_DistanceToSeesaw", 0.0);
    public static final Constant DISTANCE_OVER_SEESAW = new Constant("A_DistanceOverSeesaw", 0.0);
    public static final Constant DISTANCE_BACK_TO_SEESAW = new Constant("A_DistanceBackToSeesaw", 0.0);
    public static final Constant DISTANCE_BACK_OVER_SEESAW = new Constant("A_DistanceBackOverSeesaw", 0.0);
    
    //Intake
    public static final Constant INTAKE_SPEED = new Constant("I_Speed", 0.7);
   
    @Override
    public String getFileLocation() {
        return "home/admin/constants.txt";
    }

}
