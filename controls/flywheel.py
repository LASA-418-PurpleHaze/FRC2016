import numpy
import matplotlib.pyplot as plt

class Flywheel:
	def __init__(self):
		# Stall Torque in N m
		self.stall_torque = .71
		# Stall Current in Amps
		self.stall_current = 135
		# Free Speed in RPM
		self.free_speed = 18730.0
		# Free Current in Amps
		self.free_current = .7
		# Moment of inertia of the arm in kg m^2
		self.J = .001
		# Resistance of the motor, divided by 2 to account for the 2 motors
		self.R = (12.0 / self.stall_current)/2
		# Motor velocity constant
		self.Kv = ((self.free_speed / 2.0 * numpy.pi) / (12.0 - self.R * self.free_current))
		# Torque constant
		self.Kt = self.stall_torque / self.stall_current
		# timesteop
		self.dt = 0.01
		# gear ratio
		self.G = 12.0 / 15.0
		
		self.A = -self.Kt / (self.Kv * self.R * self.J * self.G * self.G)
		self.B = self.Kt / (self.R * self.J * self.G)
		
		self.theta = 0.0
		self.w = 0.0
		self.a = 0.0
	
	def sim(self):
		self.a = self.A * self.w + self.B * 12.0
		self.w += self.a * self.dt
		self.theta += self.w * self.dt + 0.5 * self.a * self.dt * self.dt
		
x = Flywheel()

angles = []
times = []
t = 0.0

for time in range(0, 500):
	x.sim()
	angles.append(x.a * 180.0 / numpy.pi * 60)
	times.append(t)
	t += x.dt
	
plt.plot(times, angles)
plt.show()
