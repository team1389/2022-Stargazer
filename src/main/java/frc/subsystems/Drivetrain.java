package frc.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class Drivetrain extends SubsystemBase {
    private SwerveWheel frontLeft, frontRight, backLeft, backRight;

    public Drivetrain() {
        frontLeft = new SwerveWheel(RobotMap.DRIVE_MOTOR_FRONT_LEFT, RobotMap.ROTATION_MOTOR_FRONT_LEFT, RobotMap.DRIVE_ENCODER_FRONT_LEFT);
        frontRight = new SwerveWheel(RobotMap.DRIVE_MOTOR_FRONT_RIGHT, RobotMap.ROTATION_MOTOR_FRONT_RIGHT, RobotMap.DRIVE_ENCODER_FRONT_RIGHT);
        backLeft = new SwerveWheel(RobotMap.DRIVE_MOTOR_BACK_LEFT, RobotMap.ROTATION_MOTOR_BACK_LEFT, RobotMap.DRIVE_ENCODER_BACK_LEFT);
        backRight = new SwerveWheel(RobotMap.DRIVE_MOTOR_BACK_RIGHT, RobotMap.ROTATION_MOTOR_BACK_RIGHT, RobotMap.DRIVE_ENCODER_BACK_RIGHT);
    }


}
