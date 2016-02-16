import numpy

class HazyPV:
    def __init__(self, HazyTMP, kP, kV, kFFV, kFFA):
        self.kP = kP
        self.kV = kV
        self.kFFV = kFFV
        self.kFFA = kFFA

    def calculate(self, HazyTMP, currentP, currentV):
        voltageAdjustment = 1.0
        output = 0.0

        error = HazyTMP.currentPosition - currentP
        output += error * self.kP

        errorV = HazyTMP.currentVelocity - currentV
        output += errorV * self.kV

        output += HazyTMP.currentVelocity * self.kFFV * voltageAdjustment

        output += HazyTMP.currentAcceleration * self.kFFA * voltageAdjustment

        return output

    def isDone(self, HazyTMP):
        if abs(HazyTMP.currentPosition - actualPosition) < positionDoneRange && abs(HazyTMP.currentVelocity - actualVelocity) < doneRange:
            doneCyclesCount++
        else:
            doneCyclesCount = 0

        return doneCyclesCount > minDoneCycles

    def onTrack(self, HazyTMP):
        return abs(HazyTMP.currentVelocity - actualVelocity) < doneRange
