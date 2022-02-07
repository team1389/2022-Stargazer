// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;

public class OneWheelTest extends SequentialCommandGroup {
  /** Creates a new OneWheelTest. */
  public OneWheelTest() {
    addRequirements(Robot.drivetrain);
    addCommands(new InstantCommand(() -> Robot.drivetrain.frontLeft.setAngle(90)));
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
