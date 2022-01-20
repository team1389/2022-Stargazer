package frc.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Drivetrain extends SubsystemBase {
    private SwerveWheel frontLeft, frontRight, backLeft, backRight;
    private AHRS gyro = new AHRS(SerialPort.Port.kMXP);
    private SwerveDriveKinematics kinematics;
    private SwerveDriveOdometry odometry;

    public Drivetrain() {
        frontLeft = new SwerveWheel(RobotMap.DRIVE_MOTOR_FRONT_LEFT, RobotMap.ROTATION_MOTOR_FRONT_LEFT, RobotMap.DRIVE_ENCODER_FRONT_LEFT);
        frontRight = new SwerveWheel(RobotMap.DRIVE_MOTOR_FRONT_RIGHT, RobotMap.ROTATION_MOTOR_FRONT_RIGHT, RobotMap.DRIVE_ENCODER_FRONT_RIGHT);
        backLeft = new SwerveWheel(RobotMap.DRIVE_MOTOR_BACK_LEFT, RobotMap.ROTATION_MOTOR_BACK_LEFT, RobotMap.DRIVE_ENCODER_BACK_LEFT);
        backRight = new SwerveWheel(RobotMap.DRIVE_MOTOR_BACK_RIGHT, RobotMap.ROTATION_MOTOR_BACK_RIGHT, RobotMap.DRIVE_ENCODER_BACK_RIGHT);

        //TODO: Update with actual wheel positions
        Translation2d frontLeftLocation = new Translation2d(0.381, 0.381);
        Translation2d frontRightLocation = new Translation2d(0.381, -0.381);
        Translation2d backLeftLocation = new Translation2d(-0.381, 0.381);
        Translation2d backRightLocation = new Translation2d(-0.381, -0.381);
 
        gyro.reset();

        kinematics = new SwerveDriveKinematics(
            frontLeftLocation,
            frontRightLocation,
            backLeftLocation,
            backRightLocation
        );

        odometry = new SwerveDriveOdometry(kinematics, new Rotation2d());
    }

    //Main TeleOp drive method
    //y is desired motion forward, x sideways, and rotation is desired clockwise rotation
    public void drive(double x, double y, double rotation) {
        double angle = gyro.getAngle() % 360;
        angle = Math.toRadians(angle);

        //Apply a rotation of angle radians CCW to the <x, y> vector
        final double temp = y * Math.cos(angle) + x * Math.sin(angle);
        x = x * Math.cos(angle) - y * Math.sin(angle);
        y = temp;

        //Radius from center to each wheel
        double r = Math.sqrt ((RobotMap.L * RobotMap.L) + (RobotMap.W * RobotMap.W));

        //These are useful values for calculating the desired angles and speeds for each wheel
        double a = x - rotation * (RobotMap.L / r);
        double b = x + rotation * (RobotMap.L / r);
        double c = y - rotation * (RobotMap.W / r);
        double d = y + rotation * (RobotMap.W / r);

        //Range from 0-1
        double backRightSpeed = Math.sqrt ((a * a) + (d * d));
        double backLeftSpeed = Math.sqrt ((a * a) + (c * c));
        double frontRightSpeed = Math.sqrt ((b * b) + (d * d));
        double frontLeftSpeed = Math.sqrt ((b * b) + (c * c));

        //Measured in degrees
        double backRightAngle = Math.atan2 (a, d) * (180/Math.PI);
        double backLeftAngle = Math.atan2 (a, c) * (180/Math.PI);
        double frontRightAngle = Math.atan2 (b, d) * (180/Math.PI);
        double frontLeftAngle = Math.atan2 (b, c) * (180/Math.PI);

        backRight.setAngle(backRightAngle);
        backLeft.setAngle(backLeftAngle);
        frontRight.setAngle(frontRightAngle);
        frontLeft.setAngle(frontLeftAngle);

        backRight.setSpeed(backRightSpeed);
        backLeft.setSpeed(backLeftSpeed);
        frontRight.setSpeed(frontRightSpeed);
        frontLeft.setSpeed(frontLeftSpeed);      
    }

    //Called periodically in autonomous to track the robot's position
    public void updateOdometry() {
        odometry.update(Rotation2d.fromDegrees(-gyro.getAngle()),
            frontLeft.getState(),
            frontRight.getState(),
            backLeft.getState(),
            backRight.getState()
        );
    }

}
