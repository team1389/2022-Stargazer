// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.util.SizeLimitedQueue;

public class SetShooterRPM extends CommandBase {
    private SizeLimitedQueue recentErrors;
    private Timer timer;

    /** Creates a new SetShooterRPM. */
    public SetShooterRPM() {
        addRequirements();
        this.recentErrors = new SizeLimitedQueue(15);
        this.timer = new Timer();
    }

    @Override
    public void initialize() {
        super.initialize();
        Robot.shooter.setFlywheelSpeed(Robot.shooter.targetRPM);
        timer.reset();
        timer.start();
    }

    @Override
    public void execute() {
        Robot.isShooting = true;
        Robot.shooter.targetRPM = SmartDashboard.getNumber("TargetRPM", Robot.shooter.targetRPM);
        // pidController.setSetpoint(targetRPM);

        // double power = pidController.calculate(Robot.shooter.getRPM()) + (Robot.shooter.targetRPM/MAX_RPM);
        double error = Robot.shooter.targetRPM - Robot.shooter.getRPM();

        Robot.shooter.setFlywheelSpeed(Robot.shooter.targetRPM);
        recentErrors.addElement(error);

        // targetRPM = 5640;

        // SmartDashboard.putNumber("power", Robot.shooter.getRPM());
        SmartDashboard.putNumber("RPM", Robot.shooter.getRPM());
        SmartDashboard.putNumber("recent average", recentErrors.getAverage());
        SmartDashboard.putNumber("current error", error);
        SmartDashboard.putNumber("indexer RPM", Robot.shooter.indexerMotor.getEncoder().getVelocity());

    }

    @Override
    public void end(boolean interrupted) {
        Robot.shooter.setFlywheelSpeed(0);
        Robot.isShooting = false;
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        // return Math.abs(recentErrors.getAverage()) < 10 || timer.get() > 5;
        return false;
    }
}