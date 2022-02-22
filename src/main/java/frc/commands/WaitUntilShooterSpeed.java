// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.util.SizeLimitedQueue;

public class WaitUntilShooterSpeed extends CommandBase {
  private double targetRPM;
  private Timer timer = new Timer();
  private SizeLimitedQueue recentErrors = new SizeLimitedQueue(15);


  /** Creates a new WaitUntilShooterSpeed. */
  public WaitUntilShooterSpeed(double targetRPM) {
      this.targetRPM = targetRPM;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
      timer.reset();
      timer.start();
  }

  @Override
  public void execute() {
      double error = targetRPM - Robot.shooter.getRPM();
      recentErrors.addElement(error);

      SmartDashboard.putNumber("recent average", recentErrors.getAverage());
  }


  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //TODO: change timeout value
    return Math.abs(recentErrors.getAverage()) < 10 || timer.get() > 15;
  }
}
