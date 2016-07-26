package org.lasarobotics.frc2016;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.lasarobotics.frc2016.command.CommandGroup;
import org.lasarobotics.frc2016.command.CommandManager;
import org.lasarobotics.frc2016.command.DriveStraight;
import org.lasarobotics.frc2016.command.DriveTurn;
import org.lasarobotics.frc2016.command.SetArmPosition;
import org.lasarobotics.frc2016.statics.Constants;

public class Autonomous implements Runnable {

    private static Autonomous instance;

    public final int DO_NOTHING = 0;
    public final int DRIVE_OVER = 1;
    public final int DRIVE_OVER_COME_BACK = 2;
    public final int OVER_SEESAW = 3;
    public final int OVER_SEESAW_COME_BACK = 4;
    
    private int mode = DO_NOTHING;
    
    private Autonomous() {
    }

    public static Autonomous getInstance() {
        return (instance == null) ? instance = new Autonomous() : instance;
    }

    public void start() {
        mode = (int) SmartDashboard.getNumber("AutoMode", DO_NOTHING);
    }

    @Override
    public void run() {
        switch (mode) {
            case DRIVE_OVER:
                CommandManager.addCommand(new DriveStraight("Drive over defense", 10.0, 123));
                break;
            case DRIVE_OVER_COME_BACK:
                CommandManager.addCommand(new DriveStraight("Drive over defense", 7.0, 123));
                CommandManager.addCommand(new DriveTurn("Turn around", 3.0, 180.0));
                CommandManager.addCommand(new DriveStraight("Drive back over defense", 7.0, 123));
                break;
            case OVER_SEESAW:
                CommandManager.addCommand(new DriveStraight("Drive to seesaw", 4.0, 123));
                CommandManager.addCommand(new SetArmPosition("Lower seesaw", 2.5, Constants.TILT_DOWN_ANGLE.getDouble()));
                CommandManager.addCommand(new DriveStraight("Drive over seesaw", 5.0, 123));
                break;
            case OVER_SEESAW_COME_BACK:
                CommandManager.addCommand(new DriveStraight("Drive to seesaw", 4.0, 123));
                CommandManager.addCommand(new SetArmPosition("Lower seesaw", 2.5, Constants.TILT_DOWN_ANGLE.getDouble()));
                CommandManager.addCommand(new DriveStraight("Drive over seesaw", 5.0, 123));
                CommandGroup turnAndLiftArm = new CommandGroup("Turn and lift arm", 3.0);
                turnAndLiftArm.addCommand(new DriveTurn("Turn around", 3.0, 180.0));
                turnAndLiftArm.addCommand(new SetArmPosition("Lift Arm", 3.0, Constants.TILT_MIDDLE_ANGLE.getDouble()));
                CommandManager.addCommand(turnAndLiftArm);
                CommandManager.addCommand(new DriveStraight("Drive back to seesaw", 7.0, 123));
                CommandManager.addCommand(new SetArmPosition("Lower seesaw", 2.5, Constants.TILT_DOWN_ANGLE.getDouble()));
                CommandManager.addCommand(new DriveStraight("Drive back over seesaw", 5.0, 123));
                break;
            case DO_NOTHING:
                break;
        }
    }
    
}
