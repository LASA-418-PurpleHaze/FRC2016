import numpy

class HazyPV:
	def __init__(self, HazyTMP, kP, kV, kI, kFFV, kFFA):
		self.kP = kP
		self.kV = kV
		self.kI = kI
		self.kFFV = kFFV
		self.kFFA = kFFA

    	def calculate(self, HazyTMP, currentP, currentV):
    		voltageAdjustment = 1.0
    		output = 0.0
    		errorSum = 0.0

    		error = HazyTMP.currentPosition - currentP
    		output += error * self.kP

    		errorV = HazyTMP.currentVelocity - currentV
    		output += errorV * self.kV

    		output += HazyTMP.currentVelocity * self.kFFV * voltageAdjustment

    		output += HazyTMP.currentAcceleration * self.kFFA * voltageAdjustment

    		output += errorSum * self.kI
    		if (1 >= self.kI * (errorSum + error)) and (-1 <= self.kI * (errorSum + error)):
    			errorSum += error
    		elif (errorSum > 0):
    			errorSum = 1
    		elif (errorSum < 0):
    			errorSum = -1

    		if output > 1:
    			output = 1
    		elif output < -1:
    			output = -1

    		return output
