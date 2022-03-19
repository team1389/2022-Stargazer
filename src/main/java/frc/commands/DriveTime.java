// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;

public class DriveTime extends CommandBase {
  /** Creates a new ManualAuto. */
  Timer timer;
  double x, y, rotation, timeout, initialAngle;
  PIDController pid;

  public DriveTime(double timeout, double x, double y, double rotation) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());

    timer = new Timer();
    this.timeout = timeout;
    this.x = x;
    this.y = y;
    this.rotation = rotation;

    pid = new PIDController(0.01, 0, 0);

    addRequirements(Robot.drivetrain);
  }

  @Override
  public void initialize() {
    timer.reset();
    timer.start();
    
    initialAngle = Robot.drivetrain.getAngle();
    pid.setSetpoint(initialAngle);
  }

  @Override
  public void execute() {
    double rotationCorrection = pid.calculate(Robot.drivetrain.getAngle());
    rotationCorrection = Math.max(-0.3, Math.min(0.3, rotationCorrection));

    Robot.drivetrain.drive(x, y, rotation + rotationCorrection, 1);
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
