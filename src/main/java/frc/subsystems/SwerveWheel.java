package frc.subsystems;

import com.ctre.phoenix.sensors.CANCoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveWheel extends SubsystemBase {
    public CANSparkMax rotateMotor, driveMotor;
    private SparkMaxPIDController rotatePIDController, drivePIDController;

    // Doesn't reset between matches, unlike the built in relative encoders
    public CANCoder rotateAbsEncoder;
    public RelativeEncoder rotateRelativeEncoder;
    public RelativeEncoder driveRelativeEncoder;

    // Multiplied by the native output units (-1 to 1) to find position
    private final double ROTATION_POSITION_CONVERSION_FACTOR = 1 / (5.33 * 7);;

    // Factor between RPM and m/s
    // TODO: Figure out what this value is
    private final double DRIVE_VELOCITY_CONVERSION_FACTOR = (5.25 * Math.PI * 3 * 0.0254) / 60;

    // Create PID coefficients
    public double rotateP = 0.005; // 0.025
    public double rotateI = 0.0000;
    public double rotateD = 0;

    public double driveP = 0.0007667;
    public double driveI = 0.0000005;
    public double driveD = 0;

    public SwerveWheel(int driveMotorPort, int rotateMotorPort, int rotateEncoderPort) {
        // Instantiate motors/sensors
        driveMotor = new CANSparkMax(driveMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
        rotateMotor = new CANSparkMax(rotateMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
        rotateAbsEncoder = new CANCoder(rotateEncoderPort);

        driveMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        rotateMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);

        driveMotor.setInverted(false);

        driveRelativeEncoder = driveMotor.getEncoder();
        rotateRelativeEncoder = rotateMotor.getEncoder();

        rotateAbsEncoder.setPositionToAbsolute();
        rotateRelativeEncoder.setPositionConversionFactor(ROTATION_POSITION_CONVERSION_FACTOR * 180 / Math.PI);

        // Assign PID controllers' parameters
        rotatePIDController = rotateMotor.getPIDController();

        // Set the kP, kI, and kD values for the rotatePIDController
        rotatePIDController.setP(rotateP);
        rotatePIDController.setI(rotateI);
        rotatePIDController.setD(rotateD);

        rotatePIDController.setSmartMotionAccelStrategy(SparkMaxPIDController.AccelStrategy.kTrapezoidal, 0);

        drivePIDController = driveMotor.getPIDController();

        // Set the kP, kI, and kD values for the rotatePIDController
        drivePIDController.setP(driveP);
        drivePIDController.setI(driveI);
        drivePIDController.setD(driveD);

    }

    public void setPower(double power) {
        driveMotor.set(power);
    }

    public void setSpeed(double speedMetersPerSecond) {
        drivePIDController.setReference(speedMetersPerSecond / DRIVE_VELOCITY_CONVERSION_FACTOR,
                CANSparkMax.ControlType.kVelocity);
    }

    // Angle should be measured in degrees, from -180 to 180
    public double gotoAngle(double angle) {
        double currentAngle = rotateMotor.getEncoder().getPosition();
        double setpointAngle = closestAngle(currentAngle, angle);
        double setpointAngleFlipped = closestAngle(currentAngle, angle + 180.0);

        // rotatePIDController.setReference(currentAngle + setpointAngle,
        // CANSparkMax.ControlType.kPosition, 0);

        // If the closest angle to setpoint is shorter
        if (Math.abs(setpointAngle) <= Math.abs(setpointAngleFlipped)) {
            driveMotor.setInverted(false);
            rotatePIDController.setReference(currentAngle + setpointAngle, CANSparkMax.ControlType.kPosition, 0);
        }

        // If the closest angle to setpoint + 180 is shorter
        else {
            driveMotor.setInverted(true);
            rotatePIDController.setReference(currentAngle + setpointAngleFlipped, CANSparkMax.ControlType.kPosition, 0);
        }

        return currentAngle + setpointAngle;
    }

    // Set the set of the wheel with a SwerveModuleState
    public void setState(SwerveModuleState state) {
        // Reverse the angle to match default from rotation encoders
        gotoAngle(-state.angle.getDegrees());

        setSpeed(state.speedMetersPerSecond);
    }

    // Set the relative encoder to its wheel's actual angle
    public void coordinateRelativeEncoder() {
        // rotateAbsEncoder.setPositionToAbsolute();

        double absAngle = rotateAbsEncoder.getAbsolutePosition();
        if (absAngle <= 180) {
            rotateRelativeEncoder.setPosition(absAngle);
        } else {
            rotateRelativeEncoder.setPosition(-360 + absAngle);
        }

    }

    // Get the closest angle between the given angles.
    private static double closestAngle(double a, double b) {
        // Get direction
        double dir = (b % 360.0) - (a % 360.0);

        // Convert from -360 to 360 to -180 to 180
        if (Math.abs(dir) > 180.0) {
            dir = -(Math.signum(dir) * 360.0) + dir;
        }
        return dir;
    }

    // Only run this when training the angle, never in matches
    public void resetAbsEncoder() {
        rotateAbsEncoder.setPosition(0);
        rotateMotor.getEncoder().setPosition(0);
    }

    public SwerveModuleState getState() {
        // Return the current module position and speed
        return new SwerveModuleState(driveRelativeEncoder.getVelocity() * DRIVE_VELOCITY_CONVERSION_FACTOR,
                Rotation2d.fromDegrees(-driveRelativeEncoder.getPosition()));
    }

    public void setPID(double kP, double kI, double kD) {
        rotatePIDController.setP(kP);
        rotatePIDController.setI(kI);
        rotatePIDController.setD(kD);
    }

}
