// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.SwerveWheel;

public class TestAngles extends CommandBase {
  private double kP, kI, kD;
  private SwerveWheel wheel;
  private double[] initialPID;
  
  public TestAngles() {
    wheel = Robot.drivetrain.frontRight;
    initialPID = wheel.getPID();

    // SmartDashboard.putNumber("kP", initialPID[0]);
    // SmartDashboard.putNumber("kI", initialPID[1]);
    // SmartDashboard.putNumber("kD", initialPID[2]);

    addRequirements(Robot.drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    super.initialize();


  }
  @Override
  public void execute() {
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
