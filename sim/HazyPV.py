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

    		if output > 1:
    			output = 1
    		elif output < -1:
    			output = -1

    		return output
