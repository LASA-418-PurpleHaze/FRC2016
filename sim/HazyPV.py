import numpy

class HazyPV:
	def __init__(self, HazyTMP, kP, kV, kI, kFFV, kFFA):
		self.kP = kP
		self.kV = kV
		self.kI = kI
		self.kFFV = kFFV
		self.kFFA = kFFA
		self.errorSum = 0.0

    	def calculate(self, HazyTMP, currentP, currentV):
    		voltageAdjustment = 1.0
    		output = 0.0

    		error = HazyTMP.currentPosition - currentP
		self.errorSum += error
    		output += error * self.kP

    		errorV = HazyTMP.currentVelocity - currentV
    		output += errorV * self.kV

    		output += HazyTMP.currentVelocity * self.kFFV * voltageAdjustment

    		output += (HazyTMP.currentAcceleration * self.kFFA * voltageAdjustment)

    		output += self.errorSum * self.kI
			
    		if (1.0 >= self.kI * (self.errorSum + error)) and (-1.0 <= self.kI * (self.errorSum + error)):
    			self.errorSum += error
    		elif (self.errorSum > 0):
    			self.errorSum = 1
    		elif (self.errorSum < 0):
    			self.errorSum = -1

    		if output > 1:
    			output = 1
    		elif output < -1:
    			output = -1

    		return output
