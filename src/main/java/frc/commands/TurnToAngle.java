// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;
import frc.util.Angle;

public class TurnToAngle extends CommandBase {
    Timer timer;
    double timeout;
    Angle angle;
    Angle targetAngle;
    PIDController pid;
    boolean relative = false;
    public TurnToAngle(double timeout, Angle angle) {
        timer = new Timer();
        this.timeout = timeout;
        this.angle = angle;
        targetAngle = Robot.drivetrain.getAngle().add(angle);
        SmartDashboard.putNumber("Target Angle", targetAngle.toDegrees());

        pid = new PIDController(0.01, 0, 0);

        addRequirements(Robot.drivetrain);
    }

    public TurnToAngle(double timeout, Angle angle, boolean relative) {
        timer = new Timer();
        this.timeout = timeout;
        this.angle = angle;
        targetAngle = Robot.drivetrain.getAngle().add(angle);
        SmartDashboard.putNumber("Target Angle", targetAngle.toDegrees());

        pid = new PIDController(0.01, 0, 0);
        this.relative = relative;
    }

    @Override
    public void initialize() {
        super.initialize();
        if (relative) {
            targetAngle = Robot.drivetrain.getAngle().add(angle);
        }
    }

    @Override
    public void execute() {
        double power = pid.calculate(Robot.drivetrain.getAngle().toAbsDegrees());
        power = Math.max(-0.3, Math.min(0.3, power));
        
        Robot.drivetrain.drive(0, power);
        SmartDashboard.putNumber("Current Angle", Robot.drivetrain.getAngle().toDegrees());
    }

    @Override
    public boolean isFinished() {
        return Math.abs(Robot.drivetrain.getAngle().sub(targetAngle).toDegrees()) <= 4 || timer.hasElapsed(timeout);
    }

    @Override
    public void end(boolean interrupted) {
        // Robot.drivetrain.drive(0.0, 0.0, 0.0, 1);
    }

}
