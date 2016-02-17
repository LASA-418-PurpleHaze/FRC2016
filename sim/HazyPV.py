import numpy

class HazyPV:
	def __init__(self, HazyTMP, kP, kV, kFFV, kFFA, kI):
		self.kP = kP
		self.kV = kV
		self.kFFV = kFFV
		self.kFFA = kFFA
		self.kI = kI

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
    		if (12 >= self.kI * (errorSum + error)) and (12 <= self.kI * (errorSum + error)):
    			errorSum += error
    		elif (errorSum > 0):
    			errorSum = 12
    		elif (errorSum < 0):
    			errorSum = -12

    		if output > 12:
    			output = 12
    		elif output < -12:
    			output = -12

    		return output
