// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;
import frc.subsystems.SwerveWheel;

public class OneWheelControl extends CommandBase {
    
    public OneWheelControl() {
        addRequirements(Robot.swerveWheel);

        SmartDashboard.putNumber("kP", 1);
        SmartDashboard.putNumber("kI", 0);
        SmartDashboard.putNumber("kD", 0);

    }

    @Override
    public void initialize() {
        //Robot.swerveWheel.resetAbsEncoder();
        //Robot.swerveWheel.coordinateRelativeEncoder();

        Robot.swerveWheel.setPID(
            SmartDashboard.getNumber("kP", 1),
            SmartDashboard.getNumber("kP", 0),
            SmartDashboard.getNumber("kP", 0)
        );
    }

    @Override
    public void execute() {
        Robot.swerveWheel.setAngle(90);

        SmartDashboard.putNumber("Angle", Robot.swerveWheel.getState().angle.getDegrees());
        SmartDashboard.putNumber("Speed (m/s)", Robot.swerveWheel.getState().speedMetersPerSecond);
    }
}
