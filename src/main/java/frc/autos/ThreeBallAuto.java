package frc.autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.commands.FollowPath;
import frc.commands.Shoot;
import frc.robot.Robot;

public class ThreeBallAuto extends SequentialCommandGroup{
    public ThreeBallAuto() {
        addRequirements(Robot.drivetrain, Robot.shooter,Robot.intake);
        addCommands(
            new Shoot(),
            new InstantCommand(() -> Robot.intake.setIntakePercent(0.85)),
            new FollowPath("ThreeBallAuto"),
            new InstantCommand(() -> Robot.intake.setIntakePercent(0.0)),
            new Shoot()
        );
    }
}
