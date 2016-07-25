package org.lasarobotics.frc2016;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.lasarobotics.frc2016.command.CommandManager;
import org.lasarobotics.frc2016.command.DriveStraight;

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
        CommandManager.addCommand(new DriveStraight("AutonDrive", 10, 5));
    }
    
}
