package frc.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Drivetrain;

public class TeleOpDrive extends CommandBase {
    //Add pid using gyro
    public Drivetrain drivetrain;

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

        //Sets the swerve drive command using controller inputs
        Robot.drivetrain.drive(x, y, rotation);

        SmartDashboard.putNumber("Angle", Robot.drivetrain.frontLeft.getState().angle.getDegrees());
        SmartDashboard.putBoolean("IsInverted", Robot.drivetrain.frontLeft.driveMotor.getInverted());
        SmartDashboard.putNumber("Speed (m/s)", Robot.drivetrain.frontLeft.getState().speedMetersPerSecond);
    }

}