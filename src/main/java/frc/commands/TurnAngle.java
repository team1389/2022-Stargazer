// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;

public class TurnAngle extends CommandBase {
  Timer timer;
  double angle, timeout;
  double targetAngle;
  PIDController pid;

  public TurnAngle(double timeout, double angle) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());

    timer = new Timer();
    this.timeout = timeout;
    this.angle = angle;
    targetAngle = Robot.drivetrain.getAngle() + angle;

    double dir = (targetAngle % 360.0);   
    //Convert from -360 to 360 to -180 to 180
    if (Math.abs(dir) > 180.0)
    {
        dir = -(Math.signum(dir) * 360.0) + dir;
    }

    targetAngle = dir;

    pid = new PIDController(0.01, 0, 0);

    addRequirements(Robot.drivetrain);
  }

  @Override
  public void initialize() {
    pid.setSetpoint(targetAngle);
    timer.reset();
    timer.start();
  }

  @Override
  public void execute() {
    double power = pid.calculate(Robot.drivetrain.getAngle());
    power = Math.max(-0.3, Math.min(0.3, power));

    Robot.drivetrain.drive(0, 0, power, 1);
  }

  @Override
  public boolean isFinished() {
    return Math.abs(Robot.drivetrain.getAngle() - targetAngle) <= 5 || timer.hasElapsed(timeout);
  }

  @Override
  public void end(boolean interrupted) {
    Robot.drivetrain.drive(0.0, 0.0, 0.0, 1);
  }
  
}
