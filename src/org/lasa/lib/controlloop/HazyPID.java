package org.lasa.lib.controlloop;

public class HazyPID extends ControlLoop{

    double kP, kI, kD, kFF;
    double targetValue, previousValue, output;
    double error, errorSum, doneBound;
    double maxU, minU;
    boolean firstCycle;
    int count, minCount;

    public HazyPID() {
        this.maxU = 1;
        this.minU = -1;
        this.minCount = 5;
        this.firstCycle = true;
    }

    public boolean onTarget() {
        count = (Math.abs(targetValue - previousValue) < doneBound) ? ++count : 0;
        return count >= minCount;
    }

    public void setTarget(double val) {
        targetValue = val;
    }

    public double getTargetVal() {
        return targetValue;
    }

    public void updatePID(double kP, double kI, double kD, double kFF, double doneBound) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kFF = kFF;
        this.doneBound = doneBound;
    }

    public void updateMaxMin(double maxU, double minU) {
        this.maxU = maxU;
        this.minU = minU;
    }

    public void setMinCount(int val) {
        minCount = val;
    }

    public double calculate(double currentValue) {
        error = targetValue - currentValue;
        if (!firstCycle) {
            output = kP * error + kI * errorSum - kD * (currentValue - previousValue) + kFF * targetValue;
            if (maxU >= kI * (errorSum + error) && minU <= kI * (errorSum + error)) {
                errorSum += error;
            } else if (errorSum > 0) {
                errorSum = maxU;
            } else {
                errorSum = minU;
            }
        } else {
            firstCycle = false;
            output = kP * error + kFF * targetValue;
        }

        previousValue = currentValue;

        if (output > maxU) {
            output = maxU;
        } else if (output < minU) {
            output = minU;
        }
        return output;
    }

    public void reset() {
        error = 0;
        errorSum = 0;
        previousValue = 0;
        firstCycle = true;
    }
}
