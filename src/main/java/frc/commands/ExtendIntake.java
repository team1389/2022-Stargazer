// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Intake;

public class ExtendIntake extends CommandBase {
  private Intake intake;

  public ExtendIntake() {
        intake = Robot.intake;
        addRequirements(intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    intake.extendIntake();
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
