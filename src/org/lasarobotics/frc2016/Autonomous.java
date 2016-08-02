package org.lasarobotics.frc2016;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.lasarobotics.frc2016.command.CommandGroup;
import org.lasarobotics.frc2016.command.CommandManager;
import org.lasarobotics.frc2016.command.DriveStraight;
import org.lasarobotics.frc2016.command.DriveTurn;
import org.lasarobotics.frc2016.command.SetArmPosition;
import org.lasarobotics.frc2016.command.Wait;
import org.lasarobotics.frc2016.statics.Constants;

public class Autonomous implements Runnable {

    private static Autonomous instance;

    public final int DO_NOTHING = 0;
    public final int DRIVE_OVER = 1;
    public final int DRIVE_OVER_COME_BACK = 2;
    public final int OVER_SEESAW = 3;
    public final int OVER_SEESAW_COME_BACK = 4;
    public final int OVER_SALLY_RETURN_LEFT = 5;
    public final int OVER_SALLY_RETURN_RIGHT = 6;
    
    private int mode = DO_NOTHING;
    
    private Autonomous() {
    }

    public static Autonomous getInstance() {
        return (instance == null) ? instance = new Autonomous() : instance;
    }

    public void start() {
        SmartDashboard.putNumber("AutoMode", DO_NOTHING);
    }

    @Override
    public void run() {
        mode = (int) SmartDashboard.getNumber("AutoMode", DO_NOTHING);
        
        switch (mode) {
            case DRIVE_OVER:
                CommandManager.addCommand(new SetArmPosition("arm up", 1.0, Constants.TILT_UP_ANGLE.getDouble()));
                CommandManager.addCommand(new DriveStraight("Drive over defense", 10.0, Constants.DISTANCE_OVER_DEFENSE.getDouble()));
                break;
            case DRIVE_OVER_COME_BACK:
                CommandManager.addCommand(new SetArmPosition("arm up", 1.0, Constants.TILT_UP_ANGLE.getDouble()));
                CommandManager.addCommand(new DriveStraight("Drive over defense", 7.0, Constants.DISTANCE_OVER_DEFENSE.getDouble()));
                CommandManager.addCommand(new DriveTurn("Turn around", 3.0, 180.0));
                CommandManager.addCommand(new DriveStraight("Drive back over defense", 7.0, Constants.DISTANCE_BACK_OVER_DEFENSE.getDouble()));
                break;
            case OVER_SEESAW:
                CommandManager.addCommand(new SetArmPosition("arm up", 1.0, Constants.TILT_UP_ANGLE.getDouble()));
                CommandManager.addCommand(new DriveStraight("Drive to seesaw", 4.0, Constants.DISTANCE_TO_SEESAW.getDouble()));
                CommandManager.addCommand(new Wait("wait to lower", 1.0));
                CommandManager.addCommand(new SetArmPosition("Lower seesaw", 2.5, Constants.TILT_DOWN_ANGLE.getDouble()));
                CommandManager.addCommand(new Wait("wait to lower", 1.0));
                CommandManager.addCommand(new DriveStraight("Drive over seesaw", 5.0, Constants.DISTANCE_OVER_SEESAW.getDouble()));
                break;
            case OVER_SEESAW_COME_BACK:
                CommandManager.addCommand(new SetArmPosition("arm up", 1.0, Constants.TILT_UP_ANGLE.getDouble()));
                CommandManager.addCommand(new DriveStraight("Drive to seesaw", 4.0, Constants.DISTANCE_TO_SEESAW.getDouble()));
                CommandManager.addCommand(new Wait("wait", 0.5));
                CommandManager.addCommand(new SetArmPosition("Lower seesaw", 2.5, Constants.TILT_DOWN_ANGLE.getDouble()));
                CommandManager.addCommand(new Wait("wait", 0.5));
                CommandManager.addCommand(new DriveStraight("Drive over seesaw", 5.0, Constants.DISTANCE_OVER_SEESAW.getDouble()));
                CommandManager.addCommand(new Wait("wait", 0.5));
                CommandGroup turnAndLiftArm = new CommandGroup("Turn and lift arm", 3.0);
                turnAndLiftArm.addCommand(new DriveTurn("Turn around", 3.0, 90.0));
                turnAndLiftArm.addCommand(new SetArmPosition("Lift Arm", 3.0, Constants.TILT_MIDDLE_ANGLE.getDouble()));
                CommandManager.addCommand(turnAndLiftArm);
                CommandManager.addCommand(new Wait("wait", 0.5));
                CommandManager.addCommand(new DriveTurn("Turn around", 3.0, 90.0));
                CommandManager.addCommand(new DriveStraight("Drive back to seesaw", 7.0, Constants.DISTANCE_BACK_TO_SEESAW.getDouble()));
                CommandManager.addCommand(new Wait("wait", 0.5));
                CommandManager.addCommand(new SetArmPosition("Lower seesaw", 2.5, Constants.TILT_DOWN_ANGLE.getDouble()));
                CommandManager.addCommand(new Wait("wait", 0.5));
                CommandManager.addCommand(new DriveStraight("Drive back over seesaw", 5.0, Constants.DISTANCE_BACK_OVER_SEESAW.getDouble()));
                break;
            case 100:
                CommandManager.addCommand(new SetArmPosition("arm up", 1.0, Constants.TILT_UP_ANGLE.getDouble()));
                CommandManager.addCommand(new SetArmPosition("test", 10.0, -100));
                CommandManager.addCommand(new Wait("wait", 5.0));
                CommandManager.addCommand(new SetArmPosition("test2", 10.0, -200));
                break;
            case 200:
                CommandManager.addCommand(new DriveTurn("test", 10.0, 90.0));
                break;
            case 300:
                CommandManager.addCommand(new DriveStraight("test", 10.0, 48.0));
                break;
            case OVER_SALLY_RETURN_LEFT:
                CommandManager.addCommand(new SetArmPosition("arm up", 1.0, Constants.TILT_UP_ANGLE.getDouble()));
                CommandManager.addCommand(new DriveStraight("ooer", 6.0, 148.0));
                CommandManager.addCommand(new DriveTurn("turn left", 10.0, -90.0));
                CommandManager.addCommand(new DriveStraight("ooer", 6.0, 54.0));
                CommandManager.addCommand(new DriveTurn("turn left", 10.0, -90.0));
                CommandManager.addCommand(new DriveStraight("ooer", 6.0, 72.0));
                break;
            case OVER_SALLY_RETURN_RIGHT:
                CommandManager.addCommand(new SetArmPosition("arm up", 1.0, Constants.TILT_UP_ANGLE.getDouble()));
                CommandManager.addCommand(new DriveStraight("ooer", 6.0, 148.0));
                CommandManager.addCommand(new DriveTurn("turn right", 10.0, 90.0));
                CommandManager.addCommand(new DriveStraight("ooer", 6.0, 54.0));
                CommandManager.addCommand(new DriveTurn("turn right", 10.0, 90.0));
                CommandManager.addCommand(new DriveStraight("ooer", 6.0, 72.0));
                break;
            case DO_NOTHING:
                break;
        }
    }
    
}
