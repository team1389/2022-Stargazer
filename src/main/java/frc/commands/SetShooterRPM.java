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
    private final double MAX_RPM = 5640;
    private double targetRPM;
    private PIDController pidController;
    private SizeLimitedQueue recentErrors;
    private Timer timer;

    /** Creates a new SetShooterRPM. */
    public SetShooterRPM(double targetRPM) {
        addRequirements();
        this.targetRPM = targetRPM;
        this.recentErrors = new SizeLimitedQueue(15);
        this.timer = new Timer();
        pidController = Robot.shooter.getFlywheelPID();
    }

    @Override
    public void initialize() {
        super.initialize();

        pidController.setSetpoint(targetRPM);
        timer.reset();
        timer.start();
    }

    @Override
    public void execute() {
        Robot.isShooting = true;
        double power = pidController.calculate(Robot.shooter.getRPM()) + (targetRPM/MAX_RPM);
        double error = targetRPM - Robot.shooter.getRPM();

        Robot.shooter.setShooterPercent(power);

        recentErrors.addElement(error);

        SmartDashboard.putNumber("power", power);
        SmartDashboard.putNumber("RPM", Robot.shooter.shooterMotor.getEncoder().getVelocity());
        SmartDashboard.putNumber("recent average", recentErrors.getAverage());

    }

    @Override
    public void end(boolean interrupted) {
        Robot.isShooting = false;
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        // return Math.abs(recentErrors.getAverage()) < 10 || timer.get() > 5;
        return false;
    }
}