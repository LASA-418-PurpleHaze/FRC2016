package org.lasa.lib;

public abstract class HazyCommand {

    //Pointers to the next Command above or to the left in the tree.
    HazyCommand up;
    HazyCommand left;

    String name;

    //Initialize this to something impossibly large instead of 0 so that isDone works right.
    private double startTime;
    double timeOut;

    //Booleans to keep track of what state the command is in.
    protected boolean isDone;
    protected boolean stopped;
    protected boolean shouldRun;
    protected boolean isLeftDone;

    public HazyCommand(HazyCommand up, HazyCommand left, String name, double timeOut) {
        this.up = up;
        this.left = left;
        this.name = name;
        this.timeOut = timeOut;
        this.startTime = Double.MAX_VALUE;
    }

    public HazyCommand(String nm, double t) {
        name = nm;
        timeOut = t;
    }

    public void setUp(HazyCommand p) {
        up = p;
    }

    public void setLeft(HazyCommand b) {
        left = b;
    }

    public void start() {
        startTime = System.currentTimeMillis();
        up = null;
    }

    public boolean isStarted() {
        return startTime != Double.MAX_VALUE;
    }

    public boolean isDone() {
        return isDone;
    }

    public abstract void run();

    public void stop() {
        stopped = true;
    }

    public boolean isStopped() {
        return stopped;
    }

    public boolean isLeftDone() {
        if (left == null || isLeftDone) {
            return true;
        } else {
            isLeftDone = left.isLeftDone() && left.isStopped();
            return isLeftDone;
        }
    }

    public boolean shouldRun() {
        if (shouldRun) {
            //Do this so that if we previously found out this command should run we don't do the recursion to check again unecessarily.
            return true;
        } else {
            if (up == null) {
                shouldRun = left == null || left.shouldRun();
            } else {
                shouldRun = up.stopped && up.isLeftDone();
            }
            return shouldRun;
        }
    }

    public void cancel() {
        stopped = true;
        up = null;
        left = null;
    }
}