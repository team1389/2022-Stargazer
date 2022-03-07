package frc.util;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import frc.subsystems.SwerveWheel;

public class SwerveTelemetry implements Sendable {
    SwerveWheel swerveWheel;

    public SwerveTelemetry(SwerveWheel wheel) {
        swerveWheel = wheel;
        SendableRegistry.addChild(this, swerveWheel.driveMotor);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
      builder.setSmartDashboardType("SwerveTelemetry");
      builder.addDoubleProperty("Speed", () -> getSpeed(), null);
      builder.addDoubleProperty("Angle", () -> getAngle(), null);
      builder.addDoubleProperty("Absolute Angle", () -> getAngle(), null);
      builder.addDoubleProperty("Target Angle", () -> getTargetAngle(), null);
      builder.addBooleanProperty("Inverted", () -> getInverted(), null);
    }


    public double getSpeed() {
        return swerveWheel.getState().speedMetersPerSecond;
    }
    public double getAngle() {
        return swerveWheel.getState().angle.getDegrees();
    }
    public double getAbsAngle() {
        return swerveWheel.rotateAbsEncoder.getAbsolutePosition();
    }

    public double getTargetAngle() {
        return swerveWheel.getTargetAngle();
    }
    public boolean getInverted() {
        //return swerveWheel.isInverted();
        return false;
    }

  }
