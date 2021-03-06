package org.lasarobotics.frc2016.command;

import edu.wpi.first.wpilibj.Timer;
import java.util.Iterator;
import java.util.LinkedList;

public class CommandGroup extends Command {
    
    private LinkedList<Command> commands = new LinkedList<>();

    public CommandGroup(String name, double timeOut) {
        super(name, timeOut);
    }
    
    public void addCommand(Command c) {
        commands.add(c);
    }
    
    public void start() {
        startTime = Timer.getFPGATimestamp();
        for (Command c: commands) {
            c.startTime = Timer.getFPGATimestamp();
            c.start();
        }
    }
    
    public void run() {
        Iterator it = commands.iterator();
        while (it.hasNext()) {
            Command temp = (Command) it.next();
            if (temp.isDone()) {
                temp.stop();
                it.remove();
            } else {
                temp.run();
            }
        }
    }

    @Override
    public boolean isDone() {
        boolean done = true;
        for (Command c: commands) {
            done &= c.isDone();
        }
        
        return done;
    }

    @Override
    public void stop() {
    }
}
