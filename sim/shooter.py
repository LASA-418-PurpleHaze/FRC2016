import numpy
import matplotlib.pyplot as plt

class Shooter:
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
		self.G = 12.0 / 18.0
		
		self.A = -self.Kt / (self.Kv * self.R * self.J * self.G * self.G)
		self.B = self.Kt / (self.R * self.J * self.G)
		
		self.theta = 0.0
		self.w = 0.0
		self.a = 0.0
	
	def sim(self):
		rpm = self.w * 2.0 * 3.14159 / 60.0
		volts = 12
		if (volts > 12.0):
			volts = 12.0
		if (volts < -12.0):
			volts = -12.0
		volts = 12
		self.a = self.A * self.w + self.B * volts
		self.w += self.a * self.dt
		self.theta += self.w * self.dt + 0.5 * self.a * self.dt * self.dt
		
x = Shooter()

angles = []
times = []
t = 0.0

for time in range(0, 1000):
	angles.append(x.w * 2.0 * 3.14159 / 60.0)
	x.sim()
	times.append(t)
	t += x.dt
	
plt.plot(times, angles)
plt.axis([0, 10, 0, 7000])
plt.show()
