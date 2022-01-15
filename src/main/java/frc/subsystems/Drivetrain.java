package frc.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Drivetrain extends SubsystemBase {
    private SwerveWheel frontLeft, frontRight, backLeft, backRight;

    public Drivetrain() {
        frontLeft = new SwerveWheel(RobotMap.DRIVE_MOTOR_FRONT_LEFT, RobotMap.ROTATION_MOTOR_FRONT_LEFT, RobotMap.DRIVE_ENCODER_FRONT_LEFT);
        frontRight = new SwerveWheel(RobotMap.DRIVE_MOTOR_FRONT_RIGHT, RobotMap.ROTATION_MOTOR_FRONT_RIGHT, RobotMap.DRIVE_ENCODER_FRONT_RIGHT);
        backLeft = new SwerveWheel(RobotMap.DRIVE_MOTOR_BACK_LEFT, RobotMap.ROTATION_MOTOR_BACK_LEFT, RobotMap.DRIVE_ENCODER_BACK_LEFT);
        backRight = new SwerveWheel(RobotMap.DRIVE_MOTOR_BACK_RIGHT, RobotMap.ROTATION_MOTOR_BACK_RIGHT, RobotMap.DRIVE_ENCODER_BACK_RIGHT);
    }

    //y is desired motion forward, x sideways, and rotation is desired clockwise rotation
    public void drive(double x, double y, double rotation) {
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

        backRight.setSpeed(backRightSpeed);
        backLeft.setSpeed(backLeftSpeed);
        frontRight.setSpeed(frontRightSpeed);
        frontLeft.setSpeed(frontLeftSpeed);

        backRight.setAngle(backRightAngle);
        backLeft.setAngle(backLeftAngle);
        frontRight.setAngle(frontRightAngle);
        frontLeft.setAngle(frontLeftAngle);
    }

}
