// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Robot;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class TestAngles extends SequentialCommandGroup {
  /** Creates a new TestAngles. */
  public TestAngles() {
    Robot.swerveWheel.setPower(0.2);
    // Add your commands in the addCommands() call, e.g.
    addRequirements(Robot.swerveWheel);
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new InstantCommand(() -> Robot.swerveWheel.setAngle(90)),
      new WaitCommand(2),
      new InstantCommand(() -> Robot.swerveWheel.setAngle(180)),
      new WaitCommand(2),
      new InstantCommand(() -> Robot.swerveWheel.setAngle(90))
    );

  }
}
