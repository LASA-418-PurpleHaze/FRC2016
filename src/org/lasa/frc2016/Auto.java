package org.lasa.frc2016;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.lasa.frc2016.command.AutoPrepShooter;
import org.lasa.frc2016.command.CommandManager;
import org.lasa.frc2016.command.DriveStraight;
import org.lasa.frc2016.command.DriveTurn;
import org.lasa.frc2016.command.SetArmPosition;
import org.lasa.frc2016.command.Shoot;

public class Auto implements Runnable {
    
    private static Auto instance;
    
    private final byte AUTONOMOUS_KEY;
    
    private boolean ROBOT_SLEEP;
    
    private Auto() {
        AUTONOMOUS_KEY = (byte) SmartDashboard.getNumber("Auto Key");
    }
    
    public static Auto getInstance() {
        return (instance == null) ? instance = new Auto() : instance;
    }
    
    @Override
    public void run() {
        
        switch(AUTONOMOUS_KEY) {
            case 0:
                CommandManager.addCommand(new DriveStraight("DriveOverDefense", 10, 24));
                break;
            case 1:
                CommandManager.addCommand(new SetArmPosition("PrepPortcullis", 10, 15, 0));
                CommandManager.addSequential(new SetArmPosition("Portcullis", 10, 15, 16));
                CommandManager.addSequential(new DriveStraight("DriveOverDefense", 10, 24));
                break;
            case 2:
                CommandManager.addCommand(new SetArmPosition("PrepSallyPort", 10, 15, 24));
                CommandManager.addSequential(new SetArmPosition("CatchSally", 10, 15, 23));
                CommandManager.addSequential(new DriveTurn("AdjustBot", 10, -48.59));
                CommandManager.addSequential(new DriveStraight("PullBack", 10, -63.5));
                CommandManager.addSequential(new DriveTurn("ReadjustBot", 10, 48.59));
                CommandManager.addParallel(new DriveStraight("DontHitSallyYouJankPieceOfCrapRobot/DriveOverDefense", 10, 48));
                break;
            case 3:
                CommandManager.addCommand(new SetArmPosition("PrepDrawBridge", 10, 15, 29));
                CommandManager.addSequential(new SetArmPosition("DrawBridge", 10, 15, 0));
                CommandManager.addSequential(new DriveStraight("DriveOverDefense", 10, 36));
                break;
            case 4:
                CommandManager.addCommand(new SetArmPosition("PrepSeeSaw", 10, 15, 1));
                CommandManager.addSequential(new SetArmPosition("SeeSaw", 10, 15, 0));
                CommandManager.addSequential(new DriveStraight("DriveOverDefense", 10, 24));
                break;
            default:
                ROBOT_SLEEP = true;
                break;
        }
        
        if(CommandManager.empty() && !ROBOT_SLEEP) {
            CommandManager.addCommand(new AutoPrepShooter("PrepShooter", 10));
            CommandManager.addSequential(new Shoot("Shoot", 10));
        }
        
        
    }
}
