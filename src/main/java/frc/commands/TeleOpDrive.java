package frc.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Drivetrain;

public class TeleOpDrive extends CommandBase {
    //Add pid using gyro
    public Drivetrain drivetrain;
    public final double JOYSTICK_DEADZONE = 0.05;

    public TeleOpDrive() {
        drivetrain = Robot.drivetrain;
        addRequirements(drivetrain);

    }

    @Override
    public void execute() {
        //Gets the driver's controller inputs
        double x = Robot.oi.getDriverLeftX();
        double y = Robot.oi.getDriverLeftY();
        double rotation = Robot.oi.getDriverRightX();

        SmartDashboard.putNumber("x", x);
        SmartDashboard.putNumber("y", y);
        SmartDashboard.putNumber("rotation", rotation);

        //Sets the swerve drive command using controller inputs
        if(Math.abs(x) > JOYSTICK_DEADZONE || Math.abs(y) > JOYSTICK_DEADZONE || Math.abs(rotation) > JOYSTICK_DEADZONE) {
            Robot.drivetrain.drive(x, y, rotation);
        }
        else {
            Robot.drivetrain.stopDrive();
        }

        SmartDashboard.putNumber("FL Angle", Robot.drivetrain.frontLeft.getState().angle.getDegrees());
        SmartDashboard.putNumber("FR Angle", Robot.drivetrain.frontRight.getState().angle.getDegrees());
        SmartDashboard.putNumber("BL Angle", Robot.drivetrain.backLeft.getState().angle.getDegrees());
        SmartDashboard.putNumber("BR Angle", Robot.drivetrain.backRight.getState().angle.getDegrees());

        SmartDashboard.putNumber("FL Abs Angle", Robot.drivetrain.frontLeft.rotateAbsEncoder.getAbsolutePosition());
        SmartDashboard.putNumber("FR Abs Angle", Robot.drivetrain.frontRight.rotateAbsEncoder.getAbsolutePosition());
        SmartDashboard.putNumber("BL Abs Angle", Robot.drivetrain.backLeft.rotateAbsEncoder.getAbsolutePosition());
        SmartDashboard.putNumber("BR Abs Angle", Robot.drivetrain.backRight.rotateAbsEncoder.getAbsolutePosition());

    }

}