import numpy
import matplotlib.pyplot as plt
from matplotlib import Slider, Button, RadioButtons
from HazyPV import HazyPV
from HazyTMP import HazyTMP

class Arm:
	def __init__(self):
		# Stall Torque in N m
		self.stall_torque = 1.41
		# Stall Current in Amps
		self.stall_current = 89.0
		# Free Speed in RPM
		self.free_speed = 5840.0
		# Free Current in Amps
		self.free_current = 3.0
		# Moment of inertia of the arm
		self.mass = 15.0 * 0.454
		self.radius = 23.5 * 0.0254
		self.J = self.mass * self.radius
		# Resistance of the motor, divided by 2 to account for the 2 motors
		self.R = 12.0 / self.stall_current / 2.0
		# Motor velocity constant
		self.Kv = ((self.free_speed / 60.0 * 2.0 * numpy.pi) / (12.0 - self.R * self.free_current))
		# Torque constant
		self.Kt = self.stall_torque / self.stall_current
		# timestep
		self.dt = 0.01
		# gear ratio
		self.G = (16.0 / 54.0) * (1.0 / 90.0)

		self.A = -self.Kt / (self.Kv * self.R * self.J * self.G * self.G)
		self.B = self.Kt / (self.R * self.J * self.G)

		self.theta = 0.0
		self.w = 0.0
		self.a = 0.0

	def sim(self, input):
		u = input * 12
		#u = 12.0
		self.a = self.A * self.w + self.B * u
		self.a += (-9.81 * self.mass * self.radius * numpy.cos(self.theta)) / self.J
		self.w += self.a * self.dt
		self.theta += (self.w * self.dt + 0.5 * self.a * self.dt * self.dt)

def main():
	x = Arm()
	trap = HazyTMP(35.0, 100.0)
	controlloop = HazyPV(trap, 4.5, 0.0, 0.0, 0.0, 0)
	targetPosition = 50.0
	trap.generateTrapezoid(targetPosition, 0.0, 0.0)

	output = []
	target = []
	times = []
	t = 0.0

	for time in range(0, 500):
		output.append(x.theta * 180.0 / numpy.pi)
		trap.calculateNextSituation()
		motor = controlloop.calculate(trap, x.theta * 180.0 / numpy.pi, x.w * 180.0 / numpy.pi)
		x.sim(motor)
		times.append(t)
		target.append(trap.currentPosition)
		t += x.dt

	#plt.axis([0, 1, 0, 100])
	plt.plot(times, output)
	plt.plot(times, target)
	plt.show()


main()
