import numpy

class HazyTMP:

    def __init__(self, maxVelocity, maxAccel):
        self.maxV = maxVelocity
        self.maxA = maxAccel
        self.dt = 0.001

        self.acceleration = 0.0
        self.accelerationTime = 0.0
        self.topSpeed = 0.0
        self.cruiseTime = 0.0
        self.deceleration = 0.0
        self.decelerationTime = 0.0
        self.currentPosition = 0.0
        self.currentVelocity = 0.0
        self.currentAcceleration = 0.0

    def generateTrapezoid(self, targetPosition, realPosition, realSpeed):
        self.positionError = targetPosition - realPosition

        if abs(positionError) < 0.01:
            self.currentPosition = realPosition
            self.currentVelocity = 0.0
            self.currentAcceleration = 0.0
            return

        maximumPossibleSpeed = math.sqrt((2 * self.maxA * positionError + realSpeed ** 2)/2)

        self.topSpeed = min(maximumPossibleSpeed, self.maxV)

        self.acceleration = self.maxA
        self.accelerationTime = max(((self.topSpeed ** 2 - realSpeed ** 2) / (2 * self.acceleration)), 0.0)

        accelerationDistance = max(((self.topSpeed ** 2 - realSpeed **2) / (2 * self.acceleration)), 0.0)

        self.deceleration =  -1 * acceleration
        self.decelerationTime = (0 - self.topSpeed) / self.deceleration

        decelerationDistance = -1 * (self.topSpeed ** 2) / (2 * deceleration)

        cruiseDistance = self.positionError  - accelerationDistance - decelerationDistance

        if(topSpeed != 0):
            cruiseTime = cruiseDistance / selftopSpeed
        else:
            cruiseTime = 0.0

        self.currentPosition = realPosition
        self.currentVelocity = realSpeed

    def calculateNextSituation(self):
        if self.accelerationTime > self.dt:
            accelerate(self.dt)
            self.accelerationTime -= dt
        elif (self.accelerationTime + self.cruiseTime) > self.dt:
            accelerate(self.accelerationTime)
            cruise(dt - self.accelerationTime)

            cruiseTime -= (self.dt - self.accelereationTime)
            self.accelerationTime = 0.0
        elif (self.accelereationTime + self.cruiseTime + self.decelerationTime) > self.dt:
            accelerate(self.accelerationTime)
            cruise(self.cruiseTime)
            deceleratie(self.dt - self.accelerationTime - self.cruiseTime)

            self.decelerationTime -= (self.dt - self.accelerationTime - self.cruiseTime)
            self.accelerationTime = 0.0
            self.cruiseTime = 0.0
        else:
            accelerate(self.accelereationTime)
            cruise(self.cruiseTime)
            decelerate(self.decelerationTime)

            self.accelerationTime = 0.0
            self.cruiseTime = 0.0
            self.decelerationTime = 0.0
            self.currentAcceleration = 0.0

    def accelerate(self):
        self.currentAcceleration = self.acceleration
        self.currentPosition += (self.currentVelocity * self.dt + .5 * self.currentAcceleration * dt ** 2)
        self.currentVelocity += (self.currentAcceleration * self.dt)

        if self.currentVelocity > self.topSpeed:
            self.currentVelocity = self.topSpeed
        elif self.currentVelocity < -self.topSpeed:
            self.currentVelocity = -self.topSpeed

    def cruise(self):
        self.currentAcceleration = 0.0
        self.currentPosition += self.currentVelocity * self.dt

    def decelerate(self):
        self.currentAcceleration = self.deceleration
        self.currentPosition += (self.currentVelocity * self.dt + .5 * self.deceleration * dt ** 2)
        self.currentVelocity += (self.currentAcceleration * self.dt)

        if self.currentVelocity > self.topSpeed:
            self.currentVelocity = self.topSpeed
        elif self.currentVelocity < -self.topSpeed:
            self.currentVelocity = -self.topSpeed
