// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.autos;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;

public class DriveTime extends CommandBase {
  /** Creates a new ManualAuto. */
  Timer timer;
  double timeout;

  public DriveTime(double timeout) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());

    timer = new Timer();
    this.timeout = timeout;

    addRequirements(Robot.drivetrain);
  }

  @Override
  public void initialize() {
    timer.reset();
    timer.start();
  }

  @Override
  public void execute() {
    Robot.drivetrain.drive(0, 0.3, 0, 1);
  }

  @Override
  public boolean isFinished() {
    return timer.hasElapsed(timeout);
  }

  @Override
  public void end(boolean interrupted) {
    Robot.drivetrain.drive(0.0, 0.0, 0.0, 1);
  }
  
}
