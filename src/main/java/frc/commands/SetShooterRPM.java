// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class SetShooterRPM extends CommandBase {
    private double targetRPM;
    private PIDController pidController;
    private final double MAX_RPM = 5640;

    /** Creates a new WaitUntilShooterSpeed. */
    public SetShooterRPM(double targetRPM) {
        addRequirements(Robot.shooter);
        this.targetRPM = targetRPM;

        pidController = Robot.shooter.getFlywheelPID();
    }

    @Override
    public void initialize() {
        super.initialize();

        pidController.setSetpoint(targetRPM);
    }

    @Override
    public void execute() {
        double power = pidController.calculate(Robot.shooter.getRPM()) + (targetRPM/MAX_RPM);

        Robot.shooter.setShooterPercent(power);

        SmartDashboard.putNumber("power", power);
        SmartDashboard.putNumber("RPM", Robot.shooter.shooterMotor.getEncoder().getVelocity());

    }

    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
