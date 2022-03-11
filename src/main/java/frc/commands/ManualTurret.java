// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.Robot;
import frc.subsystems.Shooter;

public class ManualTurret extends CommandBase {
  Shooter shooter;
  /** Creates a new ManualTurret. */
  public ManualTurret() {
    shooter = Robot.shooter;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter);
  }


  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // SmartDashboard.putNumber("Turret Position", shooter.getTurretEncoder().getPosition());
    shooter.setTurretPower(Robot.oi.getManipLeftX()/6);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
