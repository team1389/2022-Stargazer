package frc.autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.commands.FollowPath;
import frc.commands.Shoot;
import frc.commands.ShootWithSensors;
import frc.robot.Robot;

public class OneBallAuto extends SequentialCommandGroup{
    public OneBallAuto() {
        addRequirements(Robot.drivetrain, Robot.shooter,Robot.intake);
        addCommands(
            new ShootWithSensors(),
            new InstantCommand(() -> Robot.intake.setIntakePercent(0.85)),
            new FollowPath("OneBallAuto"),
            new InstantCommand(() -> Robot.intake.setIntakePercent(0.0))
        );
    }
}