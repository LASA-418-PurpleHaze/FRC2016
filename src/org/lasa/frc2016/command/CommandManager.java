package org.lasa.frc2016.command;

import java.util.ArrayList;
import java.util.Iterator;

public class CommandManager {
    
    private static ArrayList<Command> list = new ArrayList<>();
    private static Command lastAdded;
    
    public static void addCommand(Command c) {
        list.add(c);
    }
    
    public static void addSequential(Command c) {
        c.setUp(lastAdded);
        c.setLeft(null);
        lastAdded = c;
        list.add(c);
    }
    
    public static void addParallel(Command c) {
        c.setLeft(lastAdded);
        c.setUp(null);
        lastAdded = c;
        list.add(c);
    }

    public static void run() {
        //Iterator because it is the only way to remove items from a collection while traversing it.
            //Going through all commands every cycle isn't great but should be ok since we won't have very many commands.
            for (Iterator<Command> it = list.iterator(); it.hasNext();) {
                Command c = it.next();
                
                //This part should look kinda familiar.
                if (c.isDone()) {
                    c.stop();
                    it.remove();
                }
                
                if (c.shouldRun()) {
                    if (!c.isStarted()) {
                        c.start();
                    }
                    c.run();
                }
            }
    }
    
    public static boolean empty() {
        return list.isEmpty();
    }
    
    public static void cancelAll() {
        list.clear();
    }
}
