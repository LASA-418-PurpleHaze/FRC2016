package org.lasa.frc2016.command;

import edu.wpi.first.wpilibj.DriverStation;

public class ArcadeDrive extends HazyCommand {

    public ArcadeDrive(String nm, double t) {
        super(nm, t);
    }

    @Override
    public void run() {
        double throttle = driverInput.getThrottle();
        double wheel = driverInput.getWheel();
        
        drivetrain.setDriveSpeeds(throttle + wheel, throttle - wheel);
    }

    @Override
    public void stop() {
        drivetrain.setDriveSpeeds(0.0, 0.0);
        super.stop();
    }
    
    @Override
    public boolean isDone() {
        return DriverStation.getInstance().isDisabled();
    }
}
