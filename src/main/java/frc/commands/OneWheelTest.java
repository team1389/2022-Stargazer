// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;

public class OneWheelTest extends SequentialCommandGroup {
  public OneWheelTest() {
    addRequirements(Robot.drivetrain);

    SmartDashboard.putNumber("kP", 0.025);
    SmartDashboard.putNumber("kI", 0);
    SmartDashboard.putNumber("kD", 0);

  }

  @Override
  public void initialize() {
    // Robot.swerveWheel.resetAbsEncoder();
    // Robot.swerveWheel.coordinateRelativeEncoder();

    Robot.drivetrain.setPID(
        SmartDashboard.getNumber("kP", 0.025),
        SmartDashboard.getNumber("kI", 0),
        SmartDashboard.getNumber("kD", 0));
  }

  @Override
  public void execute() {
    /*Robot.drivetrain.frontLeft.setAngle(90);
    Robot.drivetrain.frontRight.setAngle(90);
    Robot.drivetrain.backLeft.setAngle(90);
    Robot.drivetrain.backRight.setAngle(90);*/

    // Robot.drivetrain.frontLeft.setSpeed(15);
    // Robot.drivetrain.frontLeft.setPower(0.2);

    SmartDashboard.putNumber("Angle", Robot.drivetrain.frontLeft.getState().angle.getDegrees());
    SmartDashboard.putNumber("Speed (m/s)", Robot.drivetrain.frontLeft.getState().speedMetersPerSecond);
  }
}