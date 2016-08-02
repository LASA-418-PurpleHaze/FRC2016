package org.lasarobotics.lib.controlloop;

public class HazyPID extends ControlLoop {

    double kP, kI, kD, kFF;
    double previousValue, output;
    double error, errorSum, doneBound;
    double maxU, minU;
    boolean firstCycle;
    int count, minCount;

    public HazyPID() {
        this.maxU = 1;
        this.minU = -1;
        this.minCount = 50;
        this.firstCycle = true;
    }

    public boolean onTarget() {
        count = (Math.abs(setPoint - previousValue) < doneBound) ? ++count : 0;
        return count >= minCount;
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
        error = setPoint - currentValue;
        if (!firstCycle) {
            output = kP * error + kI * errorSum - kD * (currentValue - previousValue) + kFF * setPoint;
            if (maxU >= kI * (errorSum + error) && minU <= kI * (errorSum + error)) {
                errorSum += error;
            } else if (errorSum > 0) {
                errorSum = maxU;
            } else {
                errorSum = minU;
            }
        } else {
            firstCycle = false;
            output = kP * error + kFF * setPoint;
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
