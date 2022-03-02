// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;

public class TestSystem extends SequentialCommandGroup {
  /** Creates a new TestSystem. */
  public TestSystem() {
    addRequirements(Robot.shooter);
  }

  @Override
  public void execute() {
    Robot.shooter.setTurretPower(0.2);
    //Robot.shooter.runIndexer(0.5);
    //Robot.intake.setIntakePercent(0.5);

    SmartDashboard.putNumber("Turret position", Robot.shooter.turretEncoder.getPosition());
  }


  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
   // Robot.shooter.setTurretPower(0);
  }
}
