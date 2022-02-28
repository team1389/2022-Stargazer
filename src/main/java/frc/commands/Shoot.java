// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class Shoot extends SequentialCommandGroup {
  // This is the command that should be run to shoot
  public Shoot() {
    // First get the distance to target, and then actually shoot 
    addCommands(new GetDistanceToTarget(), new ShootWithSensors());
  }
}
