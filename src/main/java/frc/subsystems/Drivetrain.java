package frc.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class Drivetrain extends SubsystemBase {
    public SwerveWheel frontLeft, frontRight, backLeft, backRight;
    private AHRS gyro = new AHRS(SerialPort.Port.kMXP);
    public SwerveDriveKinematics kinematics;
    public SwerveDriveOdometry odometry;
    public Field2d field = new Field2d();

    public boolean fieldOriented = false;
    

    public Drivetrain() {
        //Instantiates 4 individual SwerveWheels using all ports from RobotMap
        frontLeft = new SwerveWheel(RobotMap.DRIVE_MOTOR_FRONT_LEFT, RobotMap.ROTATION_MOTOR_FRONT_LEFT, RobotMap.DRIVE_ENCODER_FRONT_LEFT);
        frontRight = new SwerveWheel(RobotMap.DRIVE_MOTOR_FRONT_RIGHT, RobotMap.ROTATION_MOTOR_FRONT_RIGHT, RobotMap.DRIVE_ENCODER_FRONT_RIGHT);
        backLeft = new SwerveWheel(RobotMap.DRIVE_MOTOR_BACK_LEFT, RobotMap.ROTATION_MOTOR_BACK_LEFT, RobotMap.DRIVE_ENCODER_BACK_LEFT);
        backRight = new SwerveWheel(RobotMap.DRIVE_MOTOR_BACK_RIGHT, RobotMap.ROTATION_MOTOR_BACK_RIGHT, RobotMap.DRIVE_ENCODER_BACK_RIGHT);

        //TODO: Update with actual wheel positions
        Translation2d frontLeftLocation = new Translation2d(0.381, 0.381);
        Translation2d frontRightLocation = new Translation2d(0.381, -0.381);
        Translation2d backLeftLocation = new Translation2d(-0.381, 0.381);
        Translation2d backRightLocation = new Translation2d(-0.381, -0.381);

        //Resets the angle of the gyroscope
        setGyro(0);

        //Instantiates the SwerveDriveKinematics object using the 4 Translation2D objects from above
        kinematics = new SwerveDriveKinematics(
            frontLeftLocation,
            frontRightLocation,
            backLeftLocation,
            backRightLocation
        );

        //Instantiates the SwerveDriveOdometry object using the kinematics and Rotation2d object
        odometry = new SwerveDriveOdometry(kinematics, new Rotation2d());
        SmartDashboard.putData("Field", field);
    }

    //Main TeleOp drive method
    //y is desired motion forward, x sideways, and rotation is desired clockwise rotation
    public void drive(double x, double y, double rotation, boolean slow) {
        double angle = gyro.getAngle() % 360;
        angle = Math.toRadians(angle);

        SmartDashboard.putNumber("Gyro angle" , angle);
        //Apply a rotation of angle radians CCW to the <x, y> vector

        if(fieldOriented) {
            final double temp = y * Math.cos(-angle) + x * Math.sin(-angle);
            x = x * Math.cos(-angle) - y * Math.sin(-angle);
            y = temp;
        }

        //Radius from center to each wheel
        double r = Math.sqrt ((RobotMap.L * RobotMap.L) + (RobotMap.W * RobotMap.W));

        //These are useful values for calculating the desired angles and speeds for each wheel
        double a = x - rotation * (RobotMap.L / r);
        double b = x + rotation * (RobotMap.L / r);
        double c = y - rotation * (RobotMap.W / r);
        double d = y + rotation * (RobotMap.W / r);

        //Range from 0-1
        double backRightSpeed = Math.sqrt ((a * a) + (c * c));
        double backLeftSpeed = Math.sqrt ((a * a) + (d * d));
        double frontRightSpeed = Math.sqrt ((b * b) + (c * c));
        double frontLeftSpeed = Math.sqrt ((b * b) + (d * d));

        //Measured in degrees
        double backRightAngle = Math.atan2 (a, c) * (180/Math.PI);
        double backLeftAngle = Math.atan2 (a, d) * (180/Math.PI);
        double frontRightAngle = Math.atan2 (b, c) * (180/Math.PI);
        double frontLeftAngle = Math.atan2 (b, d) * (180/Math.PI);

        //Sets the angle for all SwerveWheels from calculate angles above
        backRight.setAngle(backRightAngle);
        backLeft.setAngle(backLeftAngle);
        frontRight.setAngle(frontRightAngle);
        frontLeft.setAngle(frontLeftAngle);

        //Sets the speed for all SwerveWheels from calculate speeds above
        if(Robot.isShooting || slow) {
            backRight.setPower(backRightSpeed / 2);
            backLeft.setPower(backLeftSpeed / 2);
            frontRight.setPower(frontRightSpeed / 2);
            frontLeft.setPower(frontLeftSpeed / 2); 
        }
        else {
            backRight.setPower(backRightSpeed);
            backLeft.setPower(backLeftSpeed);
            frontRight.setPower(frontRightSpeed);
            frontLeft.setPower(frontLeftSpeed); 
        }

        SmartDashboard.putBoolean("driving slowed", Robot.isShooting || slow);

        SmartDashboard.putNumber("BR Power", backRightSpeed);
        SmartDashboard.putNumber("BL Power", backLeftSpeed);
        SmartDashboard.putNumber("FR Power", frontRightSpeed);
        SmartDashboard.putNumber("FL Power", frontLeftSpeed);

    }

    public void stopDrive() {
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }

    //Called periodically in autonomous to track the robot's position
    public void updateOdometry() {
        odometry.update(Rotation2d.fromDegrees(gyro.getAngle()),
            frontLeft.getState(),
            frontRight.getState(),
            backLeft.getState(),
            backRight.getState()
        );
    }

    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    public void setPose(Pose2d newPose) {
        odometry.resetPosition(newPose, gyro.getRotation2d());
    }

    //Manually set the speed of the drivetrain
    public void setChassisSpeeds(ChassisSpeeds speeds) {
        SwerveModuleState[] moduleStates = kinematics.toSwerveModuleStates(speeds);

        frontLeft.setState(moduleStates[0]);
        frontRight.setState(moduleStates[1]);
        backLeft.setState(moduleStates[2]);
        backRight.setState(moduleStates[3]);
    }

    public void resetAbsEncoders() {
        frontLeft.resetAbsEncoder();
        frontRight.resetAbsEncoder();
        backLeft.resetAbsEncoder();
        backRight.resetAbsEncoder();
    }

    public void coordinateAbsoluteEncoders() {
        frontLeft.coordinateRelativeEncoder();
        frontRight.coordinateRelativeEncoder();
        backLeft.coordinateRelativeEncoder();
        backRight.coordinateRelativeEncoder();
    }

    public void setGyro(double degrees) {
        //Gyro offeset is changed in this method +/- 90 because intake is considered front side

        gyro.reset();
        gyro.setAngleAdjustment(degrees + 90);
    }

    public void setPID(double kP, double kI, double kD) {
        frontLeft.setPID(kP, kI, kD);
        frontRight.setPID(kP, kI, kD);
        backLeft.setPID(kP, kI, kD);
        backRight.setPID(kP, kI, kD);
    }

    public void toggleFieldOriented() {
        fieldOriented = !fieldOriented;

        // When it switches to field oriented, reset what the robot thinks is forward
        if(fieldOriented) {
            setGyro(0);
        }
    }
}