package org.lasa.lib;

public class HazyPotentiometer {

    private double analogInputVal;
    private double maxValue, minValue;
    private double maxPosition, minPosition;

    public HazyPotentiometer(double analogInputVal) {
        this.analogInputVal = analogInputVal;
        this.maxValue = 0;
        this.minValue = 0;
        this.maxPosition = 0;
        this.minPosition = 0;
    }

    public void setPositionRange(double max, double min) {
        this.maxPosition = max;
        this.minPosition = min;
    }

    public void setValueRange(double max, double min) {
        this.maxValue = max;
        this.minValue = min;
    }

    private double get() {
        return (getRaw() - minValue) / (maxValue - minValue);
    }

    public double getPosition() {
        return get() * (maxPosition - minPosition) + minPosition;
    }

    private double getRaw() {
        return analogInputVal;
    }
}
