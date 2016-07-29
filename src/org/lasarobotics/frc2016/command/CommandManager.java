package org.lasarobotics.frc2016.command;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.LinkedList;

public class CommandManager {

    private static LinkedList<Command> list = new LinkedList<>();

    public static void addCommand(Command c) {
        list.add(c);
    }

    public static void run() {
        if (!list.isEmpty()) {
            Command command = list.getFirst();

            if (!command.isStarted()) {
                command.startTime = Timer.getFPGATimestamp();
                command.start();
                command.run();
            } else if (command.isDone() || command.isTimedOut()) {
                command.stop();
                list.remove(command);
            } else {
                SmartDashboard.putString("command", command.name);
                command.run();
            }
        }
    }

    public static void cancelAll() {
        list.clear();
    }
}
